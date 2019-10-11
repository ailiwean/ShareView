package com.ailiwean.lib;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class ShareMultiDelegate {

    //根View
    FrameLayout rootView;

    @SuppressLint("UseSparseArrays")
    HashMap<Integer, Build> buildMap = new HashMap<>();

    @SuppressLint("UseSparseArrays")
    HashMap<Integer, Integer> typeMap = new HashMap<>();

    @SuppressLint("UseSparseArrays")
    HashMap<Integer, View> reuseMap = new HashMap<>();

    Set<Integer> receptType = new TreeSet<>();

    //是否启用懒加载
    boolean isLazyLoad = true;
    //是否复用Layout
    boolean isReuseLayout = true;
    //开屏初始的View
    int defaultType = -1;
    //当前展示的Type
    int currentType = -1;

    private ShareMultiDelegate(FrameLayout controlView) {
        this.rootView = controlView;
    }

    public static ShareMultiDelegate getInstance(FrameLayout controlView) {
        return new ShareMultiDelegate(controlView);
    }

    /***
     * 注册多布局
     * @param type
     * @param layoutId
     * @return
     */
    public Build registerView(int type, @LayoutRes int layoutId) {
        Build build = Build.create(this, layoutId, type);
        typeMap.put(type, layoutId);
        buildMap.put(type, build);
        return build;
    }

    /***
     * 非懒加载会一次性初始化所有布局信息
     * @param isLazyLoad
     * @return
     */
    public ShareMultiDelegate isLazyLoad(boolean isLazyLoad) {
        this.isLazyLoad = isLazyLoad;
        return this;
    }

    /***
     * 设定默认加载的布局
     * @param type
     * @return
     */
    public ShareMultiDelegate setDefault(int type) {
        defaultType = type;
        return this;
    }

    /***
     * 是否复用Layout
     * @param isReuseLayout
     * @return
     */
    public ShareMultiDelegate isReuseLayout(boolean isReuseLayout) {
        this.isReuseLayout = isReuseLayout;
        return this;
    }

    public void go() {

        loadView();

        switchType(defaultType);

        creatReceptType();
    }

    /***
     *  装载View
     */
    private void loadView() {
        if (buildMap.size() == 0)
            return;

        if (isLazyLoad) {

            //加载第一项
            if (defaultType == -1) {

                List<Integer> keyList = new ArrayList<>(buildMap.keySet());
                Build build = buildMap.get(keyList.get(0));
                if (build == null || build.contentLayout == 0)
                    return;

                inflate(build);

            } else {

                Build build = buildMap.get(defaultType);
                if (build == null || build.contentLayout == 0)
                    return;

                inflate(build);
            }

        } else {
            for (Integer key : buildMap.keySet()) {

                Build build = buildMap.get(key);

                if (build == null || build.contentLayout == 0)
                    continue;

                inflate(build);
            }
        }
    }

    /***
     * 区分是否复用，将View加载到rootView中并建立在Build中
     * @return
     */
    private void inflate(Build build) {

        if (!isReuseLayout) {
            View view = LayoutInflater.from(rootView.getContext()).inflate(build.contentLayout, rootView, false);
            rootView.addView(view);
            if (build.init != null)
                build.init.init(view);
            build.bindInstanceView(view);
            return;
        }

        if (reuseMap.get(build.contentLayout) == null) {
            View view = LayoutInflater.from(rootView.getContext()).inflate(build.contentLayout, rootView, false);
            rootView.addView(view);
            reuseMap.put(build.contentLayout, view);
            if (build.init != null)
                build.init.init(view);
            build.bindInstanceView(view);
        } else {
            if (build.init != null)
                build.init.init(reuseMap.get(build.contentLayout));
            build.bindInstanceView(reuseMap.get(build.contentLayout));
        }
    }

    /***
     * 显示View
     */
    private void decisionView(int type) {

        for (int key : buildMap.keySet()) {
            Build build = buildMap.get(key);
            if (build == null || build.view == null)
                continue;
            build.view.setVisibility(View.INVISIBLE);
        }

        Build build = buildMap.get(type);
        if (build == null)
            return;

        //创建新Type自带初始化,这里做过滤操作
        boolean isCreatInit = false;

        //懒加载会走这里
        if (build.view == null) {
            inflate(build);
            isCreatInit = true;
        }

        build.view.setVisibility(View.VISIBLE);

        //对于复用的View需要重新走初始化方法
        if (isReuseLayout && !isCreatInit) {

            boolean isNeedRefresh = false;

            if (receptType.contains(build.type))
                isNeedRefresh = true;

            if (isNeedRefresh)
                build.init.init(build.view);

        }
    }

    private void creatReceptType() {

        if (buildMap == null || buildMap.keySet().size() == 0)
            return;

        HashMap<Integer, Integer> copy = new HashMap<>(typeMap);
        for (Map.Entry<Integer, Integer> entry : typeMap.entrySet()) {
            //先移除要判断的某项
            copy.remove(entry.getKey());
            //仍有该values表示重复
            if (copy.containsValue(entry.getValue()))
                receptType.add(entry.getKey());
            //移除后再添加, 保证与该项相同的想可以获取
            copy.put(entry.getKey(), entry.getValue());
        }

    }

    /***
     * 切换View  {@link #decisionView(int)}
     */
    public void switchType(int type) {
        currentType = type;
        decisionView(type);
    }

    /***
     * 获取View
     */
    public View getView(int type) {
        if (buildMap.get(type) != null)
            return Objects.requireNonNull(buildMap.get(type)).view;
        else return null;
    }

    public static class Build {

        int contentLayout;
        ShareMultiDelegate delegate;
        View view;
        Init init;
        int type;

        private Build(ShareMultiDelegate delegate, @LayoutRes int layout, int type) {
            this.delegate = delegate;
            this.contentLayout = layout;
            this.type = type;
        }

        private static Build create(ShareMultiDelegate delegate, @LayoutRes int layout, int type) {
            return new Build(delegate, layout, type);
        }

        public ShareMultiDelegate init(Init init) {
            this.init = init;
            return delegate;
        }

        private void bindInstanceView(View view) {
            this.view = view;
        }

    }

    public interface Init {
        void init(View pageView);
    }
}

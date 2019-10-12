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

    //存放Type与LayoutId的Map
    @SuppressLint("UseSparseArrays")
    HashMap<Integer, Integer> typeMap = new HashMap<>();

    //存放复用配置下的LayoutId与实例化View的Map
    @SuppressLint("UseSparseArrays")
    HashMap<Integer, View> reuseMap = new HashMap<>();

    //存放可复用的Type
    Set<Integer> receptType = new TreeSet<>();

    Build lastBuild = null;

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

        creatReceptType();

        loadView();

        switchType(defaultType);

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
            build.bindInstanceView(view);
            return;
        }

        if (reuseMap.get(build.contentLayout) == null) {
            View view = LayoutInflater.from(rootView.getContext()).inflate(build.contentLayout, rootView, false);
            rootView.addView(view);
            reuseMap.put(build.contentLayout, view);
            build.bindInstanceView(view);
        } else {
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
        
        //回调上个页面的隐藏方法
        if (lastBuild != null && lastBuild.lifeListener != null)
            lastBuild.lifeListener.onHide(lastBuild.view);

        Build build = buildMap.get(type);
        if (build == null)
            return;

        //懒加载会走这里
        if (build.view == null) {
            inflate(build);
            build.init.init(build.view);
            build.isInit = true;
        } else {
            //对于复用的View需要重新走初始化方法
            if (isReuseLayout) {
                if (receptType.contains(build.type)) {
                    build.init.init(build.view);
                } else {
                    if (!build.isInit) {
                        build.init.init(build.view);
                        build.isInit = true;
                    }
                }
            } else {
                //非复用的View未执行init则init
                if (!build.isInit) {
                    build.init.init(build.view);
                    build.isInit = true;
                }
            }

        }

        //显示当前页面并回调
        build.view.setVisibility(View.VISIBLE);
        if (build.lifeListener != null)
            build.lifeListener.onVisiable(build.view);
        lastBuild = build;
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
        //Build与Layout对应的Type
        int type;

        //是否以及init
        boolean isInit;

        Init init;

        LifeListener lifeListener;

        private Build(ShareMultiDelegate delegate, @LayoutRes int layout, int type) {
            this.delegate = delegate;
            this.contentLayout = layout;
            this.type = type;
        }

        private static Build create(ShareMultiDelegate delegate, @LayoutRes int layout, int type) {
            return new Build(delegate, layout, type);
        }

        /***
         * 初始化回调，非复用布局Layout只执行一次
         * @param init
         * @return
         */
        public Build init(Init init) {
            this.init = init;
            return this;
        }


        public Build bindLifeListener(LifeListener lifeListener) {
            this.lifeListener = lifeListener;
            return this;
        }

        /***
         * 完成配置,返回代理
         * @return
         */
        public ShareMultiDelegate complete() {
            return delegate;
        }

        private void bindInstanceView(View view) {
            this.view = view;
        }

    }

    //Layout 初始化接口
    public interface Init {
        void init(View pageView);
    }

    //Layout的生命周期回调
    public interface LifeListener {

        void onVisiable(View pageView);

        void onHide(View pageView);

    }

}

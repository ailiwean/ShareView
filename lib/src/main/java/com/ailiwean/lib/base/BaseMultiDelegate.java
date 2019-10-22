package com.ailiwean.lib.base;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;

import com.ailiwean.lib.Shareable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseMultiDelegate<M extends BaseMultiDelegate, T extends BaseBuild<M>> implements Shareable {

    //根View
    FrameLayout rootView;

    //存放Type与Build的Map
    @SuppressLint("UseSparseArrays")
    LinkedHashMap<Integer, T> buildMap = new LinkedHashMap<>();

    //存放Type与LayoutId的Map
    @SuppressLint("UseSparseArrays")
    HashMap<Integer, Integer> typeMap = new HashMap<>();

    //存放复用配置下的LayoutId与实例化View的Map
    @SuppressLint("UseSparseArrays")
    HashMap<Integer, View> reuseMap = new HashMap<>();

    //存放可复用的Type
    Set<Integer> receptType = new TreeSet<>();

    //是否启用懒加载
    boolean isLazyLoad = true;
    //是否复用Layout
    boolean isReuseLayout = true;
    //开屏初始的View
    int defaultType = -1;
    //当前展示的Type
    int currentType = -1;

    protected BaseMultiDelegate(FrameLayout controlView) {
        this.rootView = controlView;
    }

    /***
     * 注册多布局
     * @param type
     * @param layoutId
     * @return
     */
    public T registerView(int type, @LayoutRes int layoutId) {
        T build = creatBuild((M) this, layoutId, type);
        typeMap.put(type, layoutId);
        buildMap.put(type, build);
        return build;
    }
        
    /***
     * 非懒加载会一次性加载所有布局
     * @param isLazyLoad
     * @return
     */
    public BaseMultiDelegate isLazyLoad(boolean isLazyLoad) {
        this.isLazyLoad = isLazyLoad;
        return this;
    }

    /***
     * 设定默认加载的布局
     * @param type
     * @return
     */
    public BaseMultiDelegate setDefault(int type) {
        defaultType = type;
        return this;
    }

    /***
     * 是否复用Layout
     * @param isReuseLayout
     * @return
     */
    public BaseMultiDelegate isReuseLayout(boolean isReuseLayout) {
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
                T build = buildMap.get(keyList.get(0));
                if (build == null || build.contentLayout == 0)
                    return;

                inflate(build);

            } else {

                T build = buildMap.get(defaultType);
                if (build == null || build.contentLayout == 0)
                    return;

                inflate(build);
            }

        } else {
            for (Integer key : buildMap.keySet()) {

                T build = buildMap.get(key);

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
    private void inflate(T build) {

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
     * 懒加载
     * @param build
     */
    protected void lazyCreat(T build) {
        if (build.view == null) {
            inflate(build);
            build.initListener.init(build.view);
            build.isInit = true;
        } else {
            //对于复用的View需要重新走初始化方法
            if (receptType.contains(build.type)) {
                build.initListener.init(build.view);
            } else {
                if (!build.isInit) {
                    build.initListener.init(build.view);
                    build.isInit = true;
                }
            }
        }

    }

    /***
     * 检索判断需要复用的布局
     */
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
     * 切换View  {@link #dispatchShowView(int)}
     */
    public void switchType(int type) {
        currentType = type;

        T build = getBuild(type);
        if (build == null)
            return;

        //懒加载View,确保切换前View存在
        lazyCreat(build);
        dispatchShowView(type);
    }

    /***
     * 获取当前显示页的Type
     * @return
     */
    public int getCurrentType() {
        return getCurrentType();
    }

    protected T getBuild(int type) {
        return buildMap.get(type);
    }

    protected List<T> getBuildLinkList() {
        List<T> buildList = new ArrayList<>();
        for (Map.Entry<Integer, T> item : buildMap.entrySet()) {
            buildList.add(item.getValue());
        }
        return buildList;
    }

    protected abstract T creatBuild(M delegate, @LayoutRes int layout, int type);

}

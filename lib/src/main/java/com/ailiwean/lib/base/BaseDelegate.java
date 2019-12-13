package com.ailiwean.lib.base;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;

import com.ailiwean.lib.interfaces.LifeListenerInner;
import com.ailiwean.lib.manager.LifeManager;
import com.ailiwean.lib.utils.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public abstract class BaseDelegate<M extends BaseDelegate, T extends BaseBuild> implements LifeListenerInner<T> {

    //根View
    protected FrameLayout rootView;

    //存放Type与Build的Map
    protected SparseArray<T> buildMap = new SparseArray<>();

    //存放Type与LayoutId的Map
    protected SparseIntArray typeMap = new SparseIntArray();

    //存放复用配置下的LayoutId与实例化View的Map
    SparseArray<View> reuseMap = new SparseArray<>();

    //存放可复用的Type
    Set<Integer> receptType = new TreeSet<>();

    //任务队列
    LinkedList<Runnable> queue = new LinkedList<>();

    //是否启用懒加载
    boolean isLazyLoad = true;
    //是否复用Layout
    boolean isReuseLayout = true;
    //开屏初始的View
    int defaultType = -1;
    //当前展示的Type
    int currentType = -1;

    protected LifeManager lifeManager;

    protected BaseDelegate(FrameLayout controlView) {
        this.rootView = controlView;
        lifeManager = LifeManager.getInstance(this);
    }

    /***
     * 注册多布局
     * @param type
     * @param layoutId
     * @return
     */
    public T regLayout(int type, @LayoutRes int layoutId) {
        return regLayout(null, type, layoutId);
    }

    public T regLayout(T t, int type, @LayoutRes int layoutId) {
        if (t == null)
            t = creatBuild((M) this, layoutId, type);
        typeMap.append(type, layoutId);
        buildMap.append(type, t);
        return t;
    }

    /***
     * 非懒加载会一次性加载所有布局
     * @param isLazyLoad
     * @return
     */
    public M isLazyLoad(boolean isLazyLoad) {
        this.isLazyLoad = isLazyLoad;
        return (M) this;
    }

    /***
     * 设定默认加载的布局
     * @param type
     * @return
     */
    public M setDefault(int type) {
        defaultType = type;
        return (M) this;
    }

    /***
     * 是否复用Layout
     * @param isReuseLayout
     * @return
     */
    public M isReuseLayout(boolean isReuseLayout) {
        this.isReuseLayout = isReuseLayout;
        return (M) this;
    }

    public void go() {

        creatReceptType();

        loadView();

        goTo(defaultType);

    }

    /***
     *  装载View
     */
    private void loadView() {

        if (buildMap.size() == 0)
            return;

        //初始化ViewStub
        Iterator<T> iterator = getBuildLinkList().iterator();
        while (iterator.hasNext()) {
            inflateStub(iterator.next());
        }

        if (isLazyLoad) {

            //加载第一项
            if (defaultType == -1) {

                T build = buildMap.valueAt(0);
                if (build == null || build.getContentLayout() == 0)
                    return;

                defaultType = build.type;

                inflate(build);

            } else {

                T build = buildMap.get(defaultType);
                if (build == null || build.getContentLayout() == 0)
                    return;

                inflate(build);
            }

        } else {

            for (int i = 0; i < buildMap.size(); i++) {
                T build = buildMap.valueAt(i);
                if (build == null || build.getContentLayout() == 0)
                    continue;
                inflate(build);
            }

            if (defaultType == -1)
                defaultType = buildMap.valueAt(0).type;

        }
        currentType = defaultType;
    }

    /***
     * 实例ViewStub
     * @param build
     */
    private void inflateStub(T build) {

        if (!isReuseLayout) {
            build.bindViewStub(rootView);
            return;
        }

        //复用状态下, 若存在相同Type的则传递引用
        if (reuseMap.get(build.getContentLayout()) == null) {
            reuseMap.append(build.getContentLayout(), build.bindViewStub(rootView));
        } else {
            build.copyPageRoot((ViewStub) reuseMap.get(build.getContentLayout()));
        }

    }

    /***
     * 区分是否复用，将View加载到rootView中并建立在Build中
     * @return
     */
    public void inflate(T build) {
        build.bindInstanceView();
        build.hide();
    }

    /***
     * 懒加载
     * @param build
     */
    protected void lazyCreat(T build) {

        if (build.getPageView() == null) {
            inflate(build);
            onInit(build);
        } else {
            //对于复用的View需要重新走初始化方法
            if (receptType.contains(build.getType())) {
                onInit(build);
            } else {
                if (!build.isInit()) {
                    onInit(build);
                }
            }
        }
        build.show();
    }

    /***
     * 检索判断需要复用的布局
     */
    private void creatReceptType() {

        if (buildMap == null || buildMap.size() == 0)
            return;

        if (!isReuseLayout)
            return;

        SparseIntArray copy = typeMap.clone();
        for (int i = 0; i < copy.size(); i++) {
            int values = copy.valueAt(i);
            int key = copy.keyAt(i);
            //先移除要判断的某项
            copy.removeAt(i);
            //仍有该values表示重复
            if (copy.indexOfValue(values) != -1)
                receptType.add(key);
            //移除后再添加, 保证与该项相同的想可以获取
            copy.append(key, values);
        }
    }

    /***
     * 切换View  {@link #dispatchShowView(int)}
     */
    public void goTo(int type) {

        T build = getBuild(type);
        if (build == null)
            return;
        //懒加载View,确保切换前View存在
        lazyCreat(build);
        dispatchShowView(type);
    }

    protected abstract void dispatchShowView(int type);

    /***
     * 页面可视时调用
     * @param baseBuild
     */
    @Override
    public void onVisiable(T baseBuild) {
        baseBuild.show();
        lifeManager.onVisiable(baseBuild);
    }

    /***
     * 页面隐藏时调用
     * @param baseBuild
     */
    @Override
    public void onHide(T baseBuild) {
        //View的显示隐藏(核心)在内部控制, 其他交给lifeManager管理
        baseBuild.hide();
        lifeManager.onHide(baseBuild);
    }

    /***
     * 切换到该页面时立刻调用只调用一次
     * @param baseBuild
     */
    @Override
    public void onInit(T baseBuild) {
        lifeManager.onInit(baseBuild);
    }

    /***
     * 该页面动画播放完毕调用
     * @param baseBuild
     */
    @Override
    public void onLazy(T baseBuild) {
        lifeManager.onLazy(baseBuild);
    }

    /***
     * 预加载时调用
     * @param type
     */
    @Override
    public void onPreload(int type) {
        lifeManager.onPreload(type);
    }

    /***
     * 发送数据不带范型模式
     * @param type
     */
    public void postData(int type, Object o) {
        postData(type, new TypeToken<>(o.getClass()), o);
    }

    /***
     * 发送数据携带范型模式,最高支持三层，   参照{@link TypeToken}
     */
    public void postData(int type, TypeToken<?> typeToken, Object o) {
        BaseBuild baseBuild = getBuild(type);
        if (baseBuild == null)
            return;
    }


    private void packRunnable(Runnable runnable) {

    }


    /***
     * 获取当前显示页的Type
     * @return
     */
    public int getCurrentType() {
        return currentType;
    }

    /***
     * 返回Build集合
     */
    public SparseArray<T> getBuildMap() {
        return buildMap;
    }

    /***
     * 更新CurrentType
     * @param currentType
     */
    protected void updateCurrentType(int currentType) {
        this.currentType = currentType;
    }

    protected T getBuild(int type) {
        return buildMap.get(type);
    }

    protected List<T> getBuildLinkList() {
        List<T> buildList = new ArrayList<>();
        for (int i = 0; i < buildMap.size(); i++) {
            buildList.add(buildMap.valueAt(i));
        }
        return buildList;
    }

    protected abstract T creatBuild(M delegate, @LayoutRes int layout, int type);

}

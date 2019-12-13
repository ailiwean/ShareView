package com.ailiwean.lib.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.annotation.LayoutRes;

import com.ailiwean.lib.ShareView;
import com.ailiwean.lib.interfaces.InitListener;
import com.ailiwean.lib.interfaces.LazyListener;
import com.ailiwean.lib.interfaces.LifeListener;
import com.ailiwean.lib.interfaces.PreLoadListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseBuild<T extends BaseBuild, M extends BaseDelegate, H extends BaseViewHolder, D extends BaseObserve> {

    private int contentLayout;

    //ViewHolder
    private View pageView;

    private ViewStub pageRoot;

    protected ShareView rootView;

    //Build与Layout对应的Type
    protected int type;

    //是否已经init
    protected boolean isInit;

    //是否已经lazy
    protected boolean isLazy;

    //pageView的持有类，并扩展其他方法
    private H vh;

    private M delegate;

    private List<D> baseObserves = new ArrayList<>();

    InitListener initListener = new InitListener<H>() {

        @Override
        public void init(H vh) {

        }
    };

    LazyListener lazyListener = new LazyListener() {
        @Override
        public void onLazy(BaseViewHolder vh) {

        }
    };

    PreLoadListener preLoadListener = new PreLoadListener() {
        @Override
        public void preLoad(BaseViewHolder vh) {

        }
    };

    public List<LifeListener<H>> lifeListeners = new ArrayList<>();

    protected BaseBuild(M delegate, @LayoutRes int layout, int type) {
        this.delegate = delegate;
        this.contentLayout = layout;
        this.type = type;
        this.rootView = (ShareView) delegate.rootView;
    }

    /***
     * 初始化回调，非复用布局Layout只执行一次
     * @param initListener
     * @return
     */
    public T init(InitListener<H> initListener) {
        this.initListener = initListener;
        return (T) this;
    }

    /***
     * 懒加载  :   View创建完成并展示动画播放完毕
     */
    public T lazy(LazyListener<H> lazyListener) {
        this.lazyListener = lazyListener;
        return (T) this;
    }

    /***
     * 页面预加载回调
     * @param preLoadListener
     * @return
     */
    public T preLoad(PreLoadListener<H> preLoadListener) {
        this.preLoadListener = preLoadListener;
        return (T) this;
    }

    /***
     * 添加生命周期回调
     * @param lifeListener
     * @return
     */
    public T addLifeListener(LifeListener<H> lifeListener) {
        lifeListeners.add(lifeListener);
        return (T) this;
    }

    /***
     * 获取页的根View
     * @return
     */
    public View getPageView() {
        return pageView;
    }

    /***
     * 获取填充ViewStub
     * @return
     */
    public ViewStub getPageRoot() {
        return pageRoot;
    }

    protected abstract H creatViewHolder(View pageView);

    public H getVH() {
        return vh;
    }

    public InitListener getInitListener() {
        return initListener;
    }

    public LazyListener getLazyListener() {
        return lazyListener;
    }

    public PreLoadListener getPreLoadListener() {
        return preLoadListener;
    }

    /***
     * 完成配置,返回代理
     * @return
     */
    public M cp() {
        return delegate;
    }

    /***
     * 自身实例化View, 非复用布局pageView会为null继而调用inflate,复用布局则会从ViewStub中通过通用ID获取实例的PageView
     */
    protected void bindInstanceView() {

        if (pageView != null) {
            vh = creatViewHolder(pageView);
            return;
        }

        //复用布局
        pageView = (View) pageRoot.getTag();

        if (pageView != null) {
            vh = creatViewHolder(pageView);
            return;
        }
        pageView = pageRoot.inflate();
        pageRoot.setTag(pageView);

        vh = creatViewHolder(pageView);
    }

    protected int getContentLayout() {
        return contentLayout;
    }

    protected int getType() {
        return type;
    }

    protected boolean isInit() {
        return isInit;
    }

    public void setInit(boolean isInit) {
        this.isInit = isInit;
    }

    protected boolean isLazy() {
        return isLazy;
    }

    public void setLazy(boolean isLazy) {
        this.isLazy = isLazy;
    }

    protected List<D> getBaseObserves() {
        return baseObserves;
    }

    protected ViewStub bindViewStub(ViewGroup rootView) {
        return bindViewStub(rootView, -1);
    }

    protected ViewStub bindViewStub(ViewGroup rootView, int index) {
        if (pageRoot == null) {
            pageRoot = new ViewStub(rootView.getContext());
            rootView.addView(pageRoot, index);
            pageRoot.setLayoutResource(contentLayout);
            pageRoot.setLayoutInflater(LayoutInflater.from(rootView.getContext()));
        }
        return pageRoot;
    }

    protected void copyPageRoot(ViewStub pageRoot) {
        this.pageRoot = pageRoot;
    }

    public void hide() {

        if (pageView != null)
            pageView.setVisibility(View.GONE);

    }

    public void show() {

        if (pageView != null)
            pageView.setVisibility(View.VISIBLE);

    }

    public void destory() {
        int index = rootView.indexOfChild(pageView);
        rootView.removeView(pageView);
        pageView = null;
        pageRoot = null;
        bindViewStub(rootView, index);
        isInit = false;
        isLazy = false;
        lifeListeners.clear();
    }
}

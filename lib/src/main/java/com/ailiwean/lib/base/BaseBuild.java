package com.ailiwean.lib.base;

import android.view.View;

import androidx.annotation.LayoutRes;

import com.ailiwean.lib.callback.InitListener;
import com.ailiwean.lib.callback.LifeListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseBuild<T extends BaseBuild, M extends BaseDelegate, H extends BaseViewHolder> {

    int contentLayout;

    //ViewHolder
    private View pageView;

    //Build与Layout对应的Type
    public int type;

    //是否已经init
    boolean isInit;

    //pageView的持有类，并扩展其他方法
    H vH;

    private M delegate;

    InitListener initListener = new InitListener<H>() {

        @Override
        public void init(H vH) {

        }
    };

    public List<LifeListener<H>> lifeListeners = new ArrayList<>();

    protected BaseBuild(M delegate, @LayoutRes int layout, int type) {
        this.delegate = delegate;
        this.contentLayout = layout;
        this.type = type;
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

    protected abstract H creatViewHolder(View pageView);

    public H getVH() {
        return vH;
    }

    /***
     * 完成配置,返回代理
     * @return
     */
    public M cp() {
        return delegate;
    }

    protected void bindInstanceView(View view) {
        this.pageView = view;
        vH = creatViewHolder(pageView);
    }

}

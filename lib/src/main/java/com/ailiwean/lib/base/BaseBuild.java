package com.ailiwean.lib.base;

import android.view.View;

import androidx.annotation.LayoutRes;

import com.ailiwean.lib.callback.InitListener;
import com.ailiwean.lib.callback.LifeListener;

import java.util.ArrayList;
import java.util.List;

public class BaseBuild<M extends BaseMultiDelegate> {

    public int contentLayout;

    public M delegate;

    //pageView
    public View view;

    //Build与Layout对应的Type
    public int type;

    //是否已经init
    public boolean isInit;

    public InitListener initListener = new InitListener() {
        @Override
        public void init(View pageView) {

        }
    };

    public List<LifeListener> lifeListeners = new ArrayList<>();

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
    public BaseBuild init(InitListener initListener) {
        this.initListener = initListener;
        return this;
    }

    /***
     * 添加生命周期回调
     * @param lifeListener
     * @return
     */
    public BaseBuild addLifeListener(LifeListener lifeListener) {
        lifeListeners.add(lifeListener);
        return this;
    }

    /***
     * 完成配置,返回代理
     * @return
     */
    public M cp() {
        return delegate;
    }

    protected void bindInstanceView(View view) {
        this.view = view;
    }

}

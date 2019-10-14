package com.ailiwean.lib.animation;

import android.view.View;

public abstract class BaseAnim {

    View pageView;

    public void injectPageView(View pageView) {
        this.pageView = pageView;
    }

    /***
     * 所有动画开始前执行
     */
    void animStar() {
        pageView.setVisibility(View.VISIBLE);
    }

    /***
     * 启动页动画结束后执行
     */
    void enterAnimEnd() {
        pageView.setVisibility(View.VISIBLE);
    }

    /***
     * 关闭页动画结束后执行
     */
    void exitAnimEnd() {
        pageView.setVisibility(View.INVISIBLE);
    }

}
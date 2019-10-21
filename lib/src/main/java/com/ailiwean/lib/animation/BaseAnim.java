package com.ailiwean.lib.animation;

import android.view.View;

public abstract class BaseAnim {

    View pageView;

    Runnable runnable;

    public final void injectPageView(View pageView) {
        this.pageView = pageView;
    }

    /***
     * 通过动画结束来控制上个界面的隐藏，由{@Link com.ailiwean.lib.delegate.ShareMultiDelegate调用}
     * @param runnable
     */
    public final void operatorLastBuildBack(Runnable runnable) {
        this.runnable = runnable;
    }

    /***
     * 进入动画开始前执行
     */
    public final void defaultEnterAnimStar() {
        pageView.setVisibility(View.VISIBLE);
    }

    /***
     * 关闭动画开始前执行
     */
    public final void defaultExitAnimStar() {
        //ageView.setVisibility(View.VISIBLE);
    }

    /***
     * 进入页动画结束后执行
     */
    public final void defaultEnterAnimEnd() {
        pageView.setVisibility(View.VISIBLE);
        //以展示页面为主,进入页动画结束后应该立即隐藏其他页面
        if (runnable != null)
            runnable.run();
    }

    /***
     * 关闭页动画结束后执行, 暂时为空， 避免含有空动画切换时白屏
     */
    public final void defaultExitAnimEnd() {
    }

}
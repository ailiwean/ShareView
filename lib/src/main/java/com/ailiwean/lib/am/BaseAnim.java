package com.ailiwean.lib.am;

import android.view.View;

import com.ailiwean.lib.callback.AnimStateListener;

public abstract class BaseAnim implements AnimStateListener {

    View pageView;

    Runnable endBack;

    Runnable startBack;

    public final void injectPageView(View pageView) {
        this.pageView = pageView;
    }

    /***
     * 为库内提供的动画状态的监听，由{@link com.ailiwean.lib.base.BaseDelegate  调用}
     * @param endBack
     */
    public final void operatorEndBack(Runnable endBack) {
        this.endBack = endBack;
    }


    public final void opetatorStartBack(Runnable startBack) {
        this.startBack = startBack;
    }

    /***
     * 保留库内回调
     */
    public void enterAnimStar_Inner() {

        pageView.setVisibility(View.VISIBLE);

        if (startBack != null) {
            startBack.run();
            startBack = null;
        }


    }

    public void enterAnimEnd_Inner() {
        if (endBack != null) {
            endBack.run();
            endBack = null;
        }
    }

    public void exitAnimEnd_Inner() {

        if (endBack != null) {
            endBack.run();
            endBack = null;
        }
    }

    public void exitAnimStar_Inner() {

        pageView.setVisibility(View.VISIBLE);

        if (startBack != null) {
            startBack.run();
            startBack = null;
        }
    }

    /***
     * 自定义选择实现
     * @param view
     */
    @Override
    public void enterAnimStar(View view) {

    }

    @Override
    public void enterAnimEnd(View view) {

    }

    @Override
    public void exitAnimStar(View view) {

    }

    @Override
    public void exitAnimEnd(View view) {

    }


}
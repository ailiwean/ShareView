package com.ailiwean.lib.am;

import android.view.View;

import com.ailiwean.lib.callback.AnimInnerListener;
import com.ailiwean.lib.callback.AnimStateListener;

public abstract class BaseAnim implements AnimStateListener, AnimInnerListener {

    Runnable endBack;

    Runnable startBack;

    //动画优先级
    int priority = 1;  //new出来的>com>自带

    public int getPriority() {
        return priority;
    }

    public BaseAnim setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public BaseAnim() {
        priority = 10;
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
    private final void enterAnimStar_Inner(View pageView) {

        pageView.setVisibility(View.VISIBLE);

    }

    private final void enterAnimEnd_Inner(View pageView) {
        if (endBack != null) {
            endBack.run();
            endBack = null;
        }
    }

    private final void exitAnimEnd_Inner(View pageView) {

        if (endBack != null) {
            endBack.run();
            endBack = null;
        }
    }

    private final void exitAnimStar_Inner(View pageView) {

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
    public void enterAnimStar(View view, boolean isTaskTop) {

    }

    @Override
    public void enterAnimEnd(View view, boolean isTaskTop) {

    }

    @Override
    public void exitAnimStar(View view, boolean isTaskTop) {

    }

    @Override
    public void exitAnimEnd(View view, boolean isTaskTop) {

    }

    /***
     * 子类的最终调用
     * @param view
     * @param isTaskTop
     */
    final void finalEnterAnimStar(View view, boolean isTaskTop) {
        enterAnimStar_Inner(view);
        enterAnimStar(view, isTaskTop);
    }

    final void finalEnterAnimEnd(View view, boolean isTaskTop) {
        enterAnimEnd_Inner(view);
        enterAnimEnd(view, isTaskTop);
    }

    final void finalExitAnimStar(View view, boolean isTaskTop) {
        exitAnimStar_Inner(view);
        exitAnimStar(view, isTaskTop);
    }

    final void finalExitAnimEnd(View view, boolean isTaskTop) {
        exitAnimEnd_Inner(view);
        exitAnimEnd(view, isTaskTop);
    }

}
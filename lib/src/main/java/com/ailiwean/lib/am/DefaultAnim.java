package com.ailiwean.lib.am;

import android.view.View;

import com.ailiwean.lib.callback.AnimOutListener;
import com.ailiwean.lib.callback.AnimStateListener;

public abstract class DefaultAnim extends CustomAnim implements AnimOutListener {

    /***
     * 通用的监听,被{@link AnimLib}回调，最终调用, 重写的动画监听方法以及{@link BaseAnim 中所必须的方法}
     */
    AnimStateListener animStateListener = new AnimStateListener() {
        @Override
        public void enterAnimStar(View view, boolean isTaskTop) {
            finalEnterAnimStar(view, isTaskTop);
        }

        @Override
        public void enterAnimEnd(View view, boolean isTaskTop) {
            finalEnterAnimEnd(view, isTaskTop);
        }

        @Override
        public void exitAnimStar(View view, boolean isTaskTop) {
            finalExitAnimStar(view, isTaskTop);
        }

        @Override
        public void exitAnimEnd(View view, boolean isTaskTop) {
            finalExitAnimEnd(view, isTaskTop);
        }

    };

    @Override
    public int taskTopEnter() {
        return AnimHelper.ALPHA_UP_SHOW;
    }

    @Override
    public int taskTopExit() {
        return AnimHelper.NULL;
    }

    @Override
    public int taskInnerEnter() {
        return AnimHelper.NULL;
    }

    @Override
    public int taskInnerExit() {
        return AnimHelper.NULL;
    }

    @Override
    public final void enter(View pageView, boolean isTopTask, boolean isExecute) {
        if (isTopTask)
            commonExecute(taskTopEnter(), true, true, isExecute);
        else commonExecute(taskInnerEnter(), true, false, isExecute);

    }

    @Override
    public final void exit(View pageView, boolean isTopTask, boolean isExecute) {

        if (isTopTask)
            commonExecute(taskTopExit(), false, true, isExecute);
        else commonExecute(taskInnerExit(), false, false, isExecute);

    }

    private void commonExecute(int type, boolean isEnter, boolean isTopTask, boolean isExecute) {

        if (!isExecute)
            return;

        switch (type) {

            case AnimHelper.ALPHA_UP_SHOW:
                AnimLib.getInstance(AnimHelper.ALPHA_UP_SHOW, pageView)
                        .isEnter(isEnter)
                        .isTopTask(isTopTask)
                        .bindListener(animStateListener)
                        .start();
                break;
            case AnimHelper.ALPHA_DOWN_HIDE:
                AnimLib.getInstance(AnimHelper.ALPHA_DOWN_HIDE, pageView)
                        .isEnter(isEnter)
                        .isTopTask(isTopTask)
                        .bindListener(animStateListener)
                        .start();
                break;

            case AnimHelper.LEFT_ALL_SHOW:
                AnimLib.getInstance(AnimHelper.LEFT_ALL_SHOW, pageView)
                        .isEnter(isEnter)
                        .isTopTask(isTopTask)
                        .bindListener(animStateListener)
                        .start();

                break;

            case AnimHelper.RIGHT_ALL_HIDE:
                AnimLib.getInstance(AnimHelper.RIGHT_ALL_HIDE, pageView)
                        .isEnter(isEnter)
                        .isTopTask(isTopTask)
                        .bindListener(animStateListener)
                        .start();
                break;

            case AnimHelper.LEFT_HALF_HIDE:
                AnimLib.getInstance(AnimHelper.LEFT_HALF_HIDE, pageView)
                        .isEnter(isEnter)
                        .isTopTask(isTopTask)
                        .bindListener(animStateListener)
                        .start();
                break;

            case AnimHelper.RIGHT_HALF_SHOW:
                AnimLib.getInstance(AnimHelper.RIGHT_HALF_SHOW, pageView)
                        .isEnter(isEnter)
                        .isTopTask(isTopTask)
                        .bindListener(animStateListener)
                        .start();
                break;

            default:
                if (isEnter) {
                    finalEnterAnimStar(pageView, isTopTask);
                    finalEnterAnimEnd(pageView, isTopTask);
                } else {
                    finalExitAnimStar(pageView, isTopTask);
                    finalExitAnimEnd(pageView, isTopTask);
                }
                break;
        }
    }

    private void finalEnterAnimStar(View view, boolean isTaskTop) {
        enterAnimStar_Inner();
        DefaultAnim.this.enterAnimStar(view, isTaskTop);
    }

    private void finalEnterAnimEnd(View view, boolean isTaskTop) {
        enterAnimEnd_Inner();
        DefaultAnim.this.enterAnimEnd(view, isTaskTop);
    }

    private void finalExitAnimStar(View view, boolean isTaskTop) {
        exitAnimStar_Inner();
        DefaultAnim.this.exitAnimStar(view, isTaskTop);
    }

    private void finalExitAnimEnd(View view, boolean isTaskTop) {
        exitAnimEnd_Inner();
        DefaultAnim.this.exitAnimEnd(view, isTaskTop);
    }
}

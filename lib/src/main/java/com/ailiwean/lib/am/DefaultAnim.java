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
        public void enterAnimStar(View view) {
            finalEnterAnimStar(view);
        }

        @Override
        public void enterAnimEnd(View view) {
            finalEnterAnimEnd(view);
        }

        @Override
        public void exitAnimStar(View view) {
            finalExitAnimStar(view);
        }

        @Override
        public void exitAnimEnd(View view) {
            finalExitAnimEnd(view);
        }

    };

    @Override
    public final void enter(View pageView, boolean isExecute) {
        commonExecute(enter(), isExecute, true);
    }

    @Override
    public final void exit(View pageView, boolean isExecute) {
        commonExecute(exit(), isExecute, false);
    }

    private void commonExecute(int type, boolean isExecute, boolean isEnter) {

        if (!isExecute)
            return;

        switch (type) {

            case AnimHelper.ALPHA_SHOW:
                AnimLib.getInstance(AnimHelper.ALPHA_SHOW, pageView)
                        .isEnter(isEnter)
                        .bindListener(animStateListener)
                        .start();
                break;
            case AnimHelper.ALPHA_HIDE:
                AnimLib.getInstance(AnimHelper.ALPHA_HIDE, pageView)
                        .isEnter(isEnter)
                        .bindListener(animStateListener)
                        .start();
                break;

            case AnimHelper.NULL:
                if (isEnter) {
                    finalEnterAnimStar(pageView);
                    finalEnterAnimEnd(pageView);
                } else {
                    finalExitAnimStar(pageView);
                    finalExitAnimEnd(pageView);
                }
                break;
        }

    }

    @Override
    public int enter() {
        return AnimHelper.ALPHA_SHOW;
    }

    @Override
    public int exit() {
        return AnimHelper.ALPHA_HIDE;
    }

    private void finalEnterAnimStar(View view) {
        enterAnimStar_Inner();
        DefaultAnim.this.enterAnimStar(view);
    }

    private void finalEnterAnimEnd(View view) {
        enterAnimEnd_Inner();
        DefaultAnim.this.enterAnimEnd(view);
    }

    private void finalExitAnimStar(View view) {
        exitAnimStar_Inner();
        DefaultAnim.this.exitAnimStar(view);
    }

    private void finalExitAnimEnd(View view) {
        exitAnimEnd_Inner();
        DefaultAnim.this.exitAnimEnd(view);
    }

}

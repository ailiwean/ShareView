package com.ailiwean.lib.animation;

import android.view.View;

import com.ailiwean.lib.callback.AnimOutListener;
import com.ailiwean.lib.callback.AnimStateListener;

public abstract class DefaultAnim extends CustomAnim implements AnimOutListener, AnimStateListener {

    /***
     * 通用的监听,被{@link AnimLib}回调，最终调用, 重写的动画监听方法以及{@link BaseAnim 中所必须的方法}
     */
    AnimStateListener animEnter = new AnimStateListener() {
        @Override
        public void enterAnimStar(View view) {
            DefaultAnim.this.defaultEnterAnimStar();
            DefaultAnim.this.enterAnimStar(view);
        }

        @Override
        public void enterAnimEnd(View view) {
            DefaultAnim.this.defaultEnterAnimEnd();
            DefaultAnim.this.enterAnimEnd(view);
        }

        @Override
        public void exitAnimStar(View view) {

        }

        @Override
        public void exitAnimEnd(View view) {

        }


    };
    AnimStateListener animExit = new AnimStateListener() {
        @Override
        public void enterAnimStar(View view) {

        }

        @Override
        public void enterAnimEnd(View view) {

        }

        @Override
        public void exitAnimStar(View view) {
            DefaultAnim.this.defaultExitAnimStar();
            DefaultAnim.this.exitAnimStar(view);
        }

        @Override
        public void exitAnimEnd(View view) {
            DefaultAnim.this.defaultExitAnimEnd();
            DefaultAnim.this.exitAnimEnd(view);
        }
    };

    @Override
    public final void enter(View pageView) {
        commonExecute(enter(), true);
    }

    @Override
    public final void exit(View pageView) {
        commonExecute(exit(), false);
    }

    private void commonExecute(int type, boolean isEnter) {

        switch (type) {

            case AnimHelper.ALPHA_SHOW:
                AnimLib.getInstance(AnimHelper.ALPHA_SHOW, pageView)
                        .bindListener(isEnter ? animEnter : animExit)
                        .start();
                break;

            case AnimHelper.ALPHA_HIDE:
                AnimLib.getInstance(AnimHelper.ALPHA_HIDE, pageView)
                        .bindListener(isEnter ? animEnter : animExit)
                        .start();
                break;

            case AnimHelper.NULL:
                if (isEnter)
                    defaultEnterAnimEnd();
                else defaultExitAnimEnd();
                break;
        }

    }

    /***
     * 初始化时选择实现
     * @param view
     */
    @Override
    public void enterAnimStar(View view) {
    }

    @Override
    public void enterAnimEnd(View view) {

    }

    @Override
    public void exitAnimEnd(View view) {

    }

    @Override
    public void exitAnimStar(View view) {

    }

    @Override
    public int enter() {
        return AnimHelper.ALPHA_SHOW;
    }

    @Override
    public int exit() {
        return AnimHelper.ALPHA_HIDE;
    }
}

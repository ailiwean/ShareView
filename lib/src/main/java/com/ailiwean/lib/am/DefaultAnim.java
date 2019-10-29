package com.ailiwean.lib.am;

import android.view.View;

import com.ailiwean.lib.callback.AnimOutListener;
import com.ailiwean.lib.callback.AnimStateListener;

public abstract class DefaultAnim extends BaseAnim implements AnimOutListener {

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
        return AnimHelper.NULL;
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

    public DefaultAnim(int duration) {
        this.duration = duration;
    }

    public DefaultAnim() {

    }

    @Override
    public final void enter(View pageView, boolean isTopTask, boolean isExecute) {
        if (isTopTask)
            commonExecute(pageView, taskTopEnter(), true, true, isExecute);
        else commonExecute(pageView, taskInnerEnter(), true, false, isExecute);

    }

    @Override
    public final void exit(View pageView, boolean isTopTask, boolean isExecute) {

        if (isTopTask)
            commonExecute(pageView, taskTopExit(), false, true, isExecute);
        else commonExecute(pageView, taskInnerExit(), false, false, isExecute);

    }

    private void commonExecute(View pageView, int type, boolean isEnter, boolean isTopTask, boolean isExecute) {

        if (!isExecute)
            return;

        switch (type) {

            case AnimHelper.ALPHA_UP_SHOW:
                commonExeAnimLib(AnimLib.getInstance(AnimHelper.ALPHA_UP_SHOW, pageView), isEnter, isTopTask);

                break;
            case AnimHelper.ALPHA_DOWN_HIDE:
                commonExeAnimLib(AnimLib.getInstance(AnimHelper.ALPHA_DOWN_HIDE, pageView), isEnter, isTopTask);

                break;

            case AnimHelper.LEFT_ALL_SHOW:
                commonExeAnimLib(AnimLib.getInstance(AnimHelper.LEFT_ALL_SHOW, pageView), isEnter, isTopTask);
                break;

            case AnimHelper.RIGHT_ALL_HIDE:
                commonExeAnimLib(AnimLib.getInstance(AnimHelper.RIGHT_ALL_HIDE, pageView), isEnter, isTopTask);

                break;

            case AnimHelper.LEFT_HALF_HIDE:
                commonExeAnimLib(AnimLib.getInstance(AnimHelper.LEFT_HALF_HIDE, pageView), isEnter, isTopTask);

                break;

            case AnimHelper.RIGHT_HALF_SHOW:
                commonExeAnimLib(AnimLib.getInstance(AnimHelper.RIGHT_HALF_SHOW, pageView), isEnter, isTopTask);

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

    private void commonExeAnimLib(AnimLib animLib, boolean isEnter, boolean isTopTask) {
        animLib.isEnter(isEnter)
                .isTopTask(isTopTask)
                .isDuration(duration)
                .bindListener(animStateListener)
                .start();
    }

}

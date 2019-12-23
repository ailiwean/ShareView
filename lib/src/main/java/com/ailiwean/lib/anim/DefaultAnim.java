package com.ailiwean.lib.anim;

import android.view.View;

import com.ailiwean.lib.interfaces.AnimOutListener;
import com.ailiwean.lib.interfaces.AnimStateListener;

public abstract class DefaultAnim extends BaseAnim implements AnimOutListener {

    /***
     * 通用的监听,被{@link AnimHolder}回调，最终调用, 重写的动画监听方法以及{@link BaseAnim 中所必须的方法}
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
            creatAnimHolder(pageView, taskTopEnter(), true, true, isExecute);
        else creatAnimHolder(pageView, taskInnerEnter(), true, false, isExecute);
    }

    @Override
    public final void exit(View pageView, boolean isTopTask, boolean isExecute) {

        if (isTopTask)
            creatAnimHolder(pageView, taskTopExit(), false, true, isExecute);
        else creatAnimHolder(pageView, taskInnerExit(), false, false, isExecute);

    }

    private AnimHolder creatAnimHolder(View pageView, @AnimHelper.Type int type, boolean isEnter, boolean isTopTask, boolean isExecute) {

        if (!isExecute) {
            if (isEnter) {
                finalEnterAnimStar(pageView, isTopTask);
                finalEnterAnimEnd(pageView, isTopTask);
            } else {
                finalExitAnimStar(pageView, isTopTask);
                finalExitAnimEnd(pageView, isTopTask);
            }
            return null;
        }

        return commonExeAnimLib(AnimHolder.getInstance(type, pageView), isEnter, isTopTask);
    }

    private AnimHolder commonExeAnimLib(AnimHolder animHolder, boolean isEnter, boolean isTopTask) {
        return animHolder.isEnter(isEnter)
                .isTopTask(isTopTask)
                .isDuration(duration)
                .bindListener(animStateListener)
                .build();
    }

}

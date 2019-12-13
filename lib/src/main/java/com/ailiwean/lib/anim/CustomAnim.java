package com.ailiwean.lib.anim;

import android.view.View;

public abstract class CustomAnim extends BaseAnim {

    @Override
    public final void enter(View pageView, boolean isTopTask, boolean isExecute) {
        if (!isExecute)
            return;
        animEnter(pageView, isTopTask);
    }

    @Override
    public final void exit(View pageView, boolean isTopTask, boolean isExecute) {
        if (!isExecute)
            return;
        animExit(pageView, isTopTask);
    }

    public CustomAnim(int duration) {
        super(duration);
    }

    public CustomAnim() {

    }

    protected abstract void animEnter(View pageView, boolean isTopTask);

    protected abstract void animExit(View pageView, boolean isTopTask);

}

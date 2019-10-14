package com.ailiwean.lib.animation;

import android.view.View;

public class NullAnim extends CustomAnim {

    @Override
    public void enter(View pageView) {
        animStar();
    }

    @Override
    public void exit(View pageView) {
        exitAnimEnd();
    }
}

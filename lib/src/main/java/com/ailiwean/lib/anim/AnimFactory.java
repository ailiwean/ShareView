package com.ailiwean.lib.anim;

import android.animation.AnimatorSet;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.Objects;

import static android.view.View.LAYER_TYPE_NONE;
import static android.view.View.LAYER_TYPE_SOFTWARE;

public class AnimFactory {

    private AnimHolder enterAnimHolder;

    private AnimHolder exitAnimHolder;

    public static class Holder {
        static AnimFactory animFactory = new AnimFactory();
    }

    public static AnimFactory getInstance() {
        return Holder.animFactory;
    }

    /***
     * 当插入连个View的入场出场动画才进行播放
     * @param animHolder
     */
    void insert(AnimHolder animHolder) {
        if (animHolder == null)
            return;
        if (animHolder.getEnter())
            enterAnimHolder = animHolder;
        else exitAnimHolder = animHolder;
        if (enterAnimHolder == null)
            return;
        if (exitAnimHolder == null)
            return;
        loop();
    }

    private void loop() {
        final AnimHolder temStart = enterAnimHolder;
        final AnimHolder temEnd = exitAnimHolder;
        exitAnimHolder = null;
        enterAnimHolder = null;
        //开启硬件加速
        temStart.getPageView().setLayerType(View.LAYER_TYPE_HARDWARE, null);
        temEnd.getPageView().setLayerType(View.LAYER_TYPE_HARDWARE, null);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(temStart.getAnimator(), temEnd.getAnimator());
        set.setInterpolator(new DecelerateInterpolator(1.5f));
        set.start();
    }
}

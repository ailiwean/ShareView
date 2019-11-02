package com.ailiwean.lib.am;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.view.View;

import static android.view.View.LAYER_TYPE_NONE;
import static android.view.View.LAYER_TYPE_SOFTWARE;

public class AnimFactory {

    private AnimHolder enterAnimHolder;

    private AnimHolder exitAnimHolder;

    public static class Holder {
        public static AnimFactory animFactory = new AnimFactory();
    }

    public static AnimFactory getInstance() {
        return Holder.animFactory;
    }

    public void bind(AnimHolder animHolder) {

        if (animHolder == null)
            return;

        if (animHolder.getEnter())
            enterAnimHolder = animHolder;
        else exitAnimHolder = animHolder;

        loop();
    }

    private void loop() {

        if (enterAnimHolder == null)
            return;

        if (exitAnimHolder == null)
            return;

        final AnimHolder temStart = enterAnimHolder;
        final AnimHolder temEnd = exitAnimHolder;

        exitAnimHolder = null;
        enterAnimHolder = null;

//        //开启硬件加速
        temStart.getPageView().setLayerType(LAYER_TYPE_NONE, null);
        temEnd.getPageView().setLayerType(LAYER_TYPE_NONE, null);
        temStart.getPageView().setLayerType(View.LAYER_TYPE_HARDWARE, null);
        temEnd.getPageView().setLayerType(View.LAYER_TYPE_HARDWARE, null);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(temStart.getAnimator(), temEnd.getAnimator());
        set.start();


        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                temStart.getPageView().setLayerType(LAYER_TYPE_SOFTWARE, null);
                temEnd.getPageView().setLayerType(LAYER_TYPE_SOFTWARE, null);
            }
        });
    }

}

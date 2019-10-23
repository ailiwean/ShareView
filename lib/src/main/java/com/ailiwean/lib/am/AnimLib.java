package com.ailiwean.lib.am;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import com.ailiwean.lib.callback.AnimStateListener;

class AnimLib {

    int type;

    //是否进入动画
    boolean isEnter;

    View pageView;

    AnimStateListener animStateListener;

    Animation.AnimationListener listener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            if (!isEnter)
                animStateListener.exitAnimStar(pageView);
            else
                animStateListener.enterAnimStar(pageView);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (!isEnter)
                animStateListener.exitAnimEnd(pageView);
            else
                animStateListener.enterAnimEnd(pageView);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private AnimLib(@AnimHelper.Type int type, View preView) {
        this.type = type;
        this.pageView = preView;
    }

    public static AnimLib getInstance(@AnimHelper.Type int type, View preView) {
        return new AnimLib(type, preView);
    }

    public AnimLib bindListener(AnimStateListener animStateListener) {
        this.animStateListener = animStateListener;
        return this;
    }

    public AnimLib isEnter(boolean isEnter) {
        this.isEnter = isEnter;
        return this;
    }

    public void start() {

        if (pageView == null)
            return;

        pageView.clearAnimation();

        if (type == AnimHelper.ALPHA_HIDE) {
            alpha_hide_tranY();
        }

        if (type == AnimHelper.ALPHA_SHOW) {
            alpha_show_tranY();
        }

    }

    private void alpha_hide_tranY() {

        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(AnimHelper.DURATION);

        TranslateAnimation tranAnimation = new TranslateAnimation(0f, 0f, 0f, 300f);
        tranAnimation.setDuration(AnimHelper.DURATION);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(alphaAnimation);
        set.addAnimation(tranAnimation);
        set.setAnimationListener(listener);

        pageView.startAnimation(set);
    }

    private void alpha_show_tranY() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(AnimHelper.DURATION);

        TranslateAnimation tranAnimation = new TranslateAnimation(0f, 0f, 300f, 0);
        tranAnimation.setDuration(AnimHelper.DURATION);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(alphaAnimation);
        set.addAnimation(tranAnimation);
        set.setAnimationListener(listener);

        pageView.startAnimation(set);
    }


}

package com.ailiwean.lib.animation;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.ailiwean.lib.callback.AnimStateListener;

class AnimLib {

    int type;

    View pageView;

    AnimStateListener animStateListener;

    Animation.AnimationListener listener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            animStateListener.exitAnimStar(pageView);
            animStateListener.enterAnimStar(pageView);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            animStateListener.exitAnimEnd(pageView);
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

    public void start() {

        if (pageView == null)
            return;

        pageView.clearAnimation();

        if (type == AnimHelper.ALPHA_HIDE) {
            alpha_hide();
        }

        if (type == AnimHelper.ALPHA_SHOW) {
            alpha_show();
        }

    }

    private void alpha_hide() {

        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(AnimHelper.DURATION);
        alphaAnimation.setAnimationListener(listener);
        pageView.startAnimation(alphaAnimation);

    }

    private void alpha_show() {

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(AnimHelper.DURATION);
        alphaAnimation.setAnimationListener(listener);
        pageView.startAnimation(alphaAnimation);
    }

}

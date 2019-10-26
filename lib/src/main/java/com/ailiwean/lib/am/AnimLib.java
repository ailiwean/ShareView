package com.ailiwean.lib.am;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import com.ailiwean.lib.utils.Utils;
import com.ailiwean.lib.callback.AnimStateListener;

class AnimLib {

    int type;

    //是否进入动画
    boolean isEnter;

    //是否是当前栈顶的View动画
    boolean isTopTask;

    View pageView;

    AnimStateListener animStateListener;

    long duration;

    Animation.AnimationListener listener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            if (!isEnter)
                animStateListener.exitAnimStar(pageView, isTopTask);
            else
                animStateListener.enterAnimStar(pageView, isTopTask);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (!isEnter)
                animStateListener.exitAnimEnd(pageView, isTopTask);
            else
                animStateListener.enterAnimEnd(pageView, isTopTask);
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

    public AnimLib isTopTask(boolean isTopTask) {
        this.isTopTask = isTopTask;
        return this;
    }

    public AnimLib isDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public void start() {

        if (pageView == null)
            return;

        pageView.clearAnimation();

        Animation animation = null;

        if (type == AnimHelper.ALPHA_DOWN_HIDE) {
            animation = aplha_down_hide();
        }

        if (type == AnimHelper.ALPHA_UP_SHOW) {
            animation = alpha_up_show();
        }

        if (type == AnimHelper.LEFT_ALL_SHOW) {
            animation = left_all_show();
        }

        if (type == AnimHelper.RIGHT_ALL_HIDE) {
            animation = right_all_hide();
        }

        if (type == AnimHelper.RIGHT_HALF_SHOW) {
            animation = right_half_show();
        }

        if (type == AnimHelper.LEFT_HALF_HIDE) {
            animation = left_half_hide();
        }

        if (animation == null)
            return;

        animation.setAnimationListener(listener);
        pageView.startAnimation(animation);
    }

    private Animation aplha_down_hide() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(duration);

        TranslateAnimation tranAnimation = new TranslateAnimation(0f, 0f, 0f, 300f);
        tranAnimation.setDuration(duration);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(alphaAnimation);
        set.addAnimation(tranAnimation);
        set.setAnimationListener(listener);

        return set;
    }

    private Animation alpha_up_show() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(duration);
        TranslateAnimation tranAnimation = new TranslateAnimation(0f, 0f, 300f, 0);
        tranAnimation.setDuration(duration);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(alphaAnimation);
        set.addAnimation(tranAnimation);
        set.setAnimationListener(listener);

        return set;
    }

    private Animation left_all_show() {
        TranslateAnimation translateAnimation = new TranslateAnimation(Utils.getScreenPoint().x, 0, 0, 0);
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }

    private Animation right_all_hide() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0, Utils.getScreenPoint().x, 0, 0);
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }

    private Animation right_half_show() {

        TranslateAnimation translateAnimation = new TranslateAnimation(-Utils.getScreenPoint().x / 2F, 0, 0, 0);
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }

    private Animation left_half_hide() {

        TranslateAnimation translateAnimation = new TranslateAnimation(0, -Utils.getScreenPoint().x / 2F, 0, 0);
        translateAnimation.setDuration(duration);
        return translateAnimation;
    }
}

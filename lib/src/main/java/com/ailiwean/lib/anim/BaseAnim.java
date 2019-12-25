package com.ailiwean.lib.anim;

import android.view.View;

import com.ailiwean.lib.interfaces.AnimInnerListener;
import com.ailiwean.lib.interfaces.AnimStateListener;

import java.util.HashMap;

public abstract class BaseAnim implements AnimStateListener, AnimInnerListener {

    HashMap<View, Runnable> endBackMaps = new HashMap<>();

    HashMap<View, Runnable> startBackMaps = new HashMap<>();

    int duration = 300;

    //动画优先级
    int priority = 1;  //new出来的>com>自带

    public final int getPriority() {
        return priority;
    }

    public final BaseAnim setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public BaseAnim() {
        priority = 10;
    }

    public BaseAnim(int duration) {
        this.duration = duration;
        this.priority = 10;
    }

    /***
     * 为库内提供的动画状态的监听，由{@link com.ailiwean.lib.base.BaseDelegate  调用}
     * @param endBack
     */
    public final void operatorEndBack(Runnable endBack, View view) {
        endBackMaps.put(view, endBack);
    }

    public final void opetatorStartBack(Runnable startBack, View view) {
        startBackMaps.put(view, startBack);
    }

    /***
     * 保留库内回调
     * 动画开始时保证View可视，动画结束在该类下不予处理
     *
     */
    private void enterAnimStar_Inner(View pageView) {

        pageView.setVisibility(View.VISIBLE);
        Runnable run = startBackMaps.get(pageView);
        if (run != null) {
            run.run();
        }
    }

    private void enterAnimEnd_Inner(View pageView) {

        Runnable run = endBackMaps.get(pageView);
        if (run != null) {
            run.run();
        }
    }

    private void exitAnimEnd_Inner(View pageView) {

        Runnable run = endBackMaps.get(pageView);
        if (run != null) {
            run.run();
        }

    }

    private void exitAnimStar_Inner(View pageView) {

        pageView.setVisibility(View.VISIBLE);
        Runnable run = startBackMaps.get(pageView);
        if (run != null) {
            run.run();
        }
    }

    /***
     * 自定义选择实现
     * @param view
     */
    @Override
    public void enterAnimStar(View view, boolean isTaskTop) {

    }

    @Override
    public void enterAnimEnd(View view, boolean isTaskTop) {

    }

    @Override
    public void exitAnimStar(View view, boolean isTaskTop) {

    }

    @Override
    public void exitAnimEnd(View view, boolean isTaskTop) {

    }

    /***
     * 子类的最终调用
     * @param view
     * @param isTaskTop
     */
    final void finalEnterAnimStar(View view, boolean isTaskTop) {
        enterAnimStar_Inner(view);
        enterAnimStar(view, isTaskTop);
    }

    final void finalEnterAnimEnd(View view, boolean isTaskTop) {
        enterAnimEnd_Inner(view);
        enterAnimEnd(view, isTaskTop);
    }

    final void finalExitAnimStar(View view, boolean isTaskTop) {
        exitAnimStar_Inner(view);
        exitAnimStar(view, isTaskTop);
    }

    final void finalExitAnimEnd(View view, boolean isTaskTop) {
        exitAnimEnd_Inner(view);
        exitAnimEnd(view, isTaskTop);
    }

}
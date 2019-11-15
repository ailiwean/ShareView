package com.ailiwean.lib.adapter;

import android.view.View;

import com.ailiwean.lib.am.AnimHelper;
import com.ailiwean.lib.am.CustomAnim;
import com.ailiwean.lib.am.DefaultAnim;
import com.ailiwean.lib.base.BaseAdapter;
import com.ailiwean.lib.delegate.ShareTaskDelegate;
import com.ailiwean.lib.holder.TaskViewHolder;
import com.ailiwean.lib.observe.TaskObserve;

public abstract class TaskAdapter extends BaseAdapter<ShareTaskDelegate.TaskBuild, ShareTaskDelegate, TaskViewHolder, TaskObserve> {

    protected TaskAdapter subscribe(TaskObserve<?> observe) {
        getBaseObserves().put(observe.getType(), observe);
        return this;
    }

    /***
     * 以下为默认动画配置
     * @return
     */
    public @AnimHelper.Type
    int taskTopExit() {
        return AnimHelper.NULL;
    }

    public @AnimHelper.Type
    int taskTopEnter() {
        return AnimHelper.NULL;
    }

    public @AnimHelper.Type
    int taskInnerExit() {
        return AnimHelper.NULL;
    }

    public @AnimHelper.Type
    int taskInnerEnter() {
        return AnimHelper.NULL;
    }

    /***
     * 以下为自定义动画实现
     * @param pageView
     * @param isTopTask
     */
    public void animEnter(View pageView, boolean isTopTask) {

    }

    public void animExit(View pageView, boolean isTopTask) {

    }

    public final void finalEnterAnimStar(View pageView, boolean isTopTask) {
        build.getAnim().finalEnterAnimStar(pageView, isTopTask);
    }

    public final void finalEnterAnimEnd(View pageView, boolean isTopTask) {
        build.getAnim().finalEnterAnimEnd(pageView, isTopTask);
    }

    public final void finalExitAnimStar(View pageView, boolean isTopTask) {
        build.getAnim().finalExitAnimStar(pageView, isTopTask);
    }

    public final void finalExitAnimEnd(View pageView, boolean isTopTask) {
        build.getAnim().finalExitAnimEnd(pageView, isTopTask);
    }

    public abstract boolean useDefaultAnim();

    @Override
    public final ShareTaskDelegate.TaskBuild build() {
        super.build();

        if (build == null)
            return null;

        if (useDefaultAnim())
            build.bindAnimation(new DefaultAnim() {
                @Override
                public int taskTopExit() {
                    return TaskAdapter.this.taskTopExit();
                }

                @Override
                public int taskTopEnter() {
                    return TaskAdapter.this.taskTopEnter();
                }

                @Override
                public int taskInnerExit() {
                    return TaskAdapter.this.taskInnerExit();
                }

                @Override
                public int taskInnerEnter() {
                    return TaskAdapter.this.taskInnerEnter();
                }
            });
        else build.bindAnimation(new CustomAnim() {
            @Override
            protected void animEnter(View pageView, boolean isTopTask) {
                animExit(pageView, isTopTask);
            }

            @Override
            protected void animExit(View pageView, boolean isTopTask) {
                animExit(pageView, isTopTask);
            }
        });
        build.setFrontType(getFrontType());

        return build;
    }

    public int getFrontType() {
        return -1;
    }

    @Override
    protected final ShareTaskDelegate.TaskBuild creatBuild() {
        return ShareTaskDelegate.TaskBuild.getInstance(delege, getLayoutId(), getType());
    }
}

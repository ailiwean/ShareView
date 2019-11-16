package com.ailiwean.lib.adapter;

import com.ailiwean.lib.am.BaseAnim;
import com.ailiwean.lib.base.BaseAdapter;
import com.ailiwean.lib.delegate.ShareTaskDelegate;
import com.ailiwean.lib.holder.TaskViewHolder;
import com.ailiwean.lib.observe.TaskObserve;

public abstract class TaskAdapter extends BaseAdapter<ShareTaskDelegate.TaskBuild, ShareTaskDelegate, TaskViewHolder, TaskObserve> {

    protected TaskAdapter subscribe(TaskObserve<?> observe) {
        getBaseObserves().put(observe.getType(), observe);
        return this;
    }

    @Override
    public void init(TaskViewHolder vh) {
    }

    @Override
    public void lazy(TaskViewHolder vh) {
    }

    @Override
    public void preload(TaskViewHolder vh) {
    }

    @Override
    public final ShareTaskDelegate.TaskBuild build() {
        super.build();

        if (build == null)
            return null;

        build.setFrontType(getFrontType());
        build.setLeaveRetain(leaveRetain());

        return build;
    }

    public final void bindAnimation(BaseAnim anim) {

        if (build == null)
            return;

        build.bindAnimation(anim);
    }

    public int getFrontType() {
        return -1;
    }

    /***
     *  该页面不可视时是否保留View
     */
    public boolean leaveRetain() {
        return true;
    }

    @Override
    protected final ShareTaskDelegate.TaskBuild creatBuild() {
        return ShareTaskDelegate.TaskBuild.getInstance(delege, getLayoutId(), getType());
    }
}

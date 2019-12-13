package com.ailiwean.lib.adapter;

import android.content.Context;

import com.ailiwean.lib.anim.BaseAnim;
import com.ailiwean.lib.base.BaseAdapter;
import com.ailiwean.lib.base.BaseEvents;
import com.ailiwean.lib.delegate.ShareTaskDelegate;
import com.ailiwean.lib.holder.TaskViewHolder;
import com.ailiwean.lib.observe.TaskObserve;
import com.ailiwean.lib.utils.TypeToken;

public abstract class TaskAdapter extends BaseAdapter<ShareTaskDelegate.TaskBuild, ShareTaskDelegate, TaskViewHolder, TaskObserve> {

    private TaskViewHolder vh;

    protected TaskAdapter subscribe(TaskObserve<?> observe) {
        build.baseObserves.add(observe);
        return this;
    }

    @Override
    public final void init(TaskViewHolder vh) {
        this.vh = vh;
        init();
        matchAllEvents();
    }

    public abstract void init();
        
    @Override
    public final void lazy(TaskViewHolder vh) {
        lazy();
        matchAllEventsClear();
    }

    public abstract void lazy();

    @Override
    public final void preload(TaskViewHolder vh) {
        preload();
    }

    public abstract void preload();

    public TaskViewHolder getVh() {
        return vh;
    }

    public Context getContext() {
        return getVh().getContext();
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

    //处理缓存中事件
    private final void matchAllEvents() {
        for (BaseEvents item : build.getEventQueue()) {
            build.matchEvent(item);
        }
    }

    //处理缓存中事件， 对于无法处理的进行清除
    private final void matchAllEventsClear() {
        matchAllEvents();
        build.getEventQueue().clear();
    }

    public final void post(int type, Object o) {
        vh.getShareView().postData(type, o);
    }

    public final void postData(int type, TypeToken<?> typeToken, Object o) {
        vh.getShareView().postData(type, typeToken, o);
    }

    @Override
    protected final ShareTaskDelegate.TaskBuild creatBuild() {
        return ShareTaskDelegate.TaskBuild.getInstance(delege, getLayoutId(), getType());
    }
}

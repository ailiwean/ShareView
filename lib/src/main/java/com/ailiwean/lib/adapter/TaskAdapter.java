package com.ailiwean.lib.adapter;

import com.ailiwean.lib.base.BaseAdapter;
import com.ailiwean.lib.delegate.ShareTaskDelegate;
import com.ailiwean.lib.holder.TaskViewHolder;
import com.ailiwean.lib.observe.TaskObserve;

public class TaskAdapter extends BaseAdapter<ShareTaskDelegate.TaskBuild, ShareTaskDelegate, TaskViewHolder, TaskObserve> {


    protected TaskAdapter subscribe(TaskObserve<?> observe) {

        return this;
    }


    @Override
    public void build() {
        super.build();
    }

}

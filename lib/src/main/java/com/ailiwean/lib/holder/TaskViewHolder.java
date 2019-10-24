package com.ailiwean.lib.holder;

import android.view.View;

import com.ailiwean.lib.base.BaseViewHolder;

public class TaskViewHolder extends BaseViewHolder {

    protected TaskViewHolder(View pageView) {
        super(pageView);
    }

    public static TaskViewHolder getInstance(View pageView) {
        return new TaskViewHolder(pageView);
    }


}

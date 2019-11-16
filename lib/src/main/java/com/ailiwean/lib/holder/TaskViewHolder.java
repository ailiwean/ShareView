package com.ailiwean.lib.holder;

import android.view.View;

import com.ailiwean.lib.ShareView;
import com.ailiwean.lib.base.BaseViewHolder;

public class TaskViewHolder extends BaseViewHolder {

    protected TaskViewHolder(View pageView, ShareView shareView) {
        super(pageView, shareView);
    }

    public static TaskViewHolder getInstance(View pageView, ShareView shareView) {
        return new TaskViewHolder(pageView, shareView);
    }


}

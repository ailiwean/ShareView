package com.ailiwean.lib.holder;

import android.view.View;

import com.ailiwean.lib.base.BaseViewHolder;

public class MultViewHolder extends BaseViewHolder {

    protected MultViewHolder(View pageView) {
        super(pageView);
    }

    public static MultViewHolder getInstance(View pageView) {
        return new MultViewHolder(pageView);
    }


}

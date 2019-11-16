package com.ailiwean.lib.holder;

import android.view.View;

import com.ailiwean.lib.ShareView;
import com.ailiwean.lib.base.BaseViewHolder;

public class MultViewHolder extends BaseViewHolder {

    protected MultViewHolder(View pageView, ShareView shareView) {
        super(pageView, shareView);
    }

    public static MultViewHolder getInstance(View pageView, ShareView shareView) {
        return new MultViewHolder(pageView, shareView);
    }


}

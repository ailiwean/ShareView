package com.ailiwean.lib.adapter;

import com.ailiwean.lib.base.BaseAdapter;
import com.ailiwean.lib.delegate.ShareMultiDelegate;
import com.ailiwean.lib.holder.MultViewHolder;
import com.ailiwean.lib.observe.MultiObserve;

public class MultAdapter extends BaseAdapter<ShareMultiDelegate.MultiBuild, ShareMultiDelegate, MultViewHolder, MultiObserve> {


    protected MultAdapter subscribe(MultiObserve<?> observe) {

        return this;
    }


    @Override
    public void build() {
        super.build();
    }
}

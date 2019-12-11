package com.ailiwean.lib.adapter;

import com.ailiwean.lib.base.BaseAdapter;
import com.ailiwean.lib.delegate.ShareMultiDelegate;
import com.ailiwean.lib.holder.MultViewHolder;
import com.ailiwean.lib.observe.MultiObserve;

public abstract class MultAdapter extends BaseAdapter<ShareMultiDelegate.MultiBuild, ShareMultiDelegate, MultViewHolder, MultiObserve> {

    protected MultAdapter subscribe(MultiObserve<?> observe) {
        // getBaseObserves().put(observe.getType(), observe);

        // TODO
        return this;
    }

    @Override
    public ShareMultiDelegate.MultiBuild build() {
        return super.build();
    }

    @Override
    protected final ShareMultiDelegate.MultiBuild creatBuild() {
        return ShareMultiDelegate.MultiBuild.getInstance(delege, getLayoutId(), getType());
    }
}

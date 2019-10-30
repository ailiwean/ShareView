package com.ailiwean.lib.callback;

import com.ailiwean.lib.base.BaseViewHolder;

public interface LazyLoadInter<H extends BaseViewHolder> {

    void onLazy(H vh);

}

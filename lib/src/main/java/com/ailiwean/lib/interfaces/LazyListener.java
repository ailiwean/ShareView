package com.ailiwean.lib.interfaces;

import com.ailiwean.lib.base.BaseViewHolder;

public interface LazyListener<H extends BaseViewHolder> {

    void onLazy(H vh);

}

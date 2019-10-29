package com.ailiwean.lib.callback;

import com.ailiwean.lib.base.BaseViewHolder;

public interface LazyLoad<H extends BaseViewHolder> {

    void onLazy(H vh);

}

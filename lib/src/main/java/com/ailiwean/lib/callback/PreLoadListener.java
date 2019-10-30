package com.ailiwean.lib.callback;

import com.ailiwean.lib.base.BaseViewHolder;

public interface PreLoadListener<H extends BaseViewHolder> {

    void preLoad(H vh);

}

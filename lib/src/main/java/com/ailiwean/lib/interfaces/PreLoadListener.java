package com.ailiwean.lib.interfaces;

import com.ailiwean.lib.base.BaseViewHolder;

public interface PreLoadListener<H extends BaseViewHolder> {

    void preLoad(H vh);

}

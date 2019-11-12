package com.ailiwean.lib.interfaces;

import com.ailiwean.lib.base.BaseViewHolder;

//Layout 初始化接口
public interface InitListener<H extends BaseViewHolder> {
    void init(H vh);
}

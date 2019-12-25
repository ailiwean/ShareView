package com.ailiwean.lib.interfaces;

import com.ailiwean.lib.base.BaseViewHolder;

/***
 * 内部初始化功能性接口
 * @param <H>
 */
public interface InitListener<H extends BaseViewHolder> {
    void init(H vh);
}

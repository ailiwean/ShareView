package com.ailiwean.lib.interfaces;

import com.ailiwean.lib.base.BaseViewHolder;

/***
 * 内部预加载功能性接口
 * @param <H>
 */
public interface PreLoadListener<H extends BaseViewHolder> {

    void preLoad(H vh);

}

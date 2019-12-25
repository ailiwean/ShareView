package com.ailiwean.lib.interfaces;

import com.ailiwean.lib.base.BaseViewHolder;

/***
 * 内部懒加载功能接口
 * @param <H>
 */
public interface LazyListener<H extends BaseViewHolder> {

    void onLazy(H vh);

}

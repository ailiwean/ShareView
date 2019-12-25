package com.ailiwean.lib.interfaces;

import com.ailiwean.lib.base.BaseViewHolder;

/***
 * 外部应用的生命周期接口
 * @param <H>
 */
public interface LifeListener<H extends BaseViewHolder> {

    void onVisiable(H vh);

    void onHidden(H vh);

    void onReVisiable(H vh);

    void onReHide(H vh);
}

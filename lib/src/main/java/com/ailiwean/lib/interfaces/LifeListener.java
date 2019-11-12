package com.ailiwean.lib.interfaces;

import com.ailiwean.lib.base.BaseViewHolder;

//Layout的生命周期回调
public interface LifeListener<H extends BaseViewHolder> {

    void onVisiable(H vH);

    void onHide(H vH);

}

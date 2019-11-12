package com.ailiwean.lib.base;

import com.ailiwean.lib.interfaces.AdapterInner;

public abstract class BaseAdapter<T extends BaseBuild, M extends BaseDelegate, H extends BaseViewHolder, D extends BaseObserve> implements AdapterInner<T, M, H, D> {

    @Override
    public void build() {
    }
}

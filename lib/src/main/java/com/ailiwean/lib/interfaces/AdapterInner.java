package com.ailiwean.lib.interfaces;

import androidx.annotation.LayoutRes;

import com.ailiwean.lib.base.BaseBuild;
import com.ailiwean.lib.base.BaseDelegate;
import com.ailiwean.lib.base.BaseObserve;
import com.ailiwean.lib.base.BaseViewHolder;

public interface AdapterInner<T extends BaseBuild, M extends BaseDelegate, H extends BaseViewHolder, D extends BaseObserve> {

    void init(H vh);

    void lazy(H vh);

    void preload(H vh);

    void addLifeListener(LifeListener<H> lifeListener);

    @LayoutRes
    int getLayoutId();

    int getType();

    void injectDelegate(M t);

    T build();
}

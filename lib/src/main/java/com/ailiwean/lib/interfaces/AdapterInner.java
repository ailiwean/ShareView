package com.ailiwean.lib.interfaces;

import com.ailiwean.lib.base.BaseBuild;
import com.ailiwean.lib.base.BaseDelegate;
import com.ailiwean.lib.base.BaseObserve;
import com.ailiwean.lib.base.BaseViewHolder;

public interface AdapterInner<T extends BaseBuild, M extends BaseDelegate, H extends BaseViewHolder, D extends BaseObserve> {

    void init(InitListener<H> initListener);

    void lazy(LazyListener<H> lazyListener);

    void preload(PreLoadListener<H> preLoadListener);



    void build();
}

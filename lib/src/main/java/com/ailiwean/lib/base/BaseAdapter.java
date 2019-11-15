package com.ailiwean.lib.base;

import com.ailiwean.lib.interfaces.AdapterInner;
import com.ailiwean.lib.interfaces.InitListener;
import com.ailiwean.lib.interfaces.LazyListener;
import com.ailiwean.lib.interfaces.LifeListener;
import com.ailiwean.lib.interfaces.PreLoadListener;

import java.util.HashMap;

public abstract class BaseAdapter<T extends BaseBuild, M extends BaseDelegate, H extends BaseViewHolder, D extends BaseObserve> implements AdapterInner<T, M, H, D> {

    protected T build = creatBuild();

    //由Delegate调用时注入
    protected M delege;

    private HashMap<Class, D> baseObserves = new HashMap<>();

    @Override
    public void init(H vh) {

    }

    @Override
    public void lazy(H vh) {

    }

    @Override
    public void preload(H vh) {

    }


    protected HashMap<Class, D> getBaseObserves() {
        return baseObserves;
    }

    @Override
    public final void addLifeListener(LifeListener<H> lifeListener) {
        if (build != null)
            build.addLifeListener(lifeListener);
    }

    public final void setLifeListener(LifeListener<H> lifeListener) {
        if (build == null)
            return;
        build.lifeListeners.clear();
        build.addLifeListener(lifeListener);
    }

    @Override
    public void injectDelegate(M t) {
        this.delege = t;
    }

    @Override
    public T build() {
        if (build == null)
            return null;

        build.init(new InitListener<H>() {
            @Override
            public void init(H vh) {
                BaseAdapter.this.init(vh);
            }
        });
        build.preLoad(new PreLoadListener<H>() {
            @Override
            public void preLoad(H vh) {
                BaseAdapter.this.preload(vh);
            }
        });
        build.lazy(new LazyListener<H>() {
            @Override
            public void onLazy(H vh) {
                BaseAdapter.this.lazy(vh);
            }
        });

        return build;
    }

    protected abstract T creatBuild();
}

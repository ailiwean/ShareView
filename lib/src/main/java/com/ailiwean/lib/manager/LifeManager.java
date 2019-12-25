package com.ailiwean.lib.manager;

import android.util.SparseArray;

import com.ailiwean.lib.base.BaseBuild;
import com.ailiwean.lib.base.BaseDelegate;
import com.ailiwean.lib.interfaces.LifeListener;
import com.ailiwean.lib.interfaces.LifeListenerInner;

public class LifeManager implements LifeListenerInner {

    SparseArray<BaseBuild> buildMap;

    BaseDelegate delegate;

    private LifeManager(BaseDelegate delegate) {
        this.delegate = delegate;
        buildMap = delegate.getBuildMap();
    }

    public static LifeManager getInstance(BaseDelegate delegate) {
        return new LifeManager(delegate);
    }

    public void onVisiable(int type) {
        onVisiable(buildMap.get(type));
    }

    public void onHide(int type) {
        onVisiable(buildMap.get(type));
    }

    @Override
    public void onVisiable(BaseBuild baseBuild) {
        if (baseBuild == null)
            return;
        for (int i = 0; i < baseBuild.lifeListeners.size(); i++) {
            LifeListener listener = (LifeListener) baseBuild.lifeListeners.get(i);
            listener.onVisiable(baseBuild.getVH());
        }
    }

    @Override
    public void onHidden(BaseBuild baseBuild) {
        if (baseBuild == null)
            return;
        for (int i = 0; i < baseBuild.lifeListeners.size(); i++) {
            LifeListener listener = (LifeListener) baseBuild.lifeListeners.get(i);
            listener.onHidden(baseBuild.getVH());
        }
    }

    @Override
    public void onReVisiable(BaseBuild baseBuild) {
        if (baseBuild == null)
            return;
        for (int i = 0; i < baseBuild.lifeListeners.size(); i++) {
            LifeListener listener = (LifeListener) baseBuild.lifeListeners.get(i);
            listener.onReVisiable(baseBuild.getVH());
        }
    }

    @Override
    public void onReHide(BaseBuild baseBuild) {
        if (baseBuild == null)
            return;
        for (int i = 0; i < baseBuild.lifeListeners.size(); i++) {
            LifeListener listener = (LifeListener) baseBuild.lifeListeners.get(i);
            listener.onReHide(baseBuild.getVH());
        }
    }


    @Override
    public void onInit(BaseBuild baseBuild) {
        baseBuild.getInitListener().init(baseBuild.getVH());
        baseBuild.setInit(true);
    }

    @Override
    public void onLazy(BaseBuild baseBuild) {
        baseBuild.getLazyListener().onLazy(baseBuild.getVH());
        baseBuild.setLazy(true);
    }

    /***
     * 预加载功能，多次调用，创建一次View，回调多次preLoad()
     * @param type
     */
    @Override
    public void onPreload(int type) {

        BaseBuild baseBuild = buildMap.get(type);
        if (baseBuild == null)
            return;
        if (baseBuild.getPageView() == null) {
            delegate.inflate(baseBuild);
        }
        baseBuild.getPreLoadListener().preLoad(baseBuild.getVH());
    }


}

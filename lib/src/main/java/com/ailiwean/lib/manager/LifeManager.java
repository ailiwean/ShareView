package com.ailiwean.lib.manager;

import com.ailiwean.lib.base.BaseBuild;
import com.ailiwean.lib.base.BaseDelegate;
import com.ailiwean.lib.callback.LifeListener;
import com.ailiwean.lib.callback.LifeListenerInner;

import java.util.LinkedHashMap;

public class LifeManager implements LifeListenerInner {

    LinkedHashMap<Integer, BaseBuild> buildMap;

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
    public void onHide(BaseBuild baseBuild) {
        if (baseBuild == null)
            return;
        for (int i = 0; i < baseBuild.lifeListeners.size(); i++) {
            LifeListener listener = (LifeListener) baseBuild.lifeListeners.get(i);
            listener.onHide(baseBuild.getVH());
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

    @Override
    public void onPreload(int type) {

        BaseBuild baseBuild = buildMap.get(type);
        if (baseBuild == null)
            return;
        if (baseBuild.getPageView() == null) {
            delegate.inflate(baseBuild);
            baseBuild.getPreLoadListener().preLoad(baseBuild.getVH());
        }
    }


}
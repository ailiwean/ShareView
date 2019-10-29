package com.ailiwean.lib.manager;

import com.ailiwean.lib.base.BaseBuild;
import com.ailiwean.lib.callback.LifeListener;

import java.util.LinkedHashMap;

public class LifeManager {

    LinkedHashMap<Integer, BaseBuild> buildMap;

    private LifeManager(LinkedHashMap<Integer, BaseBuild> buildMap) {
        this.buildMap = buildMap;
    }


    public static LifeManager getInstance(LinkedHashMap<Integer, BaseBuild> buildMap) {
        return new LifeManager(buildMap);
    }

    public void onVisiable(int type) {
        onVisiable(buildMap.get(type));
    }

    public void onHide(int type) {
        onVisiable(buildMap.get(type));
    }

    public void onVisiable(BaseBuild baseBuild) {
        if (baseBuild == null)
            return;
        for (int i = 0; i < baseBuild.lifeListeners.size(); i++) {
            LifeListener listener = (LifeListener) baseBuild.lifeListeners.get(i);
            listener.onVisiable(baseBuild.getVH());
        }
    }

    public void onHide(BaseBuild baseBuild) {
        if (baseBuild == null)
            return;
        for (int i = 0; i < baseBuild.lifeListeners.size(); i++) {
            LifeListener listener = (LifeListener) baseBuild.lifeListeners.get(i);
            listener.onHide(baseBuild.getVH());
        }
    }

    public void onInit(BaseBuild baseBuild) {
        baseBuild.getInitListener().init(baseBuild.getVH());
        baseBuild.isInit = true;
    }

    public void onLazy(BaseBuild baseBuild) {
        baseBuild.getLazyLoad().onLazy(baseBuild.getVH());
        baseBuild.isLazy = true;
    }

}

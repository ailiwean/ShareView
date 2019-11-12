package com.ailiwean.lib.interfaces;

import com.ailiwean.lib.base.BaseBuild;

public interface LifeListenerInner {

    void onVisiable(BaseBuild baseBuild);

    void onHide(BaseBuild baseBuild);

    void onInit(BaseBuild baseBuild);

    void onLazy(BaseBuild baseBuild);

    void onPreload(int type);

}

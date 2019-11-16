package com.ailiwean.lib.interfaces;

import com.ailiwean.lib.base.BaseBuild;

public interface LifeListenerInner<T extends BaseBuild> {

    void onVisiable(T baseBuild);

    void onHide(T baseBuild);

    void onInit(T baseBuild);

    void onLazy(T baseBuild);

    void onPreload(int type);

}

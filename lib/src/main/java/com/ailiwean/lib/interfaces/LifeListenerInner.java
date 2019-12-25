package com.ailiwean.lib.interfaces;

import com.ailiwean.lib.base.BaseBuild;

/***
 * 内部应用生命周期接口
 * @param <T>
 */
public interface LifeListenerInner<T extends BaseBuild> {

    void onVisiable(T baseBuild);

    void onHidden(T baseBuild);

    void onReVisiable(T baseBuild);

    void onReHide(T baseBuild);

    void onInit(T baseBuild);

    void onLazy(T baseBuild);

    void onPreload(int type);

}

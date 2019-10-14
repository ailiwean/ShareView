package com.ailiwean.lib.callback;

import android.view.View;

//Layout的生命周期回调
public interface LifeListener {

    void onVisiable(View pageView);

    void onHide(View pageView);

}

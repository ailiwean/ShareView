package com.ailiwean.lib.callback;

import android.view.View;


/**
 * 该接口通过{@link com.ailiwean.lib.delegate.ShareMultiDelegate}调用提供View,最后执行内部方法
 */
public interface AnimInnerListener {

    void enter(View pageView);

    void exit(View pageView);

}

package com.ailiwean.lib.callback;


import com.ailiwean.lib.am.AnimHelper;

/***
 * 该接口提供外部调用，返回自带动画Type
 */
public interface AnimOutListener {
    @AnimHelper.Type
    int taskInnerEnter();

    @AnimHelper.Type
    int taskInnerExit();

    @AnimHelper.Type
    int taskTopEnter();

    @AnimHelper.Type
    int taskTopExit();

}

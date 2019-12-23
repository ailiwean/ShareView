package com.ailiwean.lib.anim;

import androidx.annotation.IntDef;

public class AnimHelper {

    //无动画
    public static final int NULL = 0;
    //右退出
    public static final int RIGHT_ALL_HIDE = 10000;
    //左进入
    public static final int LEFT_ALL_SHOW = 10001;
    //透明下退出
    public static final int ALPHA_DOWN_HIDE = 10002;
    //透明上进入
    public static final int ALPHA_UP_SHOW = 10003;
    //右一半进入
    public static final int RIGHT_HALF_SHOW = 10004;
    //左一半退出
    public static final int LEFT_HALF_HIDE = 10005;

    @IntDef({NULL, ALPHA_DOWN_HIDE, ALPHA_UP_SHOW, RIGHT_ALL_HIDE, LEFT_ALL_SHOW, RIGHT_HALF_SHOW, LEFT_HALF_HIDE})
    public @interface Type {
    }

}

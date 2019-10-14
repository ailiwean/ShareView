package com.ailiwean.lib.animation;

import androidx.annotation.IntDef;

public class AnimHelper {

    //透明度(0-1)
    public static final int ALPHA_SHOW = 10000;

    //透明度(1-0)
    public static final int ALPHA_HIDE = 10001;

    //朝上
    public static final int UP_TURN = 10002;

    //朝下
    public static final int DOWN_TURN = 10003;

    //朝左
    public static final int LEFT_TURN = 10004;

    //朝右
    public static final int RIGHT_TURN = 10005;

    public static final int DURATION = 300;

    @IntDef({ALPHA_SHOW, ALPHA_HIDE, UP_TURN, DOWN_TURN, LEFT_TURN, RIGHT_TURN})
    public @interface Type {
    }

}

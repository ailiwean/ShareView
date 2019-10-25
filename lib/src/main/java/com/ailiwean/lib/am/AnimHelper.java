package com.ailiwean.lib.am;

import androidx.annotation.IntDef;

public class AnimHelper {

    public static final int NULL = 0;

    public static final int ALPHA_DOWN_HIDE = 10000;

    public static final int ALPHA_UP_SHOW = 100001;

    public static final int RIGHT_ALL_HIDE = 100002;

    public static final int LEFT_ALL_SHOW = 100003;

    public static final int RIGHT_HALF_SHOW = 10004;

    public static final int LEFT_HALF_HIDE = 10005;

    @IntDef({NULL, ALPHA_DOWN_HIDE, ALPHA_UP_SHOW, RIGHT_ALL_HIDE, LEFT_ALL_SHOW, RIGHT_HALF_SHOW, LEFT_HALF_HIDE})
    public @interface Type {
    }

}

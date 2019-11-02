package com.ailiwean.lib.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

public class Utils {


    private static Point point = new Point();

    public static void init(Context mContext) {
        getScreenPoint(mContext);
    }

    private static void getScreenPoint(Context mContext) {

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null)
            return;
        wm.getDefaultDisplay().getSize(point);
    }

    public static int getX() {
        return point.x;
    }


    public static int getY() {
        return point.y;
    }


}

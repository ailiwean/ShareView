package com.ailiwean.lib.Utils;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

public class Utils {

    private static WeakReference<Context> weakReference;

    public static void init(Context mContext) {
        weakReference = new WeakReference<>(mContext);
    }

    public static Point getScreenPoint() {

        if (weakReference.get() == null)
            return new Point();
        WindowManager wm = (WindowManager) weakReference.get().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null)
            return new Point();
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point;

    }


}

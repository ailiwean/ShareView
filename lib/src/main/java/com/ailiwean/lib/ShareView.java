package com.ailiwean.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.ailiwean.lib.utils.Utils;
import com.ailiwean.lib.delegate.ShareMultiDelegate;
import com.ailiwean.lib.delegate.ShareTaskDelegate;

public class ShareView extends FrameLayout {

    //状态多布局View
    ShareMultiDelegate multiDelegate;

    //栈模型View
    ShareTaskDelegate taskDelegate;

    public ShareView(Context context) {
        super(context);
        init();
    }

    public ShareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Utils.init(getContext());
    }

    public ShareMultiDelegate getMultiDelegate() {
        multiDelegate = ShareMultiDelegate.getInstance(this);
        return multiDelegate;
    }

    public ShareTaskDelegate getTaskDelegate() {
        taskDelegate = ShareTaskDelegate.getInstance(this);
        return taskDelegate;
    }

    public void switchType(int type) {

        if (multiDelegate != null)
            multiDelegate.switchType(type);

        if (taskDelegate != null)
            taskDelegate.switchType(type);

    }

    public int getCurrentType() {

        int currentType = 0;

        if (multiDelegate != null)
            currentType = multiDelegate.getCurrentType();

        if (taskDelegate != null)
            currentType = taskDelegate.getCurrentType();

        return currentType;
    }

    public void postData(int type, Object data) {

        if (multiDelegate != null)
            multiDelegate.postData(type, data);
        if (taskDelegate != null)
            taskDelegate.postData(type, data);

    }

    public void preload(int type) {

        if (multiDelegate != null)
            multiDelegate.onPreload(type);

        if (taskDelegate != null)
            taskDelegate.onPreload(type);

    }


    public boolean back() {

        if (multiDelegate != null)
            return false;

        if (taskDelegate != null)
            return taskDelegate.back();

        return false;
    }


}

package com.ailiwean.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.ailiwean.lib.delegate.ShareMultiDelegate;

public class ShareView extends FrameLayout {

    ShareMultiDelegate delegate;

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
        delegate = ShareMultiDelegate.getInstance(this);
    }

    public ShareMultiDelegate getMultiDelegate() {
        return delegate;
    }

    public void switchType(int type) {
        delegate.switchType(type);
    }

    public int getCurrentType() {
        return delegate.getCurrentType();
    }

}

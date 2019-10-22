package com.ailiwean.lib.delegate;

import android.view.View;
import android.widget.FrameLayout;

import com.ailiwean.lib.base.BaseBuild;
import com.ailiwean.lib.base.BaseMultiDelegate;
import com.ailiwean.lib.callback.LifeListener;

public class ShareMultiDelegate extends BaseMultiDelegate<ShareMultiDelegate, ShareMultiDelegate.MultiBuild> {

    MultiBuild lastBuild;

    private ShareMultiDelegate(FrameLayout mControlView) {
        super(mControlView);

    }

    @Override
    protected MultiBuild creatBuild(ShareMultiDelegate delegate, int layout, int type) {
        return new MultiBuild(delegate, layout, type);
    }

    public static ShareMultiDelegate getInstance(FrameLayout controlView) {
        return new ShareMultiDelegate(controlView);
    }
        
    @Override
    public void dispatchShowView(int type) {

        final MultiBuild build = getBuild(type);

        if (lastBuild == build)
            return;

        //回调上个页面的隐藏方法
        if (lastBuild != null && lastBuild.lifeListeners.size() != 0) {
            for (LifeListener item : lastBuild.lifeListeners)
                item.onHide(lastBuild.view);
        }

        if (build.lifeListeners.size() != 0)
            for (LifeListener item : build.lifeListeners)
                item.onVisiable(build.view);

        if (lastBuild != null)
            lastBuild.view.setVisibility(View.INVISIBLE);
        build.view.setVisibility(View.VISIBLE);

        lastBuild = build;
    }

    public static class MultiBuild extends BaseBuild<ShareMultiDelegate> {

        protected MultiBuild(ShareMultiDelegate delegate, int layout, int type) {
            super(delegate, layout, type);
        }
    }

}

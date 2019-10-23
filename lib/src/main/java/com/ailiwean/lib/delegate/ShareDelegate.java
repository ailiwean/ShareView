package com.ailiwean.lib.delegate;

import android.view.View;
import android.widget.FrameLayout;

import com.ailiwean.lib.base.BaseBuild;
import com.ailiwean.lib.base.BaseDelegate;
import com.ailiwean.lib.callback.LifeListener;

public class ShareDelegate extends BaseDelegate<ShareDelegate, ShareDelegate.MultiBuild> {

    MultiBuild lastBuild;

    private ShareDelegate(FrameLayout mControlView) {
        super(mControlView);

    }

    @Override
    protected MultiBuild creatBuild(ShareDelegate delegate, int layout, int type) {
        return new MultiBuild(delegate, layout, type);
    }

    public static ShareDelegate getInstance(FrameLayout controlView) {
        return new ShareDelegate(controlView);
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

    public static class MultiBuild extends BaseBuild<MultiBuild, ShareDelegate> {

        protected MultiBuild(ShareDelegate delegate, int layout, int type) {
            super(delegate, layout, type);
        }
    }

}

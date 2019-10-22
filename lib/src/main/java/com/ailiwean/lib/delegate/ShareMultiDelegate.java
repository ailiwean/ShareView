package com.ailiwean.lib.delegate;

import android.widget.FrameLayout;

import com.ailiwean.lib.base.BaseBuild;
import com.ailiwean.lib.base.BaseMultiDelegate;

public class ShareMultiDelegate extends BaseMultiDelegate<ShareMultiDelegate, ShareMultiDelegate.MultiBuild> {

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

//        final Build build = buildMap.get(type);
//        if (build == null)
//            return;
//
//        lazyCreat(build);
//
//        if (lastBuild == build)
//            return;
//
//        if (lastBuild != null)
//            lastBuild.anim.exit(lastBuild.view);
//
//        //回调上个页面的隐藏方法
//        if (lastBuild != null && lastBuild.lifeListeners.size() != 0) {
//            for (LifeListener item : lastBuild.lifeListeners)
//                item.onHide(lastBuild.view);
//        }
//
//        //执行动画并回调
//        build.anim.enter(build.view);
//
//        build.anim.operatorLastBuildBack(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//
//
//        if (build.lifeListeners.size() != 0)
//            for (LifeListener item : build.lifeListeners)
//                item.onVisiable(build.view);
//
//        lastBuild = build;

    }

    public static class MultiBuild extends BaseBuild<ShareMultiDelegate> {

        protected MultiBuild(ShareMultiDelegate delegate, int layout, int type) {
            super(delegate, layout, type);
        }
    }


}

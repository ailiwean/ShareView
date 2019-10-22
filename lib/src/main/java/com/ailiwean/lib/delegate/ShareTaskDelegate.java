package com.ailiwean.lib.delegate;

import android.view.View;
import android.widget.FrameLayout;

import com.ailiwean.lib.animation.CustomAnim;
import com.ailiwean.lib.animation.NullAnim;
import com.ailiwean.lib.base.BaseBuild;
import com.ailiwean.lib.base.BaseMultiDelegate;
import com.ailiwean.lib.callback.LifeListener;

public class ShareTaskDelegate extends BaseMultiDelegate<ShareTaskDelegate, ShareTaskDelegate.TaskBuild> {

    TaskBuild lastBuild;

    protected ShareTaskDelegate(FrameLayout controlView) {
        super(controlView);
    }

    @Override
    protected TaskBuild creatBuild(ShareTaskDelegate delegate, int layout, int type) {
        return new TaskBuild(delegate, layout, type);
    }

    public static ShareTaskDelegate getInstance(FrameLayout controlView) {
        return new ShareTaskDelegate(controlView);
    }

    @Override
    public void dispatchShowView(int type) {

        final TaskBuild build = getBuild(type);

        lazyCreat(build);

        if (lastBuild == build)
            return;

        if (lastBuild != null)
            lastBuild.anim.exit(lastBuild.view);

        //回调上个页面的隐藏方法
        if (lastBuild != null && lastBuild.lifeListeners.size() != 0) {
            for (LifeListener item : lastBuild.lifeListeners)
                item.onHide(lastBuild.view);
        }

        //执行动画并回调
        build.anim.enter(build.view);

        build.anim.operatorLastBuildBack(new Runnable() {
            @Override
            public void run() {

            }
        });


        if (build.lifeListeners.size() != 0)
            for (LifeListener item : build.lifeListeners)
                item.onVisiable(build.view);

        lastBuild = build;

    }

    public static class TaskBuild extends BaseBuild<ShareTaskDelegate> {

        CustomAnim anim = new NullAnim();

        protected TaskBuild(ShareTaskDelegate delegate, int layout, int type) {
            super(delegate, layout, type);
        }


        /***
         * 绑定一个动画效果
         */
        public BaseBuild bindAnimation(CustomAnim anim) {
            if (anim != null)
                this.anim = anim;
            return this;
        }

        @Override
        protected void bindInstanceView(View view) {
            super.bindInstanceView(view);
            anim.injectPageView(view);
        }
    }

}

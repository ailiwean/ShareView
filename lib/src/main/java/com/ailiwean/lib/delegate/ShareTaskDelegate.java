package com.ailiwean.lib.delegate;

import android.view.View;
import android.widget.FrameLayout;

import com.ailiwean.lib.animation.CustomAnim;
import com.ailiwean.lib.animation.NullAnim;
import com.ailiwean.lib.base.BaseBuild;
import com.ailiwean.lib.base.BaseMultiDelegate;

public class ShareTaskDelegate extends BaseMultiDelegate<ShareTaskDelegate.TaskBuild> {

    protected ShareTaskDelegate(FrameLayout controlView) {
        super(controlView);
    }

    @Override
    protected TaskBuild creatBuild(BaseMultiDelegate delegate, int layout, int type) {
        return TaskBuild.creat(delegate, layout, type);
    }

    public static ShareTaskDelegate getInstance(FrameLayout controlView) {
        return new ShareTaskDelegate(controlView);
    }

    @Override
    public void dispatchShowView(int type) {

    }

    public static class TaskBuild extends BaseBuild {

        CustomAnim anim = new NullAnim();

        protected TaskBuild(BaseMultiDelegate delegate, int layout, int type) {
            super(delegate, layout, type);
        }

        public static TaskBuild creat(BaseMultiDelegate delegate, int layout, int type) {
            return new TaskBuild(delegate, layout, type);
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

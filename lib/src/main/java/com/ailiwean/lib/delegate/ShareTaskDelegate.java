package com.ailiwean.lib.delegate;

import android.view.View;
import android.widget.FrameLayout;

import com.ailiwean.lib.am.CustomAnim;
import com.ailiwean.lib.am.NullAnim;
import com.ailiwean.lib.base.BaseBuild;
import com.ailiwean.lib.base.BaseDelegate;
import com.ailiwean.lib.callback.LifeListener;

public class ShareTaskDelegate extends BaseDelegate<ShareTaskDelegate, ShareTaskDelegate.TaskBuild> {

    TaskBuild lastBuild;

    protected ShareTaskDelegate(FrameLayout controlView) {
        super(controlView);
    }

    @Override
    protected TaskBuild creatBuild(ShareTaskDelegate delegate, int layout, int type) {
        return new TaskBuild(delegate, layout, type, buildMap.size());
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

        //回调当前页面的展示方法
        if (build.lifeListeners.size() != 0)
            for (LifeListener item : build.lifeListeners)
                item.onVisiable(build.view);

        //首次进入
        if (lastBuild == null) {
            build.anim.enter(build.view, false);
            lastBuild = build;
            return;
        }

        //回调上个页面的隐藏方法
        if (lastBuild.lifeListeners.size() != 0) {
            for (LifeListener item : lastBuild.lifeListeners)
                item.onHide(lastBuild.view);
        }

        //返回操作
        if (lastBuild.taskIndex > build.taskIndex) {
            lastBuild.anim.exit(lastBuild.view, true);
            lastBuild.anim.operatorEndBack(new Runnable() {
                @Override
                public void run() {
                    lastBuild.view.clearAnimation();
                    lastBuild.view.setVisibility(View.INVISIBLE);
                    lastBuild = build;
                }
            });
            build.anim.enter(build.view, true);
        }
        //进入操作
        else {
            lastBuild.anim.exit(lastBuild.view, true);
            build.anim.enter(build.view, true);
            build.anim.operatorEndBack(new Runnable() {
                @Override
                public void run() {
                    lastBuild.view.clearAnimation();
                    lastBuild.view.setVisibility(View.INVISIBLE);
                    lastBuild = build;
                }
            });
        }
    }

    /***
     * 栈结构按照注解布局顺序依次添加
     * @param type
     * @return
     */
    @Override
    public final ShareTaskDelegate setDefault(int type) {
        return super.setDefault(-1);
    }

    /***
     * 栈结构不允许复用
     * @param isReuseLayout
     * @return
     */
    @Override
    public ShareTaskDelegate isReuseLayout(boolean isReuseLayout) {
        return super.isReuseLayout(false);
    }

    public static class TaskBuild extends BaseBuild<TaskBuild, ShareTaskDelegate> {

        CustomAnim anim = new NullAnim();

        int taskIndex;

        protected TaskBuild(ShareTaskDelegate delegate, int layout, int type, int taskIndex) {
            super(delegate, layout, type);
            this.taskIndex = taskIndex;
        }

        /***
         * 绑定一个动画效果
         */
        public TaskBuild bindAnimation(CustomAnim anim) {
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

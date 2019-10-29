package com.ailiwean.lib.delegate;

import android.view.View;
import android.widget.FrameLayout;

import com.ailiwean.lib.am.BaseAnim;
import com.ailiwean.lib.am.CustomAnim;
import com.ailiwean.lib.am.DefaultAnim;
import com.ailiwean.lib.am.NullAnim;
import com.ailiwean.lib.base.BaseBuild;
import com.ailiwean.lib.base.BaseDelegate;
import com.ailiwean.lib.base.BaseViewHolder;
import com.ailiwean.lib.callback.LifeListener;
import com.ailiwean.lib.holder.TaskViewHolder;
import com.ailiwean.lib.observe.TaskObserve;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ShareTaskDelegate extends BaseDelegate<ShareTaskDelegate, ShareTaskDelegate.TaskBuild> {

    TaskBuild lastBuild;

    //所有布局通用的Anim
    BaseAnim comAnim;

    long currentTime;

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
    protected void dispatchShowView(final int type) {

        final TaskBuild build = getBuild(type);

        lazyCreat(build);

        if (lastBuild == build)
            return;

        //首次进入
        if (lastBuild == null) {
            build.anim.enter(build.getPageView(), true, false);
            onLazy(build);
            onVisiable(build);
            lastBuild = build;
            return;
        }

        //返回操作
        if (lastBuild.taskIndex > build.taskIndex) {
            build.anim.opetatorStartBack(new Runnable() {
                @Override
                public void run() {
                    build.setRunning(true);
                }
            }, build.getPageView());

            build.anim.operatorEndBack(new Runnable() {
                @Override
                public void run() {
                    build.setRunning(false);
                    //回调声明周期
                }
            }, build.getPageView());
            build.anim.enter(build.getPageView(), false, true);

            lastBuild.anim.opetatorStartBack(new Runnable() {
                @Override
                public void run() {
                    lastBuild.setRunning(true);
                }
            }, lastBuild.getPageView());

            //返回动画结束后才将lastBuild指向当前页
            lastBuild.anim.operatorEndBack(new Runnable() {
                @Override
                public void run() {
                    lastBuild.getPageView().setVisibility(View.INVISIBLE);
                    lastBuild.setRunning(false);
                    onHide(lastBuild);
                    onVisiable(build);
                    lastBuild = build;
                }
            }, lastBuild.getPageView());
            lastBuild.anim.exit(lastBuild.getPageView(), true, true);

        }
        //进入操作
        else {
            //保存last临时变量, 进入也动画<退出页，页将last置为当前,从而回调时发生问题
            final TaskBuild temBuild = lastBuild;
            lastBuild.anim.opetatorStartBack(new Runnable() {
                @Override
                public void run() {
                    temBuild.setRunning(true);
                }
            }, lastBuild.getPageView());
            lastBuild.anim.operatorEndBack(new Runnable() {
                @Override
                public void run() {
                    temBuild.setRunning(false);
                }
            }, lastBuild.getPageView());
            lastBuild.anim.exit(lastBuild.getPageView(), false, true);

            build.anim.opetatorStartBack(new Runnable() {
                @Override
                public void run() {
                    build.setRunning(true);
                }
            }, build.getPageView());
            //进入动画结束后才将lastBuild指向当前页
            build.anim.operatorEndBack(new Runnable() {
                @Override
                public void run() {
                    lastBuild.getPageView().setVisibility(View.INVISIBLE);
                    onVisiable(build);
                    onHide(lastBuild);
                    lastBuild = build;
                    if (!build.isLazy)
                        onLazy(build);
                    build.setRunning(false);
                }
            }, build.getPageView());

            build.anim.enter(build.getPageView(), true, true);
        }

        updateCurrentType(type);
    }

    @Override
    public void switchType(int type) {

        TaskBuild build = getBuild(type);
        if (build == null)
            return;

        if (System.currentTimeMillis() - currentTime < 50)
            return;
        currentTime = System.currentTimeMillis();

        if (lastBuild != null) {
            if (lastBuild.isRunning || getBuild(getCurrentType()).isRunning)
                return;
        }

        //懒加载View,确保切换前View存在
        lazyCreat(build);
        dispatchShowView(type);
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

    /***
     * 为所有布局设定通用动画
     * @param customAnim
     * @return
     */
    public ShareTaskDelegate bindCommonAnimation(BaseAnim customAnim) {
        this.comAnim = customAnim;
        this.comAnim.setPriority(5);
        return this;
    }

    @Override
    public void go() {
        initAnim();
        super.go();
    }

    private void initAnim() {

        List<TaskBuild> baseBuildList = getBuildLinkList();
        //设定通用动画
        for (TaskBuild item : baseBuildList) {
            item.bindAnimation(comAnim);
        }
    }

    public static class TaskBuild extends BaseBuild<TaskBuild, ShareTaskDelegate, TaskViewHolder, TaskObserve> {

        private BaseAnim anim = new NullAnim();

        boolean isRunning = false;

        int taskIndex;

        protected TaskBuild(ShareTaskDelegate delegate, int layout, int type, int taskIndex) {
            super(delegate, layout, type);
            this.taskIndex = taskIndex;
            anim.setPriority(1);
        }

        TaskBuild setRunning(boolean running) {
            isRunning = running;
            return this;
        }

        /***
         * 绑定一个动画效果
         */
        public TaskBuild bindAnimation(BaseAnim anim) {
            if (anim != null && anim.getPriority() > this.anim.getPriority())
                this.anim = anim;
            return this;
        }

        public TaskBuild subscibe(TaskObserve<?> baseObserve) {
            baseObserves.put(baseObserve.getType(), baseObserve);
            return this;
        }

        @Override
        protected TaskViewHolder creatViewHolder(View pageView) {
            return TaskViewHolder.getInstance(pageView);
        }

        @Override
        protected void bindInstanceView(View view) {
            super.bindInstanceView(view);
        }
    }

}

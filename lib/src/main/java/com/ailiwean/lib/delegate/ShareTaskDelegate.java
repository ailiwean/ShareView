package com.ailiwean.lib.delegate;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;

import com.ailiwean.lib.adapter.TaskAdapter;
import com.ailiwean.lib.am.BaseAnim;
import com.ailiwean.lib.am.NullAnim;
import com.ailiwean.lib.base.BaseBuild;
import com.ailiwean.lib.base.BaseDelegate;
import com.ailiwean.lib.interfaces.RollBackInter;
import com.ailiwean.lib.holder.TaskViewHolder;
import com.ailiwean.lib.manager.LifeManager;
import com.ailiwean.lib.manager.RollBackManager;
import com.ailiwean.lib.observe.TaskObserve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShareTaskDelegate extends BaseDelegate<ShareTaskDelegate, ShareTaskDelegate.TaskBuild> implements RollBackInter {

    TaskBuild lastBuild;

    //所有布局通用的Anim
    BaseAnim comAnim;

    RollBackManager rollBackManager;

    long currentTime;

    TaskBuild rootBuild;

    protected ShareTaskDelegate(FrameLayout controlView) {
        super(controlView);
        rollBackManager = RollBackManager.getInstance(this);
    }

    @Override
    protected TaskBuild creatBuild(ShareTaskDelegate delegate, int layout, int type) {
        return new TaskBuild(delegate, layout, type);
    }

    public static ShareTaskDelegate getInstance(FrameLayout controlView) {
        return new ShareTaskDelegate(controlView);
    }

    /***
     * 注册根Layout
     * @param type
     * @param layoutId
     * @return
     */
    public TaskBuild regRootLayout(int type, @LayoutRes int layoutId) {
        return regRootLayout(null, type, layoutId);
    }

    public TaskBuild regRootLayout(TaskBuild build, int type, @LayoutRes int layoutId) {
        if (build == null)
            build = creatBuild(this, layoutId, type);
        typeMap.put(type, layoutId);
        rootBuild = build;
        return build;
    }

    /***
     * 手动控制顺序  也可使用新的可指定front{@link #regLayout(int, int, int)}
     * @param type
     * @param layoutId
     * @return
     */
    @Override
    public TaskBuild regLayout(int type, int layoutId) {
        if (rootBuild == null) {
            regRootLayout(type, layoutId);
            return rootBuild;
        } else {
            List<TaskBuild> taskBuildList = getBuildLinkList();
            int lastFrontType;
            if (taskBuildList.size() == 0)
                lastFrontType = rootBuild.getType();
            else
                lastFrontType = taskBuildList.get(taskBuildList.size() - 1).getFrontType();
            regLayout(type, layoutId, lastFrontType);
            return buildMap.get(type);
        }
    }

    public TaskBuild regLayout(int type, int layoutId, int frontType) {
        return regLayout(null, type, layoutId, frontType);
    }

    private TaskBuild regLayout(TaskBuild build, int type, int layoutId, int frontType) {
        if (build == null)
            build = creatBuild(this, layoutId, type);
        typeMap.put(type, layoutId);
        buildMap.put(type, build);
        build.frontType = frontType;
        return build;
    }

    /***
     * 注册适配器, 单独的Page配置管理
     * @return
     */
    public TaskBuild regAdapter(TaskAdapter adapter) {

        adapter.injectDelegate(this);
        //非Root
        if (adapter.getFrontType() != -1)
            return regLayout(adapter.build(), adapter.getType(), adapter.getLayoutId(), adapter.getFrontType());

        else return regRootLayout(adapter.build(), adapter.getType(), adapter.getLayoutId());

    }

    @Override
    protected void dispatchShowView(final int type) {

        final TaskBuild build = getBuild(type);

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
                    if (!build.isLazy())
                        onLazy(build);
                    onVisiable(build);
                    onHide(lastBuild);
                    lastBuild = build;
                    build.setRunning(false);
                }
            }, build.getPageView());

            build.anim.enter(build.getPageView(), true, true);
        }

        updateCurrentType(type);
    }

    @Override
    public void goTo(int type) {

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

        record(type);
    }

    @Override
    public void record(int type) {
        //委托返回栈
        rollBackManager.record(type);
    }

    @Override
    public boolean back() {
        return rollBackManager.back();
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

    @Override
    public void onHide(TaskBuild baseBuild) {
        super.onHide(baseBuild);
        if (!baseBuild.leaveRetain)
            baseBuild.destory();
    }

    /***
     * 为所有布局设定通用动画
     * @param customAnim 通用动画
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
        buildSort();
        super.go();
    }

    /***
     * 将创建时无序的Build重置为有序的栈结构
     */
    private void buildSort() {

        if (rootBuild == null) {
            try {
                throw new Exception("must add a Root Layout");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        List<TaskBuild> temList = new ArrayList<>();
        temList.add(rootBuild);
        reCurSort(temList, rootBuild);

        LinkedHashMap<Integer, TaskBuild> newBuildMap = new LinkedHashMap<>();
        int taskIndex = 0;
        for (TaskBuild tem : temList) {
            tem.taskIndex = taskIndex;
            taskIndex++;
            newBuildMap.put(tem.getType(), tem);
        }
        buildMap = newBuildMap;

        //buildMap发生更改， 重置lifeMananger
        lifeManager = LifeManager.getInstance(this);
    }

    /***
     * 递归RootBuild为入口， 将FrontType指向的Type排序
     * @param temList
     */
    private void reCurSort(List<TaskBuild> temList, TaskBuild build) {

        @SuppressLint("UseSparseArrays")
        HashMap<Integer, TaskBuild> temMap = new HashMap<>(buildMap);
        Iterator<Map.Entry<Integer, TaskBuild>> iterator = temMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, TaskBuild> item = iterator.next();
            if (item.getValue().getFrontType() == build.getType()) {
                temList.add(item.getValue());
                //添加后移除BuildMap里的
                buildMap.remove(item.getKey());
                reCurSort(temList, item.getValue());
            }
        }
    }

    /***
     * 设定通用动画
     */
    private void initAnim() {

        if (comAnim == null)
            return;

        List<TaskBuild> baseBuildList = getBuildLinkList();
        //设定通用动画
        for (TaskBuild item : baseBuildList) {
            item.bindAnimation(comAnim);
        }
    }

    public static class TaskBuild extends BaseBuild<TaskBuild, ShareTaskDelegate, TaskViewHolder, TaskObserve> {

        private BaseAnim anim = new NullAnim();

        boolean isRunning = false;

        boolean leaveRetain = true;

        int taskIndex;

        int frontType;

        protected TaskBuild(ShareTaskDelegate delegate, int layout, int type) {
            super(delegate, layout, type);
            anim.setPriority(1);
        }

        public static TaskBuild getInstance(ShareTaskDelegate delegate, int layout, int type) {
            return new TaskBuild(delegate, layout, type);
        }

        void setRunning(boolean running) {
            isRunning = running;
        }

        /***
         * 绑定一个动画效果
         */
        public void bindAnimation(BaseAnim anim) {
            if (anim != null && anim.getPriority() > this.anim.getPriority())
                this.anim = anim;
        }

        public TaskBuild subscibe(TaskObserve<?> baseObserve) {
         //   getBaseObserves().put(baseObserve.getType(), baseObserve);
            // TODO
            return this;
        }

        @Override
        protected TaskViewHolder creatViewHolder(View pageView) {
            return TaskViewHolder.getInstance(pageView, rootView);
        }

        public BaseAnim getAnim() {
            return anim;
        }

        public int getFrontType() {
            return frontType;
        }

        public void setFrontType(int frontType) {
            this.frontType = frontType;
        }

        protected boolean isLazy() {
            return isLazy;
        }

        public int getType() {
            return type;
        }

        public void setLeaveRetain(boolean leaveRetain) {
            this.leaveRetain = leaveRetain;
        }

    }
}

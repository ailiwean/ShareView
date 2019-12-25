package com.ailiwean.lib.delegate;

import android.view.View;
import android.widget.FrameLayout;

import com.ailiwean.lib.adapter.MultAdapter;
import com.ailiwean.lib.base.BaseBuild;
import com.ailiwean.lib.base.BaseDelegate;
import com.ailiwean.lib.holder.MultViewHolder;
import com.ailiwean.lib.interfaces.LifeListener;
import com.ailiwean.lib.observe.MultiObserve;

public class ShareMultiDelegate extends BaseDelegate<ShareMultiDelegate, ShareMultiDelegate.MultiBuild> {

    MultiBuild lastBuild;

    private ShareMultiDelegate(FrameLayout mControlView) {
        super(mControlView);

    }

    public MultiBuild regAdapter(MultAdapter adapter) {
        adapter.injectDelegate(this);
        return regLayout(adapter.build(), adapter.getType(), adapter.getLayoutId());
    }

    @Override
    protected MultiBuild creatBuild(ShareMultiDelegate delegate, int layout, int type) {
        return new MultiBuild(delegate, layout, type);
    }

    public static ShareMultiDelegate getInstance(FrameLayout controlView) {
        return new ShareMultiDelegate(controlView);
    }

    @Override
    protected void dispatchShowView(int type) {

        final MultiBuild build = getBuild(type);

        if (lastBuild == build)
            return;

        //回调上个页面的隐藏方法
        if (lastBuild != null && lastBuild.lifeListeners.size() != 0) {
            for (LifeListener item : lastBuild.lifeListeners)
                item.onHidden(lastBuild.getVH());
        }

        if (build.lifeListeners.size() != 0)
            for (LifeListener item : build.lifeListeners)
                item.onVisiable(build.getVH());

        if (lastBuild != null)
            onHidden(lastBuild);
        onVisiable(build);
        lastBuild = build;

        updateCurrentType(type);
    }

    public static class MultiBuild extends BaseBuild<MultiBuild, ShareMultiDelegate, MultViewHolder, MultiObserve> {

        protected MultiBuild(ShareMultiDelegate delegate, int layout, int type) {
            super(delegate, layout, type);
        }

        @Override
        protected MultViewHolder creatViewHolder(View pageView) {
            return MultViewHolder.getInstance(pageView, rootView);
        }

        public ShareMultiDelegate.MultiBuild subscibe(MultiObserve<?> baseObserve) {
            baseObserves.add(baseObserve);
            return this;
        }

        public static MultiBuild getInstance(ShareMultiDelegate delegate, int layout, int type) {
            return new MultiBuild(delegate, layout, type);
        }

    }
}

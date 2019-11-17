package com.ailiwean.shareview.Adapter;

import android.view.View;

import com.ailiwean.lib.adapter.TaskAdapter;
import com.ailiwean.lib.holder.TaskViewHolder;
import com.ailiwean.shareview.R;

public class AAdapter extends TaskAdapter {
    public static int CONTENT = 1;

    @Override
    public int getLayoutId() {
        return R.layout.aa;
    }

    @Override
    public int getType() {
        return CONTENT;
    }

    @Override
    public void init(final TaskViewHolder vh) {


        vh.getPageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.getShareView().goTo(2);
            }
        });


    }

    @Override
    public void lazy(TaskViewHolder vh) {
    }

    @Override
    public void preload(TaskViewHolder vh) {

    }

    @Override
    public int getFrontType() {
        return super.getFrontType();
    }
}
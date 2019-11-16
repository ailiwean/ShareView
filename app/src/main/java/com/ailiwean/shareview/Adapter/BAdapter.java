package com.ailiwean.shareview.Adapter;

import android.view.View;

import com.ailiwean.lib.adapter.TaskAdapter;
import com.ailiwean.lib.holder.TaskViewHolder;
import com.ailiwean.shareview.R;

import java.util.ArrayList;

public class BAdapter extends TaskAdapter {

    private static int INPUT = 2;

    @Override
    public void init(final TaskViewHolder vh) {

        vh.getTv(R.id.name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.getShareView().goTo(3);
                vh.getShareView().postData(1, new ArrayList<Float>());
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
    public int getLayoutId() {
        return R.layout.bb;
    }

    @Override
    public int getType() {
        return INPUT;
    }

    @Override
    public int getFrontType() {
        return 1;
    }

    @Override
    public boolean leaveRetain() {
        return false;
    }
    
}

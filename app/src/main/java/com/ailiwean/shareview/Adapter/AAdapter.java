package com.ailiwean.shareview.Adapter;

import android.view.View;
import android.widget.Toast;

import com.ailiwean.lib.adapter.TaskAdapter;
import com.ailiwean.lib.holder.TaskViewHolder;
import com.ailiwean.lib.observe.TaskObserve;
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

        subscribe(new TaskObserve<String>() {
            @Override
            public void response(TaskViewHolder vh, String s) {

                Toast.makeText(vh.getContext(), s, Toast.LENGTH_SHORT).show();

            }
        });

        subscribe(new TaskObserve<Integer>() {

            @Override
            public void response(TaskViewHolder vh, Integer integer) {

                Toast.makeText(vh.getContext(), integer + "", Toast.LENGTH_SHORT).show();

            }
        });

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

}
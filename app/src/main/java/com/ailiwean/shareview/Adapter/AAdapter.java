package com.ailiwean.shareview.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ailiwean.lib.adapter.TaskAdapter;
import com.ailiwean.lib.holder.TaskViewHolder;
import com.ailiwean.lib.observe.TaskObserve;
import com.ailiwean.shareview.R;

import java.util.ArrayList;
import java.util.HashMap;

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
    public void init() {

        getVh().getPageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVh().getShareView().goTo(2);
            }
        });

    }

    @Override
    public void lazy() {

    }

    @Override
    public void preload() {

    }

}
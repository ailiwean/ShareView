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
import java.util.List;

public class CAdapter extends TaskAdapter {


    public static int OTHER = 3;

    @Override
    public void init() {

        getVh().getPageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVh().getShareView().goTo(1);
            }
        });


    }

    @Override
    public void lazy() {

        subscribe(new TaskObserve<List<ImageView>>() {
            @Override
            public void response(TaskViewHolder vh, List<ImageView> imageViews) {
                Toast.makeText(getContext(), "接收到注册", Toast.LENGTH_SHORT).show();

            }
        });


    }


    @Override
    public void preload() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.cc;
    }

    @Override
    public int getType() {
        return OTHER;
    }

    @Override
    public int getFrontType() {
        return 2;
    }
}

package com.ailiwean.shareview.Adapter;

import android.view.View;

import com.ailiwean.lib.adapter.TaskAdapter;
import com.ailiwean.shareview.R;

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

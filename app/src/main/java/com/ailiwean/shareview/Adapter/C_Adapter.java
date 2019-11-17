package com.ailiwean.shareview.Adapter;

import android.view.View;

import com.ailiwean.lib.adapter.MultAdapter;
import com.ailiwean.lib.holder.MultViewHolder;
import com.ailiwean.shareview.R;

public class C_Adapter extends MultAdapter {
    @Override
    public void init(final MultViewHolder vh) {

        vh.getPageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.getShareView().goTo(1);
            }
        });

    }

    @Override
    public void lazy(MultViewHolder vh) {

    }

    @Override
    public void preload(MultViewHolder vh) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.cc;
    }

    @Override
    public int getType() {
        return 3;
    }
}

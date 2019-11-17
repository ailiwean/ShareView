package com.ailiwean.shareview.Adapter;

import android.view.View;

import com.ailiwean.lib.adapter.MultAdapter;
import com.ailiwean.lib.holder.MultViewHolder;
import com.ailiwean.shareview.R;

public class B_Adapter extends MultAdapter {

    @Override
    public void init(final MultViewHolder vh) {

        vh.getTv(R.id.name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.getShareView().goTo(3);
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
        return R.layout.bb;
    }

    @Override
    public int getType() {
        return 2;
    }
}

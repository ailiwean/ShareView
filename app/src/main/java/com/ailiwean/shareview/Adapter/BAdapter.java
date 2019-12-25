package com.ailiwean.shareview.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ailiwean.lib.adapter.TaskAdapter;
import com.ailiwean.lib.utils.TypeToken;
import com.ailiwean.shareview.MainActivity;
import com.ailiwean.shareview.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BAdapter extends TaskAdapter {

    private static int INPUT = 2;

    @Override
    public void init() {

        getVh().getTv(R.id.name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVh().getShareView().goTo(3);
                getVh().getShareView().postData(3, new TypeToken<ArrayList>() {
                }.getType(), new ArrayList<>());
            }
        });

    }

    @Override
    public void lazy() {
        Toast.makeText(getContext(), "内部lazy", Toast.LENGTH_LONG).show();
    }

    @Override
    public void preload() {

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
        return true;
    }

}

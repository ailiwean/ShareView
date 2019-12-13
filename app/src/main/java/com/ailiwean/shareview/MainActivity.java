package com.ailiwean.shareview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.ailiwean.lib.anim.AnimHelper;
import com.ailiwean.lib.anim.DefaultAnim;
import com.ailiwean.lib.ShareView;
import com.ailiwean.lib.base.BaseViewHolder;
import com.ailiwean.lib.holder.TaskViewHolder;
import com.ailiwean.lib.observe.TaskObserve;
import com.ailiwean.shareview.Adapter.AAdapter;
import com.ailiwean.shareview.Adapter.A_Adapter;
import com.ailiwean.shareview.Adapter.BAdapter;
import com.ailiwean.shareview.Adapter.B_Adapter;
import com.ailiwean.shareview.Adapter.CAdapter;
import com.ailiwean.shareview.Adapter.C_Adapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ShareView shareTask;

    public static final int CONTENT = 0;

    public static final int INPUT = 1;

    public static final int OTHER = 2;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shareTask = findViewById(R.id.mult);

        DefaultAnim anim = new DefaultAnim(3000) {
            @Override
            public int taskTopEnter() {
                return AnimHelper.ALPHA_UP_SHOW;
            }

            @Override
            public int taskTopExit() {
                return AnimHelper.ALPHA_DOWN_HIDE;
            }

            @Override
            public int taskInnerEnter() {
                return AnimHelper.RIGHT_HALF_SHOW;
            }

            @Override
            public int taskInnerExit() {
                return AnimHelper.LEFT_HALF_HIDE;
            }
        };

        shareTask.getTaskDelegate()
                .regAdapter(new BAdapter())
                .cp()
                .regAdapter(new CAdapter())
                .cp()
                .regAdapter(new AAdapter())

                .cp()
                .bindCommonAnimation(anim)
                .go();

    }

    @Override
    public void onBackPressed() {

        if (shareTask.back())
            return;

        super.onBackPressed();
    }
}

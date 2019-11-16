package com.ailiwean.shareview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ailiwean.lib.am.AnimHelper;
import com.ailiwean.lib.am.DefaultAnim;
import com.ailiwean.lib.interfaces.InitListener;
import com.ailiwean.lib.ShareView;
import com.ailiwean.lib.interfaces.LazyListener;
import com.ailiwean.lib.interfaces.LifeListener;
import com.ailiwean.lib.interfaces.PreLoadListener;
import com.ailiwean.lib.holder.TaskViewHolder;
import com.ailiwean.lib.observe.TaskObserve;
import com.ailiwean.shareview.Adapter.AAdapter;
import com.ailiwean.shareview.Adapter.BAdapter;
import com.ailiwean.shareview.Adapter.CAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ShareView shareMultiView;

    public static final int CONTENT = 0;

    public static final int INPUT = 1;

    public static final int OTHER = 2;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shareMultiView = findViewById(R.id.mult);

        DefaultAnim anim = new DefaultAnim(400) {
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

        shareMultiView.getTaskDelegate()
                .regAdapter(new BAdapter())
                .cp()
                .regAdapter(new CAdapter())
                .cp()
                .regAdapter(new AAdapter())
                .subscibe(new TaskObserve<ArrayList<String>>() {

                    @Override
                    public void response(TaskViewHolder vh, ArrayList<String> strings) {
                        Toast.makeText(MainActivity.this, "list集合", Toast.LENGTH_SHORT).show();
                    }
                })
                .cp()
                .bindCommonAnimation(anim)
                .go();

    }

    @Override
    public void onBackPressed() {

        if (shareMultiView.back())
            return;

        super.onBackPressed();
    }
}

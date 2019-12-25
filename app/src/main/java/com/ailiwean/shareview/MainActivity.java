package com.ailiwean.shareview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.ailiwean.lib.anim.AnimHelper;
import com.ailiwean.lib.anim.DefaultAnim;
import com.ailiwean.lib.ShareView;
import com.ailiwean.lib.holder.TaskViewHolder;
import com.ailiwean.lib.interfaces.LifeAdapter;
import com.ailiwean.shareview.Adapter.AAdapter;
import com.ailiwean.shareview.Adapter.BAdapter;
import com.ailiwean.shareview.Adapter.CAdapter;

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

        DefaultAnim anim = new DefaultAnim(4000) {
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
                .addLifeListener(new LifeAdapter<TaskViewHolder>() {

                    @Override
                    public void onReVisiable(TaskViewHolder vh) {
                        Toast.makeText(MainActivity.this, "准备显示", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onVisiable(TaskViewHolder vh) {
                        Toast.makeText(MainActivity.this, "已经显示", Toast.LENGTH_LONG).show();

                    }
                })
                .cp()
                .regAdapter(new CAdapter())
                .cp()
                .regAdapter(new AAdapter())
                .addLifeListener(new LifeAdapter<TaskViewHolder>() {
                    @Override
                    public void onReHide(TaskViewHolder vh) {
                        //                    Toast.makeText(MainActivity.this, "准备隐藏", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onHidden(TaskViewHolder vh) {
                        //                    Toast.makeText(MainActivity.this, "已经隐藏", Toast.LENGTH_LONG).show();
                    }
                })
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

package com.ailiwean.shareview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ailiwean.lib.am.AnimHelper;
import com.ailiwean.lib.am.DefaultAnim;
import com.ailiwean.lib.am.NullAnim;
import com.ailiwean.lib.callback.InitListener;
import com.ailiwean.lib.ShareView;
import com.ailiwean.lib.holder.TaskViewHolder;

public class MainActivity extends AppCompatActivity {

    ShareView shareMultiView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shareMultiView = findViewById(R.id.mult);


        DefaultAnim anim = new DefaultAnim() {
            @Override
            public int taskTopEnter() {
                return AnimHelper.LEFT_ALL_SHOW;
            }

            @Override
            public int taskTopExit() {
                return AnimHelper.RIGHT_ALL_HIDE;
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

        shareMultiView.getTaskDelegate().isLazyLoad(true)
                .isReuseLayout(false)
                .setDefault(2)
                .regView(0, R.layout.aa)
                .init(new InitListener<TaskViewHolder>() {
                    @Override
                    public void init(TaskViewHolder vh) {
                        vh.getRootView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareMultiView.switchType(1);
                            }
                        });
                    }
                })
                .bindAnimation(new NullAnim())
                .cp()
                .regView(1, R.layout.bb)
                .init(new InitListener<TaskViewHolder>() {
                    @Override
                    public void init(TaskViewHolder vh) {

                        vh.getRootView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareMultiView.switchType(2);
                            }
                        });
            
                    }
                })
                .cp()
                .regView(2, R.layout.cc)
                .init(new InitListener<TaskViewHolder>() {
                    @Override
                    public void init(TaskViewHolder vh) {

                        vh.getRootView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareMultiView.switchType(0);
                            }
                        });

                    }
                })
                .cp()
                .bindCommonAnimation(anim)
                .go();

    }
}

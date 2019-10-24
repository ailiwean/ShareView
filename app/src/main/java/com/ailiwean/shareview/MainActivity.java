package com.ailiwean.shareview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ailiwean.lib.am.AnimHelper;
import com.ailiwean.lib.am.DefaultAnim;
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

        shareMultiView.getTaskDelegate().isLazyLoad(true)
                .isReuseLayout(false)
                .setDefault(2)
                .regView(0, R.layout.aa)
                .init(new InitListener<TaskViewHolder>() {
                    @Override
                    public void init(TaskViewHolder vH) {

                        vH.getRootView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareMultiView.switchType(1);
                            }
                        });

                    }
                })
                .cp()
                .regView(1, R.layout.bb)
                .init(new InitListener<TaskViewHolder>() {
                    @Override
                    public void init(TaskViewHolder vH) {

                        vH.getRootView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareMultiView.switchType(2);
                            }
                        });

                    }
                })
                .bindAnimation(new DefaultAnim() {
                    @Override
                    public int enter() {
                        return AnimHelper.ALPHA_SHOW;
                    }

                    @Override
                    public int exit() {
                        return AnimHelper.ALPHA_HIDE;
                    }
                })
                .cp()
                .regView(2, R.layout.bb)
                .init(new InitListener<TaskViewHolder>() {
                    @Override
                    public void init(TaskViewHolder vH) {

                        vH.getRootView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareMultiView.switchType(0);
                            }
                        });

                    }
                })
                .bindAnimation(new DefaultAnim() {
                    @Override
                    public int enter() {
                        return AnimHelper.ALPHA_SHOW;
                    }

                    @Override
                    public int exit() {
                        return AnimHelper.ALPHA_HIDE;
                    }
                })
                .cp()
                .go();

    }
}

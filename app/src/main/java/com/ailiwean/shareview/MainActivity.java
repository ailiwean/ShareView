package com.ailiwean.shareview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ailiwean.lib.am.AnimHelper;
import com.ailiwean.lib.am.DefaultAnim;
import com.ailiwean.lib.callback.InitListener;
import com.ailiwean.lib.ShareView;
import com.ailiwean.lib.callback.LazyListener;
import com.ailiwean.lib.callback.LifeListener;
import com.ailiwean.lib.callback.PreLoadListener;
import com.ailiwean.lib.holder.TaskViewHolder;
import com.ailiwean.lib.observe.TaskObserve;

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
                .isLazyLoad(false)
                .isReuseLayout(false)
                .setDefault(2)

                .regLayout(INPUT, R.layout.bb, OTHER)
                .init(new InitListener<TaskViewHolder>() {
                    @Override
                    public void init(final TaskViewHolder vh) {

                        vh.getView(R.id.name).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                shareMultiView.switchType(OTHER);

                            }
                        });

                    }
                })
                .preLoad(new PreLoadListener<TaskViewHolder>() {
                    @Override
                    public void preLoad(TaskViewHolder vh) {

                        Toast.makeText(MainActivity.this, "与加载", Toast.LENGTH_SHORT).show();
                    }
                })
                .lazy(new LazyListener<TaskViewHolder>() {
                    @Override
                    public void onLazy(TaskViewHolder vh) {

                    }
                })
                .addLifeListener(new LifeListener<TaskViewHolder>() {
                    @Override
                    public void onVisiable(TaskViewHolder vH) {

                    }

                    @Override
                    public void onHide(TaskViewHolder vH) {

                    }
                })
                .cp()


                .regLayout(OTHER, R.layout.cc, CONTENT)
                .init(new InitListener<TaskViewHolder>() {
                    @Override
                    public void init(TaskViewHolder vh) {

                        vh.getRootView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareMultiView.switchType(CONTENT);
                            }
                        });

                    }
                })
                .cp()


                .regRootLayout(CONTENT, R.layout.aa)
                .init(new InitListener<TaskViewHolder>() {
                    @Override
                    public void init(TaskViewHolder vh) {
                        vh.getRootView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareMultiView.switchType(INPUT);
//                                Intent a = new Intent(MainActivity.this, B.class);
//                                startActivity(a);
//                                overridePendingTransition(R.anim.trans, 0);

                                shareMultiView.preload(INPUT);
                            }
                        });

                    }
                })
                .lazy(new LazyListener<TaskViewHolder>() {
                    @Override
                    public void onLazy(TaskViewHolder vh) {
                    }
                })
                .addLifeListener(new LifeListener<TaskViewHolder>() {
                    @Override
                    public void onVisiable(TaskViewHolder vH) {
                    }

                    @Override
                    public void onHide(TaskViewHolder vH) {

                    }
                })
                .subscibe(new TaskObserve<String>() {
                    @Override
                    public void response(TaskViewHolder vh, String s) {
                        vh.setText(R.id.name, s);
                    }
                })
                .subscibe(new TaskObserve<Integer>() {
                    @Override
                    public void response(TaskViewHolder vh, Integer integer) {
                        vh.setText(R.id.age, integer.toString());
                    }
                })
                .bindAnimation(new DefaultAnim() {
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

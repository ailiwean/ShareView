package com.ailiwean.shareview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.ailiwean.lib.am.AnimHelper;
import com.ailiwean.lib.am.CustomAnim;
import com.ailiwean.lib.am.DefaultAnim;
import com.ailiwean.lib.base.BaseObserve;
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


        shareMultiView.getTaskDelegate()
                .isLazyLoad(true)
                .isReuseLayout(false)
                .setDefault(2)
                .regLayout(0, R.layout.aa)
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
                .subscibe(new BaseObserve<String>() {
                    @Override
                    public void response(String s) {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                })
                .subscibe(new BaseObserve<Integer>() {
                    @Override
                    public void response(Integer integer) {
                        Toast.makeText(MainActivity.this, "接收到数字" + integer, Toast.LENGTH_SHORT).show();
                    }
                })
                .cp()


                .regLayout(1, R.layout.bb)
                .init(new InitListener<TaskViewHolder>() {
                    @Override
                    public void init(TaskViewHolder vh) {

                        vh.getRootView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareMultiView.postData(0, "sdsd");
                                shareMultiView.switchType(2);
                            }
                        });

                    }
                })
                .cp()


                .regLayout(2, R.layout.cc)
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

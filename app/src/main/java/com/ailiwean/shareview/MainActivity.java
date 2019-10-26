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
import com.ailiwean.lib.base.BaseViewHolder;
import com.ailiwean.lib.callback.BaseHolderClick;
import com.ailiwean.lib.callback.InitListener;
import com.ailiwean.lib.ShareView;
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

        DefaultAnim anim = new DefaultAnim(300) {
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
                .regLayout(CONTENT, R.layout.aa)
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
                .cp()

                .regLayout(INPUT, R.layout.bb)
                .init(new InitListener<TaskViewHolder>() {
                    @Override
                    public void init(final TaskViewHolder vh) {

                        vh.getView(R.id.send).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                shareMultiView.postData(CONTENT, vh.getText(R.id.name));
                                shareMultiView.postData(CONTENT, Integer.parseInt(vh.getText(R.id.age)));

                            }
                        });


                        vh.getView(R.id.next).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                shareMultiView.switchType(OTHER);

                            }
                        });

                    }
                })
                .cp()


                .regLayout(OTHER, R.layout.cc)
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

                .bindCommonAnimation(anim)
                .go();

    }
}

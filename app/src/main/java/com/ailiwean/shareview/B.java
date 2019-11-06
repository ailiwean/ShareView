package com.ailiwean.shareview;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ailiwean.lib.ShareView;
import com.ailiwean.lib.callback.InitListener;
import com.ailiwean.lib.holder.MultViewHolder;

public class B extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final ShareView shareView = findViewById(R.id.mult);

        shareView.getMultiDelegate()
                .isLazyLoad(true)
                .isReuseLayout(true)
                .regLayout(0, R.layout.aa)
                .init(new InitListener<MultViewHolder>() {
                    @Override
                    public void init(MultViewHolder vh) {
                        vh.getRootView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareView.switchType(1);
                            }
                        });
                    }   
                })
                .cp()
                .regLayout(1, R.layout.cc)
                .init(new InitListener<MultViewHolder>() {
                    @Override
                    public void init(MultViewHolder vh) {
                        vh.getRootView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareView.switchType(2);
                            }
                        });
                    }
                })
                .cp()
                .regLayout(2, R.layout.aa)
                .init(new InitListener<MultViewHolder>() {
                    @Override
                    public void init(MultViewHolder vh) {
                        vh.getRootView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareView.switchType(0);
                            }
                        });
                    }
                })

                .cp()
                .go();

    }
}

package com.ailiwean.sharemultiview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ailiwean.lib.ShareMultiDelegate;
import com.ailiwean.lib.ShareMultiView;

public class MainActivity extends AppCompatActivity {

    ShareMultiView shareMultiView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shareMultiView = findViewById(R.id.mult);

        shareMultiView.getMultiDelegate().isLazyLoad(true)
                .isReuseLayout(true)
                .setDefault(0)
                .registerView(0, R.layout.aa)
                .init(new ShareMultiDelegate.Init() {
                    @Override
                    public void init(View view) {

                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareMultiView.switchType(1);
                            }
                        });

                    }
                })
                .registerView(1, R.layout.bb)
                .init(new ShareMultiDelegate.Init() {
                    @Override
                    public void init(View view) {

                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareMultiView.switchType(2);
                            }
                        });

                    }
                })
                .registerView(2, R.layout.bb)
                .init(new ShareMultiDelegate.Init() {
                    @Override
                    public void init(View pageView) {

                        pageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareMultiView.switchType(0);
                            }
                        });

                    }
                }).go();

    }
}

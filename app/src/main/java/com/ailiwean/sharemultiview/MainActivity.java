package com.ailiwean.sharemultiview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ailiwean.lib.callback.InitListener;
import com.ailiwean.lib.ShareView;

public class MainActivity extends AppCompatActivity {

    ShareView shareMultiView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shareMultiView = findViewById(R.id.mult);

        shareMultiView.getMultiDelegate().isLazyLoad(true)
                .isReuseLayout(false)
                .setDefault(0)
                .registerView(0, R.layout.aa)
                .init(new InitListener() {
                    @Override
                    public void init(View pageView) {
                        pageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareMultiView.switchType(1);
                            }
                        });

                    }
                })
                .cp()
                .registerView(1, R.layout.bb)
                .init(new InitListener() {
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


                .cp()
                .registerView(2, R.layout.bb)
                .init(new InitListener() {
                    @Override
                    public void init(View pageView) {

                        pageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                shareMultiView.switchType(0);
                            }
                        });

                    }
                })
                .cp()
                .go();

    }
}

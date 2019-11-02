package com.ailiwean.shareview;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }
}

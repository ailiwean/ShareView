package com.ailiwean.lib.callback;

public interface RollBackInter {

    void record(int type);

    boolean back();

}

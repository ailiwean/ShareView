package com.ailiwean.lib.interfaces;

public interface RollBackInter {

    void record(int type);

    boolean back();

}

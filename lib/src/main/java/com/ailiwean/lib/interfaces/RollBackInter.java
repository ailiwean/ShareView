package com.ailiwean.lib.interfaces;

/***
 * 回退接口
 */
public interface RollBackInter {

    void record(int type);

    boolean back();

}

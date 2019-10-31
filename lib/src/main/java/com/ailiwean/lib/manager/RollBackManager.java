package com.ailiwean.lib.manager;

import com.ailiwean.lib.callback.RollBackInter;
import com.ailiwean.lib.delegate.ShareTaskDelegate;

import java.util.ArrayList;
import java.util.List;

public class RollBackManager implements RollBackInter {

    List<Integer> runTaskList = new ArrayList<>();

    ShareTaskDelegate delegate;

    private RollBackManager(ShareTaskDelegate delegate) {
        this.delegate = delegate;
    }

    public static RollBackManager getInstance(ShareTaskDelegate delegate) {
        return new RollBackManager(delegate);
    }

    /***
     * 模拟结束到指定页
     * @param type
     */
    @Override
    public void switchType(int type) {
        if (runTaskList.contains(type)) {
            List<Integer> newRunTaskList = new ArrayList<>();
            for (Integer item : runTaskList) {
                if (item != type)
                    newRunTaskList.add(item);
                else {
                    newRunTaskList.add(item);
                    runTaskList = newRunTaskList;
                    return;
                }
            }

        } else runTaskList.add(type);

    }

    /***
     * 用模拟栈里边消费一次返回事件,消费成功返回true
     * @return
     */
    @Override
    public boolean back() {
        if (runTaskList.size() == 1)
            return false;
        else {
            if (runTaskList.size() == 0)
                return false;

            runTaskList.remove(runTaskList.size() - 1);
            delegate.switchType(runTaskList.get(runTaskList.size() - 1));
            return true;
        }
    }
}

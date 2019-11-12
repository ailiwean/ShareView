package com.ailiwean.lib.interfaces;

import android.view.View;

public interface AnimStateListener {

    void enterAnimStar(View view, boolean isTaskTop);

    void enterAnimEnd(View view, boolean isTaskTop);

    void exitAnimStar(View view, boolean isTaskTop);

    void exitAnimEnd(View view, boolean isTaskTop);

}

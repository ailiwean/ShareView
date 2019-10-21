package com.ailiwean.lib.callback;

import android.view.View;

public interface AnimStateListener {

    void enterAnimStar(View view);

    void enterAnimEnd(View view);

    void exitAnimStar(View view);

    void exitAnimEnd(View view);

}

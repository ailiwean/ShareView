package com.ailiwean.lib.animation;

import android.view.View;

import com.ailiwean.lib.callback.AnimOutListener;
import com.ailiwean.lib.callback.AnimStateListener;

public abstract class DefaultAnim extends CustomAnim implements AnimOutListener, AnimStateListener {


    @Override
    public void enter(View pageView) {
        commonExecute(enter(), true);
    }

    @Override
    public void exit(View pageView) {
        commonExecute(exit(), false);
    }

    private void commonExecute(int type, final boolean isEnter) {

        switch (type) {

            case AnimHelper.ALPHA_SHOW:

                AnimLib.getInstance(AnimHelper.ALPHA_SHOW, pageView)
                        .bindListener(new AnimStateListener() {
                            @Override
                            public void animStar(View view) {
                                DefaultAnim.this.animStar();
                                DefaultAnim.this.animStar(pageView);
                            }

                            @Override
                            public void animEnd(View view) {
                                if (isEnter)
                                    DefaultAnim.this.enterAnimEnd();
                                else DefaultAnim.this.exitAnimEnd();

                                DefaultAnim.this.animEnd(pageView);
                            }
                        })
                        .start();

                break;


            case AnimHelper.ALPHA_HIDE:

                AnimLib.getInstance(AnimHelper.ALPHA_HIDE, pageView)
                        .bindListener(new AnimStateListener() {
                            @Override
                            public void animStar(View view) {
                                DefaultAnim.this.animStar();
                            }

                            @Override
                            public void animEnd(View view) {
                                if (isEnter)
                                    DefaultAnim.this.enterAnimEnd();
                                else DefaultAnim.this.exitAnimEnd();
                            }
                        })
                        .start();


                break;
        }

    }

    @Override
    public void animStar(View view) {
    }

    @Override
    public void animEnd(View view) {
    }

}

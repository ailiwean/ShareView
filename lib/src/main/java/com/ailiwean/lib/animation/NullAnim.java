package com.ailiwean.lib.animation;

public class NullAnim extends DefaultAnim {

    @Override
    public int enter() {
        return AnimHelper.NULL;
    }

    @Override
    public int exit() {
        return AnimHelper.NULL;
    }
}


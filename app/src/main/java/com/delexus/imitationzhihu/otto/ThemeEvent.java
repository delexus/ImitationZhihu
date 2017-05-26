package com.delexus.imitationzhihu.otto;

/**
 * Created by delexus on 2017/5/15.
 */

public class ThemeEvent {

    private boolean mNightMode;

    public ThemeEvent(boolean mNightMode) {
        this.mNightMode = mNightMode;
    }

    public boolean isNightMode() {
        return mNightMode;
    }

    public void setNightMode(boolean mNightMode) {
        this.mNightMode = mNightMode;
    }
}

package com.delexus.imitationzhihu.util;

import android.support.annotation.IntDef;

/**
 * Created by delexus on 2017/5/15.
 */

public class NightModeHelper {
    private static NightModeHelper sInstance;
    private static final Object mLock = new Object();

    public static final int DAY = 1;
    public static final int NIGHT = 2;

    @NightModeId
    private int mMode;

    @IntDef({DAY, NIGHT})
    private @interface NightModeId {
    }

    public static NightModeHelper getInstance() {
        if (sInstance == null) {
            synchronized (mLock) {
                sInstance = new NightModeHelper();
            }
        }
        return sInstance;
    }

    private NightModeHelper() {
    }

    public void setMode(@NightModeId int mode) {
        mMode = mode;
    }

    public int getMode() {
        return mMode;
    }
}

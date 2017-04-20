package com.delexus.imitationzhihu.otto;

import com.squareup.otto.Bus;

/**
 * Created by delexus on 2017/4/20.
 */

public final class MyBus extends Bus {

    private static final Object mLock = new Object();
    private static MyBus mBus = null;

    private MyBus() {
    }

    public static MyBus getInstance() {
        if (mBus == null) {
            synchronized (mLock) {
                mBus = new MyBus();
            }
        }
        return mBus;
    }
}

package com.delexus.imitationzhihu;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by delexus on 2017/5/10.
 */

public class MainApplication extends Application {

    private boolean mIsDay;
    private RefWatcher mRefWatcher;

    public static MainApplication getInstance(Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRefWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        MainApplication application = (MainApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

    /**
     * @return true 白天 false 夜间
     */
    public boolean getDayOrNight() {
        return mIsDay;
    }

    /**
     * @param isDay true 白天 false 夜间
     */
    public void setDayOrNight(boolean isDay) {
        mIsDay = isDay;
    }
}

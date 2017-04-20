package com.delexus.imitationzhihu.otto;

import android.support.annotation.IdRes;
import android.support.annotation.IntDef;

/**
 * Created by delexus on 2017/4/20.
 */

public class GestureEvent {

    @IntDef({ACTION_DOWN, ACTION_UP})
    private @interface GestureIds{}

    public static final int ACTION_DOWN = 0;
    public static final int ACTION_UP = 1;

    @GestureIds
    private int motionId;

    @IdRes
    private int id;

    public GestureEvent(@GestureIds int motionId) {
        this.motionId = motionId;
    }

    public GestureEvent(@GestureIds int motionId, @IdRes int id) {
        this.motionId = motionId;
        this.id = id;
    }

    @GestureIds
    public int getMotionId() {
        return motionId;
    }

    public void setMotionId(@GestureIds int motionId) {
        this.motionId = motionId;
    }

    @IdRes
    public int getId() {
        return id;
    }

    public void setId(@IdRes int id) {
        this.id = id;
    }
}

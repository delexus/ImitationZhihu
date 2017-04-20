package com.delexus.imitationzhihu.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ImageButton;

import com.delexus.imitationzhihu.R;
import com.delexus.imitationzhihu.otto.GestureEvent;
import com.delexus.imitationzhihu.otto.MyBus;
import com.squareup.otto.Subscribe;

/**
 * 与FloatingButton通过otto联动
 * Created by delexus on 2017/4/20.
 */

public class FloatingImageButton extends ImageButton {

    private UnsetPressedState mUnsetPressedState;
    private boolean mPrepressed;

    public FloatingImageButton(Context context) {
        super(context);
    }

    public FloatingImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                MyBus.getInstance().post(new GestureEvent(GestureEvent.ACTION_DOWN, getId()));
                break;
            case MotionEvent.ACTION_UP:
                MyBus.getInstance().post(new GestureEvent(GestureEvent.ACTION_UP));
                break;

        }
        return super.onTouchEvent(event);
    }

    @Subscribe
    public void onTouchEvent(GestureEvent event) {
        switch (event.getMotionId()) {
            case GestureEvent.ACTION_DOWN:
                if ((event.getId() == R.id.text_idea && getId() == R.id.fab_idea) ||
                        (event.getId() == R.id.text_question && getId() == R.id.fab_question) ||
                        (event.getId() == R.id.text_answer && getId() == R.id.fab_answer)) {
                    setPressed(true);
                    mPrepressed = true;
                }
                break;
            case GestureEvent.ACTION_UP:
                if (!mPrepressed) {
                    return;
                }
                if (mUnsetPressedState == null) {
                    mUnsetPressedState = new UnsetPressedState();
                }
                postDelayed(mUnsetPressedState, ViewConfiguration.getPressedStateDuration());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MyBus.getInstance().register(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MyBus.getInstance().unregister(this);
    }

    private final class UnsetPressedState implements Runnable {
        @Override
        public void run() {
            setPressed(false);
        }
    }

}

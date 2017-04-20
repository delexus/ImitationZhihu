package com.delexus.imitationzhihu.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

import com.delexus.imitationzhihu.R;
import com.delexus.imitationzhihu.otto.GestureEvent;
import com.delexus.imitationzhihu.otto.MyBus;
import com.squareup.otto.Subscribe;

/**
 * 与FloatingImageButton联动
 * Created by delexus on 2017/4/20.
 */

public class FloatingButton extends TextView {

    private UnsetPressedState mUnsetPressedState;

    public FloatingButton(Context context) {
        super(context);
    }

    public FloatingButton(Context context, AttributeSet attrs) {
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
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Subscribe
    public void onTouchEvent(GestureEvent event) {
        switch (event.getMotionId()) {
            case GestureEvent.ACTION_DOWN:
                if ((event.getId() == R.id.fab_idea && getId() == R.id.text_idea) ||
                        (event.getId() == R.id.fab_question && getId() == R.id.text_question) ||
                        (event.getId() == R.id.fab_answer && getId() == R.id.text_answer)) {
                    setPressed(true);
                }
                break;
            case GestureEvent.ACTION_UP:
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

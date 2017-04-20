package com.delexus.imitationzhihu.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.delexus.imitationzhihu.R;

/**
 * 背景消费掉触摸事件，不要继续上传
 * Created by delexus on 2017/4/20.
 */

public class FloatingActionLayout extends FrameLayout {

    public FloatingActionLayout(@NonNull Context context) {
        super(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.layout_action_publish, this, true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}

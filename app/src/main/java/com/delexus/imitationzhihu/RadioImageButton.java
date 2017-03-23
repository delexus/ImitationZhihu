package com.delexus.imitationzhihu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.CompoundButton;

import java.lang.reflect.Field;

/**
 * Created by delexus on 2017/3/13.
 */

public class RadioImageButton extends CompoundButton {

    private Drawable mDrawable;

    public RadioImageButton(Context context) {
        super(context);
    }

    public RadioImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDrawable = (Drawable) reflectParentFieldAndReturn(this, getClass().getSuperclass().getName(),
                "mButtonDrawable");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final Drawable buttonDrawable = mDrawable;
        if (buttonDrawable != null) {
            final int verticalGravity = getGravity() & Gravity.VERTICAL_GRAVITY_MASK;
            final int drawableHeight = buttonDrawable.getIntrinsicHeight();
            final int drawableWidth = buttonDrawable.getIntrinsicWidth();

            final int top;
            switch (verticalGravity) {
                case Gravity.BOTTOM:
                    top = getHeight() - drawableHeight;
                    break;
                case Gravity.CENTER_VERTICAL:
                    top = (getHeight() - drawableHeight) / 2;
                    break;
                default:
                    top = 0;
            }
            final int bottom = top + drawableHeight;
            final int horizontalGravity = getGravity() & Gravity.HORIZONTAL_GRAVITY_MASK;
            final int left;
            switch (horizontalGravity) {
                case Gravity.CENTER_HORIZONTAL:
                    left = (getWidth() - drawableWidth) / 2;
                    break;
                default:
                    left = 0;
                    break;
            }
            final int right = left + drawableWidth;
            buttonDrawable.setBounds(left, top, right, bottom);

            final Drawable background = getBackground();
            if (background != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                background.setHotspotBounds(left, top, right, bottom);
            }

            buttonDrawable.draw(canvas);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * If the radio button is already checked, this method will not toggle the radio button.
     */
    @Override
    public void toggle() {
        // we override to prevent toggle when the radio is already
        // checked (as opposed to check boxes widgets)
        if (!isChecked()) {
            super.toggle();
        }
    }

    public static Object reflectParentFieldAndReturn(Object object, String clazzName, String fieldName) {
        Field field = null;
        Object ret = null;
        try {
            for (Class clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                if (clazz.getName().equals(clazzName))
                    field = clazz.getDeclaredField(fieldName);
            }
            if (field != null) {
                field.setAccessible(true);
                ret = field.get(object);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return ret;
    }
}

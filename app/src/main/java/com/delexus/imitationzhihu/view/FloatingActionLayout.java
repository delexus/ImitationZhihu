package com.delexus.imitationzhihu.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.delexus.imitationzhihu.R;

/**
 * 背景消费掉触摸事件，不要继续上传
 * Created by delexus on 2017/4/20.
 */

public class FloatingActionLayout extends FrameLayout {

    private FloatingButton mTextIdea, mTextAnswer, mTextQuestion;
    private FloatingImageButton mImageIdea, mImageAnswer, mImageQuestion;
    private int mTranslationX;

    public FloatingActionLayout(@NonNull Context context) {
        this(context, null);
    }

    public FloatingActionLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.layout_action_publish, this, true);

        mTextIdea = (FloatingButton) findViewById(R.id.text_idea);
        mTextAnswer = (FloatingButton) findViewById(R.id.text_answer);
        mTextQuestion = (FloatingButton) findViewById(R.id.text_question);
        mImageIdea = (FloatingImageButton) findViewById(R.id.fab_idea);
        mImageAnswer = (FloatingImageButton) findViewById(R.id.fab_answer);
        mImageQuestion = (FloatingImageButton) findViewById(R.id.fab_question);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mTranslationX = mTextIdea.getMeasuredWidth() / 2;
    }

    public void enterAnimation(boolean enter) {
        ObjectAnimator rootViewAnimator;
        ObjectAnimator textIdeaAnimator;
        ObjectAnimator textIdeaAnimator2;
        ObjectAnimator textAnswerAnimator;
        ObjectAnimator textAnswerAnimator2;
        ObjectAnimator textQuestionAnimator;
        ObjectAnimator textQuestionAnimator2;
        ObjectAnimator imageIdeaAnimator;
        ObjectAnimator imageIdeaAnimator2;
        ObjectAnimator imageAnswerAnimator;
        ObjectAnimator imageAnswerAnimator2;
        ObjectAnimator imageQuestionAnimator;
        ObjectAnimator imageQuestionAnimator2;
        if (enter) {
            rootViewAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, 0, 1);
            textIdeaAnimator = ObjectAnimator.ofFloat(mTextIdea, View.TRANSLATION_X, mTranslationX, 0);
            textIdeaAnimator2 = ObjectAnimator.ofFloat(mTextIdea, View.ALPHA, 0, 1);
            textAnswerAnimator = ObjectAnimator.ofFloat(mTextAnswer, View.TRANSLATION_X, mTranslationX, 0);
            textAnswerAnimator2 = ObjectAnimator.ofFloat(mTextAnswer, View.ALPHA, 0, 1);
            textQuestionAnimator = ObjectAnimator.ofFloat(mTextQuestion, View.TRANSLATION_X, mTranslationX, 0);
            textQuestionAnimator2 = ObjectAnimator.ofFloat(mTextQuestion, View.ALPHA, 0, 1);
            imageIdeaAnimator = ObjectAnimator.ofFloat(mImageIdea, View.SCALE_X, 0, 1);
            imageIdeaAnimator2 = ObjectAnimator.ofFloat(mImageIdea, View.SCALE_Y, 0, 1);
            imageAnswerAnimator = ObjectAnimator.ofFloat(mImageAnswer, View.SCALE_X, 0, 1);
            imageAnswerAnimator2 = ObjectAnimator.ofFloat(mImageAnswer, View.SCALE_Y, 0, 1);
            imageQuestionAnimator = ObjectAnimator.ofFloat(mImageQuestion, View.SCALE_X, 0, 1);
            imageQuestionAnimator2 = ObjectAnimator.ofFloat(mImageQuestion, View.SCALE_Y, 0, 1);
        } else {
            rootViewAnimator = ObjectAnimator.ofFloat(this, View.ALPHA, 1, 0);
            textIdeaAnimator = ObjectAnimator.ofFloat(mTextIdea, View.TRANSLATION_X, 0, mTranslationX);
            textIdeaAnimator2 = ObjectAnimator.ofFloat(mTextIdea, View.ALPHA, 1, 0);
            textAnswerAnimator = ObjectAnimator.ofFloat(mTextAnswer, View.TRANSLATION_X, 0, mTranslationX);
            textAnswerAnimator2 = ObjectAnimator.ofFloat(mTextAnswer, View.ALPHA, 1, 0);
            textQuestionAnimator = ObjectAnimator.ofFloat(mTextQuestion, View.TRANSLATION_X, 0, mTranslationX);
            textQuestionAnimator2 = ObjectAnimator.ofFloat(mTextQuestion, View.ALPHA, 1, 0);
            imageIdeaAnimator = ObjectAnimator.ofFloat(mImageIdea, View.SCALE_X, 1, 0);
            imageIdeaAnimator2 = ObjectAnimator.ofFloat(mImageIdea, View.SCALE_Y, 1, 0);
            imageAnswerAnimator = ObjectAnimator.ofFloat(mImageAnswer, View.SCALE_X, 1, 0);
            imageAnswerAnimator2 = ObjectAnimator.ofFloat(mImageAnswer, View.SCALE_Y, 1, 0);
            imageQuestionAnimator = ObjectAnimator.ofFloat(mImageQuestion, View.SCALE_X, 1, 0);
            imageQuestionAnimator2 = ObjectAnimator.ofFloat(mImageQuestion, View.SCALE_Y, 1, 0);
        }
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new OvershootInterpolator());
        set.play(rootViewAnimator).with(textIdeaAnimator).with(textIdeaAnimator2)
                .with(textAnswerAnimator).with(textAnswerAnimator2)
                .with(textQuestionAnimator).with(textQuestionAnimator2)
                .with(imageIdeaAnimator).with(imageIdeaAnimator2)
                .with(imageAnswerAnimator).with(imageAnswerAnimator2)
                .with(imageQuestionAnimator).with(imageQuestionAnimator2);
        set.setDuration(300);
        set.start();
    }
}

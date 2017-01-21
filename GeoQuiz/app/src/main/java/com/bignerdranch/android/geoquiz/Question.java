package com.bignerdranch.android.geoquiz;

/**
 * Created by valarauko on 1/15/2017.
 */

public class Question {
    private int mTextResourceId;
    private boolean mAnswerTrue;

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public int getTextResourceId() {
        return mTextResourceId;
    }

    public void setTextResourceId(int textResourceId) {
        mTextResourceId = textResourceId;
    }

    public Question(int textResId, boolean answerTrue) {
        this.mTextResourceId = textResId;
        this.mAnswerTrue = answerTrue;
    }
}

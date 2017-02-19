package com.bignerdranch.android.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown";
    private static final String HAS_CHEATED_KEY = "has_cheated";
    private static final String ANSWER_IS_TRUE_KEY = "answer_is_true";

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private boolean mHasCheated = false;
    private TextView mAPITextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if (savedInstanceState != null) {
            mAnswerIsTrue = savedInstanceState.getBoolean(ANSWER_IS_TRUE_KEY, false);
            mHasCheated = savedInstanceState.getBoolean(HAS_CHEATED_KEY, false);
            setAnswerShownResult(mHasCheated);
        }

        mAnswerIsTrue = getIntent().getBooleanExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, false);
        mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);

        mShowAnswer = (Button)findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAnswer(mAnswerIsTrue);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswer.getWidth() / 2;
                    int cy = mShowAnswer.getHeight() / 2;
                    float r = mShowAnswer.getWidth();

                    Animator anim = ViewAnimationUtils
                            .createCircularReveal(mShowAnswer, cx, cy, r, 0);

                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            toggleAnswer();
                        }
                    });
                    anim.start();
                } else {
                    toggleAnswer();
                }
            }
        });

        if (mHasCheated) {
            showAnswer(mAnswerIsTrue);
            toggleAnswer();
        }

        mAPITextView = (TextView)findViewById(R.id.api_text_view);
        mAPITextView.setText("API Level " + Build.VERSION.SDK_INT);
    }

    public void toggleAnswer() {
        mAnswerTextView.setVisibility(View.VISIBLE);
        mShowAnswer.setVisibility(View.INVISIBLE);
    }

    public void showAnswer(boolean answerIsTrue) {
        if (answerIsTrue) {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
        mHasCheated = true;
        setAnswerShownResult(mHasCheated);
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceStateData) {
        saveInstanceStateData.putBoolean(HAS_CHEATED_KEY, mHasCheated);
    }
}

package com.khud44.triviaallday.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import com.khud44.triviaallday.data.MyViewModel;
import com.khud44.triviaallday.R;
import com.khud44.triviaallday.models.placeholder.QuestionView;
import com.khud44.triviaallday.models.pojo.Question;
import com.khud44.triviaallday.models.pojo.QuestionList;
import com.khud44.triviaallday.utils.SwipeCardCallback;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;


import java.util.List;

import static com.khud44.triviaallday.utils.Utils.showMsg;

public class QuestionsActivity extends BaseActivity implements SwipeCardCallback {

    private static final String TAG = QuestionsActivity.class.getSimpleName();

    private SwipePlaceHolderView swipeView;
    private MyViewModel viewModel;
    private int score;
    private int position;

    public void makeUI(){
        setContentView(R.layout.question_list_layout);
        Intent received = getIntent();
        final int categoryId = received.getIntExtra(getString(R.string.category_id), -1);
        position = received.getIntExtra(getString(R.string.position), -1);
        final String category = received.getStringExtra(getString(R.string.category));

        if (categoryId == -1){
            showFailedMsg();
        }

        score = 0;

        swipeView = findViewById(R.id.swipeView);

        swipeView.disableTouchSwipe();

        swipeView.addItemRemoveListener(new ItemRemovedListener() {
            @Override
            public void onItemRemoved(int count) {
                if (count == 0){
                    Intent data = new Intent();
                    data.putExtra(getString(R.string.round_score), score);
                    data.putExtra(getString(R.string.category_id), categoryId);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });

        swipeView.getBuilder().setDisplayViewCount(1).setSwipeDecor(new SwipeDecor()
                .setViewGravity(Gravity.TOP)
                .setPaddingTop(20)
                //.setRelativeScale(0.1f)
                .setSwipeAnimFactor(2.0f)
                .setSwipeAnimTime(500));

        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        viewModel.getQuestionListData(categoryId).observe(this, new Observer<QuestionList>() {
            @Override
            public void onChanged(@Nullable QuestionList questionList) {
                if (questionList != null){
                    populateQuestionCards(questionList);
                }
                else{
                    showFailedMsg();
                }
            }
        });

    }

    private void showFailedMsg(){
        showMsg(this, getString(R.string.category_id_error));
        Intent data = new Intent();
        if (position != -1){
            data.putExtra(this.getString(R.string.position), position);
        }
        setResult(RESULT_CANCELED, data);
        finish();
    }

    private void populateQuestionCards(QuestionList questionList){
        List<Question> questions = questionList.getResults();
        if (questions != null && questions.size() != 0){
            for(Question question : questions){
                swipeView.addView(new QuestionView(this, question, swipeView));
            }
        }
        else{
            showFailedMsg();
        }
    }

    @Override
    public void onSwipeIn(){
        score++;
        Log.d(TAG, "SCORE: " + score);
    }

    @Override
    public void onSwipeOut(){ }

}

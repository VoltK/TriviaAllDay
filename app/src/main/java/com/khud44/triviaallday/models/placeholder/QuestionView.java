package com.khud44.triviaallday.models.placeholder;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.khud44.triviaallday.R;
import com.khud44.triviaallday.models.pojo.Question;
import com.khud44.triviaallday.utils.SwipeCardCallback;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.*;
import com.mindorks.placeholderview.annotations.swipe.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.khud44.triviaallday.utils.Utils.*;

@NonReusable
@Layout(R.layout.question_card_view)
public class QuestionView {

    private static final String TAG = QuestionView.class.getSimpleName();

    @View(R.id.questionView)
    public TextView questionView;

    @View(R.id.answersHolder)
    public LinearLayout answersHolder;

    @View(R.id.categoryView)
    public TextView categoryView;

    @View(R.id.countdownView)
    public TextView countdownView;

    @Position
    public int position;

    private Question question;
    private Context context;
    private SwipePlaceHolderView swipeView;
    private String correctAnswer;
    private Button correctButton;
    private List<String> allChoices;
    private CountDownTimer timer;
    private QuestionView item;

    private SwipeCardCallback swipeCardCallback;

    public QuestionView(Context context, Question question, SwipePlaceHolderView swipeView){
        this.context = context;
        this.question = question;
        this.swipeView = swipeView;
        this.swipeCardCallback = (SwipeCardCallback) context;
        // get all the answers
        this.allChoices = new ArrayList<>();
                //question.getIncorrectAnswers();
        for (String choice: question.getIncorrectAnswers()){
            allChoices.add(decodedHtmlToString(choice));
        }
        this.correctAnswer = decodedHtmlToString(question.getCorrectAnswer());
        // add the correct answer
        this.allChoices.add(this.correctAnswer);
        // shuffle them
        Collections.shuffle(this.allChoices);
    }

    @Resolve
    public void onResolved(){
        item = this;
        setTextViewDecodedHtml(questionView, question.getQuestion());
        setTextViewDecodedHtml(categoryView, question.getCategory());
        //questionView.setText(question.getQuestion());
        //categoryView.setText(question.getCategory());
        for (String choice: allChoices){
            createButton(choice);
        }

        setUpTimer();
    }

    private void setUpTimer(){
         timer = new CountDownTimer(21000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (countdownView != null){
                    countdownView.setText(String.valueOf(millisUntilFinished / 1000));
                }
            }

            public void onFinish() {
                //countdownView.setText("done!");
                // failed to answer to specified time
                swipeView.doSwipe(item, false);
            }
        };
        timer.start();

    }

    private void createButton(String choice){
        Button button = new Button(context);
        button.setBackground(context.getDrawable(R.drawable.btn_background));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, dpToPx(context, 10),0,0);
        button.setLayoutParams(layoutParams);

        button.setTextSize(20f);
        button.setTextColor(Color.BLACK);
        //setTextViewDecodedHtml(button, choice);
        button.setText(choice);
        //button.setText(choice);
        button.setOnClickListener(getButtonClickListener());
        answersHolder.addView(button);

        if (choice.equals(correctAnswer)) { this.correctButton = button; }

    }

    private android.view.View.OnClickListener getButtonClickListener(){
        return new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Button button = (Button)v;
                // get text from a button
                String pick = button.getText().toString();
                final boolean swipeIn;
                int color;
                if (pick.equals(correctAnswer)){
                    showMsg(context, "YEAH this is the correct answer");
                    // swipe to the right
                    swipeIn = true;
                    // set color to green
                    color = R.color.green;
                } else{
                    showMsg(context, "Noooo, this is the wrong answer");
                    // swipe to the left
                    swipeIn = false;
                    // set color to red
                    color = R.color.red;
                    correctButton.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.green),PorterDuff.Mode.MULTIPLY);
                }
                // change color of a button
                button.getBackground().setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.MULTIPLY);

                // make a pause in 1 sec
                button.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeView.doSwipe(item, swipeIn);
                        // if we don't stop the timer it will run until it's finished
                        // and will run it's method for wrong item
                        timer.cancel();

                    }
                }, 1000);
            }
        };
    }

    @SwipeOut
    public void onSwipedOut(){
        Log.d(TAG, "DELETE");
        Log.d(TAG, "SIZE: " + swipeView.getAllResolvers().size());
        swipeCardCallback.onSwipeOut();

    }

    @SwipeCancelState
    public void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    public void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
        swipeCardCallback.onSwipeIn();
    }

    @SwipeInState
    public void onSwipeInState(){
        Log.d(TAG, "onSwipeInState. Correct Answer");
        Log.d(TAG, "SIZE: " + swipeView.getAllResolvers().size());
    }

    @SwipeOutState
    public void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }

}

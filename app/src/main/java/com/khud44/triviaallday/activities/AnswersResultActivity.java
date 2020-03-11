package com.khud44.triviaallday.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.khud44.triviaallday.R;

public class AnswersResultActivity extends BaseActivity {

    public void makeUI(){
        setContentView(R.layout.result_layout);

        int score = getIntent().getIntExtra(getString(R.string.round_score), -1);

        ImageView ratingImage = findViewById(R.id.result_image);
        TextView titleView = findViewById(R.id.result_title);
        TextView scoreView = findViewById(R.id.result_score);
        String scoreStr = (score * 10) + "%";
        scoreView.setText(scoreStr);

        if (isBetween(score, 0, 5)){
            ratingImage.setImageResource(R.drawable.rating_one);
            titleView.setText(getString(R.string.bad_score));
        }
        else if (isBetween(score, 6, 8)){
            ratingImage.setImageResource(R.drawable.rating_two);
            titleView.setText(getString(R.string.normal_score));
        } else if (isBetween(score, 9, 10)){
            ratingImage.setImageResource(R.drawable.rating_three);
            titleView.setText(getString(R.string.good_score));
        }

    }

    private boolean isBetween(int score, int lower, int upper){
        return lower <= score && score <= upper;
    }

    @Override
    public void onClick(View view){
        finish();
    }

}

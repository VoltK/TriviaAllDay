package com.khud44.triviaallday.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.khud44.triviaallday.data.MyViewModel;
import com.khud44.triviaallday.R;
import com.khud44.triviaallday.database.MyDao;
import com.khud44.triviaallday.models.dbEntries.CategoryEntry;
import com.khud44.triviaallday.models.placeholder.CategoryView;
import com.khud44.triviaallday.models.placeholder.RankView;
import com.khud44.triviaallday.models.dbEntries.RankEntry;
import com.khud44.triviaallday.models.pojo.TriviaCategory;
import com.khud44.triviaallday.utils.Utils;
import com.mindorks.placeholderview.PlaceHolderView;

import java.util.Date;
import java.util.List;

import static com.khud44.triviaallday.utils.Utils.showMsg;

public class ListActivity extends BaseActivity {

    private static final String TAG = ListActivity.class.getSimpleName();
    public static final int questionsCode = 1;
    //private SwipePlaceHolderView swipeView;

    private PlaceHolderView listView;
    private MyViewModel viewModel;
    private String mode;

    public void makeUI(){
        setContentView(R.layout.list_layout);

        Intent intent = getIntent();
        mode = intent.getStringExtra(getString(R.string.mode));

        viewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        listView = findViewById(R.id.placeholder_list);
        TextView activityTitle = findViewById(R.id.list_title);

        listView.getBuilder();

        RecyclerView.LayoutManager layoutManager;

        if (mode.equals(getString(R.string.game_mode))){

            layoutManager = new GridLayoutManager(this, 2);
            activityTitle.setText(getString(R.string.categories_label));
            listView.setLayoutManager(layoutManager);

            final Context context = this;
            viewModel.getCategories().observe(this, new Observer<List<CategoryEntry>>(){
                @Override
                public void onChanged(@Nullable List<CategoryEntry> categoryEntryList) {
                    Log.d(TAG, "CATEGORIES LEN:" + categoryEntryList.size());

                    for (CategoryEntry categoryEntry: categoryEntryList){

                        TriviaCategory triviaCategory = new TriviaCategory(categoryEntry.getId(),
                                                                           categoryEntry.getName());

                        Log.d(TAG, "Category ID: " + triviaCategory.getId() + "\nTitle: " + triviaCategory.getName());
                        listView.addView(new CategoryView(context, triviaCategory));
                    }
                }
            });

        } else{
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            activityTitle.setText(getString(R.string.top_5));
            listView.setLayoutManager(layoutManager);

            viewModel.getCategoryScores().observe(this, new Observer<List<MyDao.CategoryScore>>() {
                @Override
                public void onChanged(@Nullable List<MyDao.CategoryScore> categoryScores) {
                    for (MyDao.CategoryScore categoryScore: categoryScores){
                        //Log.d(TAG, "RANK INFO: \n" + "score = " + rankEntry.getScore() + "\ncategory = " + rankEntry.getCategory());
                        listView.addView(new RankView(categoryScore));
                        // ohuenno vse perepisal
                    }
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == questionsCode) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(getString(R.string.round_score), 0);
                int categoryId = data.getIntExtra(getString(R.string.category_id),0);
                // insert score into a table
                // int score, int level, int categoryId, Date updatedAt
                viewModel.insertRank(new RankEntry(score, "medium", categoryId, new Date()));
                // TODO дописать ranking функционал
                // insert +
                // select
                showMsg(this, "Score: " + score + "\nCategory ID: " + categoryId);
                Intent intent = new Intent(this, AnswersResultActivity.class);
                intent.putExtra(getString(R.string.round_score), score);
                startActivity(intent);
            }
            else if (resultCode == RESULT_CANCELED && data != null){
                Bundle bundle = data.getExtras();
                // delete empty category
                if (bundle != null && bundle.containsKey(getString(R.string.position))){
                        int position = data.getIntExtra(getString(R.string.position),-1);
                        if (position != -1){
                            //showMsg(this,position + "");
                            listView.removeView(position);
                        }
                    }
            }
        }
    }

}

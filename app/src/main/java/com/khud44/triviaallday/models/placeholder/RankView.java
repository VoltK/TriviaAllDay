package com.khud44.triviaallday.models.placeholder;

import android.widget.TextView;
import com.khud44.triviaallday.R;
import com.khud44.triviaallday.database.MyDao;
import com.khud44.triviaallday.models.dbEntries.RankEntry;
import com.mindorks.placeholderview.annotations.*;

import java.text.SimpleDateFormat;

@NonReusable
@Layout(R.layout.rank_view)
public class RankView {

    private static final String TAG = RankView.class.getSimpleName();

    @View(R.id.rank_score_text)
    public TextView rankScoreView;

    @View(R.id.rank_category_text)
    public TextView rankCategoryView;

    @View(R.id.rank_date_text)
    public TextView rankDateView;

    @Position
    public int position;

    private MyDao.CategoryScore categoryScore;

    public RankView(MyDao.CategoryScore categoryScore){
        this.categoryScore = categoryScore;
    }

    @Resolve
    public void onResolved() {
        rankScoreView.setText(Integer.toString(categoryScore.score));
        rankCategoryView.setText(categoryScore.catName);
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd\nHH:mm");
//        rankDateView.setText(format.format(categoryScore.updatedAt));
        rankDateView.setText(categoryScore.level);
    }
    /*
     * This method is called when the view holder is recycled
     * and used to display view for the next data set
     */
    @Recycle
    public void onRecycled(){
        // do something here
        // Example: clear some references used by earlier rendering
    }

}

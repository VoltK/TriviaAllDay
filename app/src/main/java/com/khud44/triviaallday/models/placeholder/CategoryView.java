package com.khud44.triviaallday.models.placeholder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.Button;
import com.khud44.triviaallday.activities.QuestionsActivity;
import com.khud44.triviaallday.R;
import com.khud44.triviaallday.models.pojo.TriviaCategory;
import com.mindorks.placeholderview.annotations.*;

import static com.khud44.triviaallday.activities.ListActivity.questionsCode;
import static com.khud44.triviaallday.utils.Utils.getRandomColor;

@NonReusable
@Layout(R.layout.category_view)
public class CategoryView {

    private static final String TAG = CategoryView.class.getSimpleName();

    @View(R.id.category_button)
    public Button categoryButton;

    @Position
    public int position;

    private Context context;
    private Activity activity;
    private TriviaCategory category;

    public CategoryView(Context context, TriviaCategory category){
        this.context = context;
        this.activity = (Activity) context;
        this.category = category;
    }

    @Resolve
    public void onResolved() {
        // do something here
        // example: load imageView with url image
        // TODO add a list of colors for categories and set color of each button randomly
        //int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        //categoryButton.getBackground().setColorFilter(getRandomColor(context), PorterDuff.Mode.MULTIPLY);

        categoryButton.setTextColor(Color.BLACK);
        categoryButton.setText(category.getName());
    }

    /*
     * This method is called when the view holder is recycled
     * and used to display view for the next data set
     */
//    @Recycle
//    public void onRecycled(){
//        // do something here
//        // Example: clear some references used by earlier rendering
//    }

    /*
     * This method is called when the view with id image_view is clicked.
     * onImageViewClick method could be named anything.
     */
    @Click(R.id.category_button)
    public void onButtonClick(){
        // TODO start an intent to get and show questions
        Intent intent = new Intent(context, QuestionsActivity.class);
        intent.putExtra(context.getString(R.string.category_id), category.getId());
        intent.putExtra(context.getString(R.string.category), category.getName());
        intent.putExtra(context.getString(R.string.position), position);
        activity.startActivityForResult(intent, questionsCode);
    }

}

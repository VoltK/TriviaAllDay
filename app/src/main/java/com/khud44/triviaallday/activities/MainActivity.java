package com.khud44.triviaallday.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.khud44.triviaallday.R;
import com.khud44.triviaallday.data.MyViewModel;
import com.khud44.triviaallday.models.dbEntries.CategoryEntry;

import java.util.List;

import static com.khud44.triviaallday.utils.Utils.showMsg;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public void makeUI(){
        setContentView(R.layout.start_activity_layout);

        MyViewModel myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        myViewModel.getCategories().observe(this, new Observer<List<CategoryEntry>>() {
            @Override
            public void onChanged(@Nullable List<CategoryEntry> categoryEntryList) {
                // just a hack to avoid duplicates on first observation after pre populating db with callback
            }
        });
    }

    @Override
    public void onClick(View view){
        Button button = (Button) view;
        Intent intent = new Intent(this, ListActivity.class);

        showMsg(this, "Button pressed: " + button.getText().toString());
        switch(button.getId()){
            case R.id.play_btn:
                intent.putExtra(getString(R.string.mode), getString(R.string.game_mode));
                break;
            case R.id.ranking_btn:
                intent.putExtra(getString(R.string.mode), getString(R.string.ranking_mode));
                break;
        }

        startActivity(intent);
    }

}

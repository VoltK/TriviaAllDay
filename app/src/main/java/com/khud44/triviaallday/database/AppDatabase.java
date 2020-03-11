package com.khud44.triviaallday.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import com.khud44.triviaallday.models.dbEntries.CategoryEntry;
import com.khud44.triviaallday.models.dbEntries.RankEntry;
import com.khud44.triviaallday.models.pojo.TriviaCategory;
import com.khud44.triviaallday.utils.Utils;

import java.util.List;

@Database(entities = {CategoryEntry.class, RankEntry.class}, version = 3, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "ranking";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = buildDb(context);
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    private static AppDatabase buildDb(final Context context){
        return Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, AppDatabase.DATABASE_NAME)
                .addCallback(new Callback(){
                    @Override
                    public void onOpen (@NonNull SupportSQLiteDatabase db){
                        super.onOpen(db);
                        Log.d(LOG_TAG, "Callback was called");
                        new insertCategoriesAsyncTask(sInstance, context).execute();
                    }
                })
                .fallbackToDestructiveMigration()
                .build();
    }


    public abstract MyDao taskDao();

    private static class insertCategoriesAsyncTask extends AsyncTask<Void, Void, Void> {

        MyDao myDao;
        Context context;
        insertCategoriesAsyncTask(AppDatabase db, Context context){
            myDao = db.taskDao();
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

                int count = myDao.countCategories();
                if (count == 0){
                    List<TriviaCategory> list = Utils.loadCategories(context).getTriviaCategories();
                    Log.d(LOG_TAG, "THERE ARE " + list.size() + " elements in a categories");
                    for (TriviaCategory triviaCategory: list){
                        myDao.insertCategory(new CategoryEntry(triviaCategory.getId(), triviaCategory.getName()));
                        Log.d(LOG_TAG, triviaCategory.getName() + " was inserted");
                    }
                }

                Log.d(LOG_TAG, "Categories were inserted");
                return null;
        }

    }

}

package com.khud44.triviaallday.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.khud44.triviaallday.database.AppDatabase;
import com.khud44.triviaallday.database.MyDao;
import com.khud44.triviaallday.models.dbEntries.CategoryEntry;
import com.khud44.triviaallday.models.pojo.CategoryList;
import com.khud44.triviaallday.models.pojo.QuestionList;
import com.khud44.triviaallday.models.dbEntries.RankEntry;
import com.khud44.triviaallday.models.pojo.TriviaCategory;
import com.khud44.triviaallday.retrofit.GetData;
import com.khud44.triviaallday.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

import static com.khud44.triviaallday.utils.Utils.showMsg;

public class MyRepository {

   // private  MyRepository repository;

    private MyDao myDao;
    private LiveData<List<RankEntry>> ranks;
    private LiveData<List<CategoryEntry>> categories;
    private LiveData<List<MyDao.CategoryScore>> categoryScores;
    private GetData service;
    private Context context;

//    public static MyRepository getInstance(Application application){
//        if (repository == null){
//            repository = new MyRepository(application);
//        }
//        return repository;
//    }


    public MyRepository(Application application) {
        this.context = application;
        AppDatabase database = AppDatabase.getInstance(application);
        myDao = database.taskDao();
        ranks = myDao.getTopRanking();
        categories = myDao.getAllCategories();
        categoryScores = myDao.getBestScores();
        service = RetrofitClient.getRetrofitInstance().create(GetData.class);
    }

    public MutableLiveData<QuestionList> getQuestions(int catId){
        final MutableLiveData<QuestionList> questionsData = new MutableLiveData<>();

//        Call<QuestionList> call = service.getQuestions(catId, "medium");
        service.getQuestions(catId, "medium").enqueue(new Callback<QuestionList>() {
            @Override
            public void onResponse(Call<QuestionList> call, Response<QuestionList> response) {
                QuestionList questionList = response.body();
                if (questionList != null) {
                    questionsData.setValue(questionList);
                    //populateQuestionCards(response.body());
                } else {
                    showMsg(context, "Failed to load questions");
                }
            }
            @Override
            public void onFailure(Call<QuestionList> call, Throwable t) {
                questionsData.setValue(null);
            }
        });
        return questionsData;
    }

    public LiveData<List<MyDao.CategoryScore>> getCategoryScores() {
        return categoryScores;
    }

    public LiveData<List<CategoryEntry>> getCategories (){ return categories;}

    public LiveData<List<RankEntry>> getTopRanking(){
        return ranks;
    }

    public void insertRank(RankEntry rankEntry){
        new insertRankAsyncTask(myDao).execute(rankEntry);
    }

    public void insertCategory(CategoryEntry categoryEntry){
        new insertCategoryAsyncTask(myDao).execute(categoryEntry);
    }


    private static class insertRankAsyncTask extends AsyncTask<RankEntry, Void, Void> {

        private MyDao myDao;

        insertRankAsyncTask(MyDao myDao){
            this.myDao = myDao;
        }

        @Override
        protected Void doInBackground(RankEntry... params) {
            myDao.insertRank(params[0]);
            return null;
        }

    }

    private static class insertCategoryAsyncTask extends AsyncTask<CategoryEntry, Void, Void> {

        private MyDao myDao;

        insertCategoryAsyncTask(MyDao myDao){
            this.myDao = myDao;
        }

        @Override
        protected Void doInBackground( CategoryEntry... params) {
            myDao.insertCategory(params[0]);
            return null;
        }

    }

}

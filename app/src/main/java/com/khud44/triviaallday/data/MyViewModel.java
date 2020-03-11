package com.khud44.triviaallday.data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.khud44.triviaallday.database.MyDao;
import com.khud44.triviaallday.models.dbEntries.CategoryEntry;
import com.khud44.triviaallday.models.pojo.QuestionList;
import com.khud44.triviaallday.models.dbEntries.RankEntry;

import java.util.List;

public class MyViewModel extends AndroidViewModel {

    private MyRepository repository;

    private LiveData<List<RankEntry>> ranks;

    private LiveData<List<CategoryEntry>> categories;

    private LiveData<List<MyDao.CategoryScore>> categoryScores;

    public MyViewModel(Application application) {
        super(application);
        repository = new MyRepository(application);
        ranks = repository.getTopRanking();
        categories = repository.getCategories();
        categoryScores = repository.getCategoryScores();
    }

    public LiveData<List<MyDao.CategoryScore>> getCategoryScores(){
        return categoryScores;
    }

    public MutableLiveData<QuestionList> getQuestionListData(int catId){
        return repository.getQuestions(catId);
    }

    public LiveData<List<RankEntry>> getRanks() { return ranks; }

    public LiveData<List<CategoryEntry>> getCategories() { return categories; }

    public void insertRank(RankEntry rankEntry) { repository.insertRank(rankEntry); }

    public void insertCategory(CategoryEntry categoryEntry){
        repository.insertCategory(categoryEntry);
    }

}

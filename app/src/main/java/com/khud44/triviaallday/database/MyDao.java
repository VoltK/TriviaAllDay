package com.khud44.triviaallday.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.*;
import com.khud44.triviaallday.models.dbEntries.CategoryEntry;
import com.khud44.triviaallday.models.dbEntries.RankEntry;
import com.khud44.triviaallday.models.pojo.TriviaCategory;

import java.util.Date;
import java.util.List;

@Dao
public interface MyDao {

    @Query("SELECT * FROM ranks ORDER BY score DESC LIMIT 5")
    LiveData<List<RankEntry>> getTopRanking();

    @Insert
    void insertRank(RankEntry rankEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRank(RankEntry rankEntry);

    @Insert
    void insertCategory(CategoryEntry categoryEntry);

    @Query("SELECT * FROM categories")
    LiveData<List<CategoryEntry>> getAllCategories();

    @Query("Delete FROM categories")
    void deleteCategories();

    @Query("SELECT count(*) FROM categories")
    int countCategories();

    @Query("DELETE FROM categories WHERE name=:toDelete")
    void deleteCategory(String toDelete);

//    @Query("SELECT categories.name as catName, ranks.score as score, ranks.level as level, ranks.updated_at as updatedAt " +
//            "FROM categories, ranks " +
//            "WHERE categories.id = ranks.categoryId " +
//            "ORDER BY ranks.score DESC LIMIT 5")
@Query("SELECT categories.name as catName, ranks.score as score, ranks.level as level, ranks.updated_at as updatedAt " +
        "FROM categories INNER JOIN ranks ON categories.id = ranks.categoryId ORDER BY ranks.score DESC LIMIT 5")
    LiveData<List<CategoryScore>> getBestScores();

    static class CategoryScore {
        public String catName;
        public int score;
        public String level;
        public Date updatedAt;
    }

//    @Insert
//    void insertCategories(CategoryEntry... categories);

//    @Delete
//    void deleteRank(RankEntry rankEntry);

}

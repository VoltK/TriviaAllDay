package com.khud44.triviaallday.database;

import android.arch.persistence.room.TypeConverter;
import com.khud44.triviaallday.models.dbEntries.CategoryEntry;
import com.khud44.triviaallday.models.pojo.TriviaCategory;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static TriviaCategory toTriviaCategory(CategoryEntry categoryEntry){
        return categoryEntry == null ? null : new TriviaCategory(categoryEntry.getId(), categoryEntry.getName());
    }

    public static CategoryEntry toCategoryEntry(TriviaCategory triviaCategory){
        return triviaCategory == null ? null : new CategoryEntry(triviaCategory.getId(), triviaCategory.getName());
    }
}

package com.khud44.triviaallday.models.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryList {

    @SerializedName("trivia_categories")
    @Expose
    private List<TriviaCategory> triviaCategories = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public CategoryList() {
    }

    /**
     *
     * @param triviaCategories
     */
    public CategoryList(List<TriviaCategory> triviaCategories) {
        super();
        this.triviaCategories = triviaCategories;
    }

    public List<TriviaCategory> getTriviaCategories() {
        return triviaCategories;
    }

    public void setTriviaCategories(List<TriviaCategory> triviaCategories) {
        this.triviaCategories = triviaCategories;
    }

}

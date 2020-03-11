package com.khud44.triviaallday.models.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TriviaCategory {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     *
     */
    public TriviaCategory() {
    }

    /**
     *
     * @param name
     * @param id
     */
    public TriviaCategory(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}

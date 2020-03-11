
package com.khud44.triviaallday.models.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.khud44.triviaallday.models.pojo.Question;

public class QuestionList {

    @SerializedName("response_code")
    @Expose
    private int responseCode;
    @SerializedName("results")
    @Expose
    private List<Question> results = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public QuestionList() {
    }

    /**
     * 
     * @param results
     * @param responseCode
     */
    public QuestionList(int responseCode, List<Question> results) {
        super();
        this.responseCode = responseCode;
        this.results = results;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public List<Question> getResults() {
        return results;
    }

    public void setResults(List<Question> results) {
        this.results = results;
    }

}

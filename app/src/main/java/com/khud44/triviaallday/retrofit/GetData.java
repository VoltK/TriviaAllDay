package com.khud44.triviaallday.retrofit;

import com.khud44.triviaallday.models.pojo.QuestionList;
import com.khud44.triviaallday.models.pojo.Token;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetData {

    @GET("api.php?amount=10")
    Call<QuestionList> getQuestions(//@Query("amount") int amount,
                                    @Query("category") int categoryId,
                                    @Query("difficulty") String difficulty);

    @GET("api_token.php?command=request")
    Call<Token> getToken();

}

package com.khud44.triviaallday.models.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("response_code")
    @Expose
    private int responseCode;
    @SerializedName("response_message")
    @Expose
    private String responseMessage;
    @SerializedName("token")
    @Expose
    private String token;

    /**
     * No args constructor for use in serialization
     *
     */
    public Token() {
    }

    /**
     *
     * @param responseMessage
     * @param responseCode
     * @param token
     */
    public Token(int responseCode, String responseMessage, String token) {
        super();
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.token = token;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}


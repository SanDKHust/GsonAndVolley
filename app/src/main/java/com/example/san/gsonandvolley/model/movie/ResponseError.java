package com.example.san.gsonandvolley.model.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by San on 1/16/2018.
 */

public class ResponseError {
    @SerializedName("Response")
    @Expose
    private String response;
    @SerializedName("Error")
    @Expose
    private String error;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}

package com.example.tmorimatsu.newsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/v2/top-headlines")
    Call<ResponseData> getNews(@Query("apiKey") String apiKey, @Query("country") String country);
}

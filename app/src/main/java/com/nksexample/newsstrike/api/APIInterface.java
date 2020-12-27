package com.nksexample.newsstrike.api;

import com.nksexample.newsstrike.model.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface  APIInterface {

    @GET("top-headlines")
    Call<NewsModel> getLocalNews(

            @Query("country") String country,
            @Query("apiKey") String apiKey

    );

    @GET("top-headlines")
    Call<NewsModel> getTrendNews(

            @Query("q") String country,
            @Query("apiKey") String apiKey

    );

    @GET("everything")
    Call<NewsModel> getCovidNews(

            @Query("q") String keyword,
            @Query("apiKey") String apiKey

    );

    @GET("everything")
    Call<NewsModel> getNewsSearch(

            @Query("q") String keyword,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey

    );

}

package com.example.yeeybook.whattoon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyAPI {

    @POST("recommender/")
    Call<RecommenderItem> post_recommenders(@Body RecommenderItem recommender);

    @GET("recommender/")
    Call<List<RecommenderItem>> get_recommenders();

    @GET("recommender/{pk}/")
    Call<RecommenderItem> get_recommender_pk(@Path("pk") int pk);
}

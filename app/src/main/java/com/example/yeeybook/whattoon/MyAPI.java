package com.example.yeeybook.whattoon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyAPI {

    @FormUrlEncoded
    @POST("predict/")
    Call<RecommenderItem> post_recommenders(@Field("uid") String currentUid);

//    @POST("predict/")
//    Call<RecommenderItem> post_recommenders(@Body RecommenderItem recommenderItem);

//    @POST("recommender/")
//    Call<RecommenderItem> post_recommenders(@Body RecommenderItem recommender);
//
//    @GET("recommender/")
//    Call<List<RecommenderItem>> get_recommenders();
//
//    @GET("recommender/{pk}/")
//    Call<RecommenderItem> get_recommender_pk(@Path("pk") int pk);
}

package com.kutt.it.kutt_android;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API
{


    @POST("submit")
    Call<String> submit(@Header("Content-type") String contentType, @Body String postData);

    @GET("geturls")
    Call<String> geturls();

    @POST("deleteurl")
    Call<String> deleteUrl(@Header("Content-type") String contentType, @Body String postData);

    @GET("stats")
    Call<String> stats(@Query("id") String id);

    @GET("stats")
    Call<String> stats(@Query("id") String id, @Query("domain") String domain);





}

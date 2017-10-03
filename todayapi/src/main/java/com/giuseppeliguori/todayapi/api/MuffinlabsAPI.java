package com.giuseppeliguori.todayapi.api;

import com.giuseppeliguori.todayapi.apiclass.Header;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by giuseppeliguori on 9/25/17.
 */

interface MuffinlabsAPI {
    @GET("/date/{month}/{day}")
    Call<Header> getData(@Path("month") int month, @Path("day") int day);

    @GET("/date")
    Call<Header> getData();
}

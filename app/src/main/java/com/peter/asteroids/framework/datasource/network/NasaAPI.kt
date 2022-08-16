package com.peter.asteroids.framework.datasource.network

import com.peter.asteroids.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaAPI {
    @GET("neo/rest/v1/feed")
    fun getDataAsync(
        @Query("start_date") startDate: String,
//        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Call<String>

    @GET("planetary/apod")
    fun getImageOfDayAsync(@Query("api_key") apiKey: String = BuildConfig.API_KEY): Call<String>
}
package com.gmail.everloser12.fishingweather.rest;

import com.gmail.everloser12.fishingweather.model.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by al-ev on 29.04.2016.
 */
public interface IOWApi {

    @GET("data/2.5/forecast/daily")
    Call<Root> getWeather(@Query("lat") double lat, @Query("lon") double lon, @Query("cnt") int cnt, @Query("APPID") String string);
}

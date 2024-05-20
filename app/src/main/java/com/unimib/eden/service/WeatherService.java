package com.unimib.eden.service;

import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.model.weather.WeatherHistory;
import com.unimib.eden.model.weather.WeatherSearchLocation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("forecast.json")
    Call<WeatherForecast> getForecast(
            @Query("key") String apiKey,
            @Query("q") String location,
            @Query("days") int days,
            @Query("aqi") String aqi,
            @Query("alerts") String alerts
    );

    @GET("history.json")
    Call<WeatherHistory> getHistory(
            @Query("key") String apiKey,
            @Query("q") String location,
            @Query("dt") String date
    );

    @GET("search.json")
    Call<List<WeatherSearchLocation>> getAutocompleteSearch(
            @Query("key") String apiKey,
            @Query("q") String locationSearch
    );
}
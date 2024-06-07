package com.unimib.eden.service;

import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.model.weather.WeatherHistory;
import com.unimib.eden.model.weather.WeatherSearchLocation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * WeatherService is an interface that defines methods to interact with the weather services API using Retrofit.
 * It includes methods to obtain weather forecasts, historical data, and location searches.
 */
public interface WeatherService {

    /**
     * Gets the weather forecast for a specific location.
     *
     * @param apiKey The API key to authenticate with the service.
     * @param location The location for which to get the weather forecast.
     * @param days The number of days to get the forecast for.
     * @param aqi Indicates whether to include air quality data or not.
     * @param alerts Indicates whether to include weather alerts or not.
     * @return A {@link Call} object that can be used to request the weather forecast.
     */
    @GET("forecast.json")
    Call<WeatherForecast> getForecast(
            @Query("key") String apiKey,
            @Query("q") String location,
            @Query("days") int days,
            @Query("aqi") String aqi,
            @Query("alerts") String alerts
    );

    /**
     * Gets historical weather data for a specific location and date.
     *
     * @param apiKey The API key to authenticate with the service.
     * @param location The location for which to get historical data.
     * @param date The date for which to get historical data, in "yyyy-MM-dd" format.
     * @return A {@link Call} object that can be used to request historical weather data.
     */
    @GET("history.json")
    Call<WeatherHistory> getHistory(
            @Query("key") String apiKey,
            @Query("q") String location,
            @Query("dt") String date
    );

    /**
     * Performs an autocomplete search for locations matching a specific query.
     *
     * @param apiKey The API key to authenticate with the service.
     * @param locationSearch The search query to find matching locations.
     * @return A {@link Call} object that can be used to request location search results.
     */
    @GET("search.json")
    Call<List<WeatherSearchLocation>> getAutocompleteSearch(
            @Query("key") String apiKey,
            @Query("q") String locationSearch
    );
}

package com.unimib.eden.repository;

import androidx.lifecycle.LiveData;

import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.model.weather.WeatherHistory;
import com.unimib.eden.model.weather.WeatherSearchLocation;

import java.util.Date;
import java.util.List;

/**
 * Interface for the weather data repository.
 */
public interface IWeatherRepository {
    /**
     * Retrieves historical weather data for a given location and date.
     *
     * @param location The location for which to get the history.
     * @param date The date for which to get the history.
     * @return LiveData containing the historical weather data.
     */
    LiveData<WeatherHistory> getHistory(String location, Date date);

    /**
     * Retrieves weather search locations based on a search query.
     *
     * @param locationSearch The search query for the location.
     * @return LiveData containing a list of weather search locations.
     */
    LiveData<List<WeatherSearchLocation>> getSearchlocation(String locationSearch);

    /**
     * Retrieves weather forecast data for a given location and number of days.
     *
     * @param location The location for which to get the forecast.
     * @param days The number of days for the forecast.
     * @param aqi Parameter to request the air quality index.
     * @param alerts Parameter to request any alerts.
     * @return LiveData containing the weather forecast data.
     */
    LiveData<WeatherForecast> getForecast(String location, int days, String aqi, String alerts);
}

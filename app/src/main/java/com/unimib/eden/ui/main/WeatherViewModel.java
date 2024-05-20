package com.unimib.eden.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.model.weather.WeatherHistory;
import com.unimib.eden.model.weather.WeatherSearchLocation;
import com.unimib.eden.repository.WeatherRepository;

import java.time.LocalDate;
import java.util.List;

public class WeatherViewModel extends ViewModel {
    private WeatherRepository repository;
    private LiveData<WeatherForecast> forecastLiveData;
    private LiveData<WeatherHistory> historyLiveData;
    private LiveData<List<WeatherSearchLocation>> searchLocationLiveData;

    public WeatherViewModel() {
        repository = new WeatherRepository();
    }

    public LiveData<WeatherForecast> getForecast(String location, int days, String aqi, String alerts) {
        forecastLiveData = repository.getForecast(location, days, aqi, alerts);
        return forecastLiveData;
        /*if (forecast == null) {
            forecast = repository.getForecast(location, days, aqi, alerts);
        }
        return forecast;
         */
    }

    public LiveData<WeatherHistory> getHistory(String location, LocalDate localDate){
        historyLiveData = repository.getHistory(location, localDate);
        return historyLiveData;
    }

    public LiveData<List<WeatherSearchLocation>> getSearchLocation(String searchLocation){
        searchLocationLiveData = repository.getSearchlocation(searchLocation);
        return searchLocationLiveData;
    }
}
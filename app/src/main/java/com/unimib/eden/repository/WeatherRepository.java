package com.unimib.eden.repository;

import static com.unimib.eden.utils.Constants.BASE_URL;
import static com.unimib.eden.utils.Constants.apiKey;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.model.weather.WeatherHistory;
import com.unimib.eden.model.weather.WeatherSearchLocation;
import com.unimib.eden.service.WeatherService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Repository to handle weather data requests.
 */
public class WeatherRepository implements IWeatherRepository {
    private final WeatherService service;

    /**
     * Constructor that initializes the repository with the network service.
     * Uses Retrofit for HTTP communication.
     */
    public WeatherRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(WeatherService.class);
    }

    /**
     * Constructor that allows injecting a custom WeatherService.
     *
     * @param service The WeatherService to use.
     */
    public WeatherRepository(WeatherService service) {
        this.service = service;
    }

    /**
     * Retrieves historical weather data for a given location and date.
     *
     * @param location The location for which to get the history.
     * @param date The date for which to get the history.
     * @return LiveData containing the historical weather data.
     */
    public LiveData<WeatherHistory> getHistory(String location, Date date) {
        MutableLiveData<WeatherHistory> data = new MutableLiveData<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        String month = String.format("%02d", calendar.get(Calendar.MONTH) + 1);  // Calendar.MONTH is zero-based
        String day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
        String formattedDate = year + "-" + month + "-" + day;
        Call<WeatherHistory> call = service.getHistory(apiKey, location, formattedDate);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<WeatherHistory> call, @NonNull Response<WeatherHistory> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherHistory> call, @NonNull Throwable throwable) {
            }
        });

        return data;
    }

    /**
     * Retrieves weather search locations based on a search query.
     *
     * @param locationSearch The search query for the location.
     * @return LiveData containing a list of weather search locations.
     */
    public LiveData<List<WeatherSearchLocation>> getSearchlocation(String locationSearch) {
        MutableLiveData<List<WeatherSearchLocation>> data = new MutableLiveData<>();
        Call<List<WeatherSearchLocation>> call = service.getAutocompleteSearch(apiKey, locationSearch);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<WeatherSearchLocation>> call, @NonNull Response<List<WeatherSearchLocation>> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<WeatherSearchLocation>> call, @NonNull Throwable throwable) {
            }
        });

        return data;
    }

    /**
     * Retrieves weather forecast data for a given location and number of days.
     *
     * @param location The location for which to get the forecast.
     * @param days The number of days for the forecast.
     * @param aqi Parameter to request the air quality index.
     * @param alerts Parameter to request any alerts.
     * @return LiveData containing the weather forecast data.
     */
    public LiveData<WeatherForecast> getForecast(String location, int days, String aqi, String alerts) {
        MutableLiveData<WeatherForecast> data = new MutableLiveData<>();
        Call<WeatherForecast> call = service.getForecast(apiKey, location, days, aqi, alerts);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<WeatherForecast> call, @NonNull Response<WeatherForecast> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherForecast> call, @NonNull Throwable throwable) {
            }
        });

        return data;
    }
}

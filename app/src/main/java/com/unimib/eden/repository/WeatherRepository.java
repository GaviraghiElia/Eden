package com.unimib.eden.repository;

import static com.unimib.eden.utils.Constants.BASE_URL;
import static com.unimib.eden.utils.Constants.apiKey;

import android.util.Log;

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

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

//TODO: è il weather repository c he fa le richieste all API e riempie i dati.
// poi li passa al IrrigazioniFragment, con quelle di oggi, domani, dopodomani


public class WeatherRepository {
    private WeatherService service;

    public WeatherRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(WeatherService.class);
    }

    public LiveData<WeatherHistory> getHistory(String location, LocalDate date) {
        MutableLiveData<WeatherHistory> data = new MutableLiveData<>();
        Call<WeatherHistory> call = service.getHistory(apiKey, location, date.toString());

        call.enqueue(new Callback<WeatherHistory>() {
            @Override
            public void onResponse(Call<WeatherHistory> call, Response<WeatherHistory> response) {
                Log.d("WeatherApp", "History - repository - onResponse");
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    Log.d("WeatherAppLog", "History - repository - onResponse success");
                }else{
                    Log.d("WeatherAppLog", "History - repository - onResponse not success");
                    Log.d("WeatherAppLog", "History - " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<WeatherHistory> call, Throwable throwable) {
                Log.d("WeatherAppLog", "History - " + call.toString());
                Log.d("WeatherAppLog", "History - repository - onFailure");
                Log.d("WeatherAppLog", "History - " + Objects.requireNonNull(throwable.getMessage()));
                Log.d("WeatherAppLog", "History - " + Objects.requireNonNull(throwable.getLocalizedMessage()));
            }
        });

        return data;
    }

    public LiveData<List<WeatherSearchLocation>> getSearchlocation(String locationSearch){
        MutableLiveData<List<WeatherSearchLocation>> data = new MutableLiveData<>();
        Call<List<WeatherSearchLocation>> call = service.getAutocompleteSearch(apiKey, locationSearch);
        call.enqueue(new Callback<List<WeatherSearchLocation>>() {
            @Override
            public void onResponse(Call<List<WeatherSearchLocation>> call, Response<List<WeatherSearchLocation>> response) {
                // ...
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    Log.d("WeatherAppLogSearch", "SearchLocation - repository - onResponse success");
                }else{
                    Log.d("WeatherAppLogSearch", "SearchLocation - repository - onResponse not success");
                    Log.d("WeatherAppLogSearch", "SearchLocation - " + response.toString());
                }
            }
            @Override
            public void onFailure(Call<List<WeatherSearchLocation>> call, Throwable throwable) {
                Log.d("WeatherAppLogSearch", "SearchLocation - repository - onFailure");
                Log.d("WeatherAppLogSearch", "SearchLocation - " + Objects.requireNonNull(throwable.getMessage()));
                Log.d("WeatherAppLogSearch", "SearchLocation - " + Objects.requireNonNull(throwable.getLocalizedMessage()));
            }
        });
        return data;
    }

    public LiveData<WeatherForecast> getForecast(String location, int days, String aqi, String alerts) {
        MutableLiveData<WeatherForecast> data = new MutableLiveData<>();
        Call<WeatherForecast> call = service.getForecast(apiKey, location, days, aqi, alerts);

        call.enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                Log.d("WeatherApp", "repository - onResponse");
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                    Log.d("WeatherAppLog", "repository - onResponse success");
                }else{
                    Log.d("WeatherAppLog", "repository - onResponse not success");
                    Log.d("WeatherAppLog", response.toString());
                }
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable throwable) {
                Log.d("WeatherAppLog", call.toString());
                Log.d("WeatherAppLog", "repository - onFailure");
                Log.d("WeatherAppLog", Objects.requireNonNull(throwable.getMessage()));
                Log.d("WeatherAppLog", Objects.requireNonNull(throwable.getLocalizedMessage()));
            }
        });

        return data;
    }
}

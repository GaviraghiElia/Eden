package com.unimib.eden.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.weather.Day;
import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.model.weather.WeatherHistory;
import com.unimib.eden.model.weather.WeatherSearchLocation;
import com.unimib.eden.repository.CropRepository;
import com.unimib.eden.repository.WeatherRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ViewModel for managing and providing weather data and updating crops accordingly.
 */
public class WeatherViewModel extends AndroidViewModel {
    private final WeatherRepository repository;
    private final CropRepository cropRepository;
    private LiveData<WeatherForecast> forecastLiveData;
    private LiveData<WeatherHistory> historyLiveData;
    private LiveData<List<WeatherSearchLocation>> searchLocationLiveData;

    private final LiveData<List<Coltura>> mCrops;

    private static final String TAG = "WeatherViewModel";

    /**
     * Constructor for WeatherViewModel.
     * Initializes the repositories and fetches all crops.
     *
     * @param application The application context.
     */
    public WeatherViewModel(Application application) {
        super(application);
        repository = new WeatherRepository();
        cropRepository = new CropRepository(application);

        mCrops = cropRepository.getAllCrops();
    }

    /**
     * Retrieves weather forecast data.
     *
     * @param location The location for the forecast.
     * @param days     The number of days for the forecast.
     * @param aqi      Air Quality Index parameter.
     * @param alerts   Alerts parameter.
     * @return LiveData containing the weather forecast.
     */
    public LiveData<WeatherForecast> getForecast(String location, int days, String aqi, String alerts) {
        forecastLiveData = repository.getForecast(location, days, aqi, alerts);
        return forecastLiveData;
    }

    /**
     * Retrieves weather history data.
     *
     * @param location The location for the history.
     * @param date     The date for the history.
     * @return LiveData containing the weather history.
     */
    public LiveData<WeatherHistory> getHistory(String location, Date date) {
        historyLiveData = repository.getHistory(location, date);
        return historyLiveData;
    }

    /**
     * Retrieves weather search locations.
     *
     * @param searchLocation The search query for the location.
     * @return LiveData containing a list of weather search locations.
     */
    public LiveData<List<WeatherSearchLocation>> getSearchLocation(String searchLocation) {
        searchLocationLiveData = repository.getSearchlocation(searchLocation);
        return searchLocationLiveData;
    }

    /**
     * Updates the watering data for crops based on past precipitation.
     *
     * @param dayWeather The weather conditions of the past day.
     * @param crops      List of crops to be updated.
     */
    public void updateWateringDays(Day dayWeather, List<Coltura> crops) {
        Map<Coltura, Date> updates = updateDatesOfWatering(crops, dayWeather);
        cropRepository.updateCropsWateringDate(updates);
    }

    /**
     * Retrieves all crops.
     *
     * @return LiveData containing a list of crops.
     */
    public LiveData<List<Coltura>> getCrops() {
        return mCrops;
    }

    /**
     * Determines the last watering date for each crop based on past weather conditions.
     *
     * @param crops      The list of crops.
     * @param dayWeather The past weather conditions.
     * @return A map containing crop-date pairs to be updated.
     */
    public static Map<Coltura, Date> updateDatesOfWatering(List<Coltura> crops, Day dayWeather) {
        Map<Coltura, Date> datesForCrops = new HashMap<>();
        Date currentDate = new Date();
        for (Coltura coltura : crops) {
            Date lastUpdate = coltura.getUltimoAggiornamento();
            // Check if the crop has not been updated today
            if (!(lastUpdate.getDay() == currentDate.getDay()
                    && lastUpdate.getMonth() == currentDate.getMonth()
                    && lastUpdate.getYear() == currentDate.getYear())) {
                // It rained enough to consider the plants as watered (yesterday)
                if (dayWeather.getTotalprecip_mm() > 20) {
                    Date newDate = new Date(currentDate.getTime() - 1 * 24 * 60 * 60 * 1000);
                    datesForCrops.put(coltura, newDate);
                }
                // It was too hot and dry, bring forward the watering date by one day
                else if (dayWeather.getAvgtemp_c() > 35 && dayWeather.getAvghumidity() < 50) {
                    Date newDate = new Date(coltura.getUltimoInnaffiamento().getTime() - 2 * 24 * 60 * 60 * 1000);
                    datesForCrops.put(coltura, newDate);
                } else {
                    // Return null as a date when there is no need to update it
                    datesForCrops.put(coltura, null);
                }
            }
        }
        return datesForCrops;
    }
}

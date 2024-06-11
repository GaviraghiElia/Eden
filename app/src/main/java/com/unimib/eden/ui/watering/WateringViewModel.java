package com.unimib.eden.ui.watering;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.model.Crop;
import com.unimib.eden.model.Phase;
import com.unimib.eden.model.Plant;
import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.repository.CropRepository;
import com.unimib.eden.repository.PhaseRepository;
import com.unimib.eden.repository.PlantRepository;
import com.unimib.eden.repository.WeatherRepository;

import java.util.Date;
import java.util.List;

/**
 * ViewModel class for WateringFragment.
 * This class handles the data related to WateringFragment.
 */
public class WateringViewModel extends AndroidViewModel {
    private static final String TAG = "WateringViewModel";

    private final List<Plant> mPlants;
    private final List<Phase> mPhases;
    private final LiveData<List<Crop>> mCrops;
    private final LiveData<List<Crop>> mCropsToWater;
    private final PlantRepository plantRepository;
    private final CropRepository cropRepository;
    private final PhaseRepository phaseRepository;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final WeatherRepository repository;
    private LiveData<WeatherForecast> forecastLiveData;

    /**
     * Constructor for HomeViewModel.
     *
     * @param application An instance of the application.
     */
    public WateringViewModel(Application application) {
        super(application);

        // Initialize repositories
        plantRepository = new PlantRepository(application);
        phaseRepository = new PhaseRepository(application);
        cropRepository = new CropRepository(application);
        repository = new WeatherRepository();
        // Retrieve data from repositories
        mPlants = plantRepository.getAllPlants();
        mPhases = phaseRepository.getAllPhases();
        mCrops = cropRepository.getAllCrops();
        mCropsToWater = cropRepository.getAllCropsToWater((new Date()).getTime() / (1000 * 60 * 60 * 24));
    }

    /**
     * Retrieve the weather forecast for a specified location.
     *
     * @param location The location for which the weather forecast is to be retrieved.
     *                 Can be a city name, postal code, or geographic coordinates.
     * @param days     The number of days for which weather forecasts are required.
     * @param aqi      Indicates whether the Air Quality Index (AQI) data should be included.
     *                 Possible values can be "yes" or "no".
     * @param alerts   Indicates whether weather alerts should be included.
     *                 Possible values can be "yes" or "no".
     * @return A LiveData object containing the weather forecast data.
     *         Observers can use this LiveData to receive updates when the forecast data changes.
     */
    public LiveData<WeatherForecast> getForecast(String location, int days, String aqi, String alerts) {
        forecastLiveData = repository.getForecast(location, days, aqi, alerts);
        return forecastLiveData;
    }

    /**
     * Retrieve a list of plants.
     *
     * @return A list of plants.
     */
    public List<Plant> getPlants() {
        return mPlants;
    }

    /**
     * Retrieve a list of phases.
     *
     * @return A list of phases.
     */
    public List<Phase> getPhases() {
        return mPhases;
    }

    /**
     * Retrieve a list of crops.
     *
     * @return A list of crops.
     */
    public LiveData<List<Crop>> getCrops() {
        return mCrops;
    }

    /**
     * Retrieve all crops to be watered on the current date.
     *
     * @return A list of all crops to be watered on the current date.
     */
    public LiveData<List<Crop>> getCropsToWater() {
        return mCropsToWater;
    }

    /**
     * Retrieve a plant by its ID.
     *
     * @param plantId The ID of the plant to be retrieved.
     * @return The plant with the specified ID.
     */
    private Plant getPlantById(String plantId) {
        return plantRepository.getPlantById(plantId);
    }

    /**
     * Update the local database.
     */
    public void updateDB(String currentUserId) {
        // Update the local database with crop data
        plantRepository.updateLocalDB();
        phaseRepository.updateLocalDB();
        cropRepository.updateLocalDB(currentUserId);
    }

    /**
     * Update the last watering date to the current date for the specified crop.
     *
     * @param crop The crop for which the last watering date needs to be updated to the current date.
     */
    public void updateWateringDateCrop(Crop crop) {
        cropRepository.updateCropWateringDate(crop);
    }

    /**
     * Update the last watering date for the specified crop to the specified date.
     *
     * @param crop The crop for which the last watering date needs to be updated.
     * @param newDate The date to which the last watering date of the specified crop needs to be updated.
     */
    public void updateWateringDateCrop(Crop crop, Date newDate) {
        cropRepository.updateCropWateringDate(crop, newDate);
    }
}

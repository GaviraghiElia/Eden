package com.unimib.eden.ui.searchPlant;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unimib.eden.model.Plant;
import com.unimib.eden.repository.PlantRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * ViewModel class for the SearchPlantActivity.
 * Manages the data for the SearchPlantActivity and handles the logic for searching and filtering plants.
 */
public class SearchPlantViewModel extends AndroidViewModel {
    private static final String TAG = "SearchPlantViewModel";
    private final PlantRepository plantRepository;
    private final MutableLiveData<List<Plant>> plantListLiveData;

    /**
     * Constructor that initializes the ViewModel with the application context.
     * Initializes the plant repository and an empty plant list.
     *
     * @param application The application context.
     */
    public SearchPlantViewModel(@NonNull Application application) {
        super(application);
        plantRepository = new PlantRepository(application);
        plantListLiveData = new MutableLiveData<>();
    }

    /**
     * Returns a LiveData object containing a list of all plants in the database.
     * If the plant list is null, it performs a search with an empty query.
     *
     * @return A LiveData object containing the list of plants.
     */
    public LiveData<List<Plant>> getPlantsList() {
        if (plantListLiveData.getValue() == null) {
            searchPlant("");
        }
        return plantListLiveData;
    }

    /**
     * Returns a LiveData object containing a list of plants that match the specified filters.
     * If the plant list is null, it performs a search with an empty query and the given filters.
     *
     * @param filtersMap A map containing the search filters.
     * @return A LiveData object containing the filtered list of plants.
     */
    public LiveData<List<Plant>> getPlantsList(Map<String, String> filtersMap) {
        if (plantListLiveData.getValue() == null) {
            searchPlant("", filtersMap);
        }
        return plantListLiveData;
    }

    /**
     * Updates the plant list in the ViewModel by searching for plants that match the given query.
     *
     * @param query The query string used to search for plants.
     */
    public void searchPlant(String query) {
        try {
            plantListLiveData.postValue(plantRepository.searchPlants(query));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the plant list in the ViewModel by searching for plants that match the given query and filters.
     *
     * @param query      The query string used to search for plants.
     * @param filtersMap A map containing the search filters.
     */
    public void searchPlant(String query, Map<String, String> filtersMap) {
        try {
            plantListLiveData.postValue(plantRepository.searchPlants(query, filtersMap));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

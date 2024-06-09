package com.unimib.eden.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.unimib.eden.model.Fase;
import com.unimib.eden.model.Crop;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.PhaseRepository;
import com.unimib.eden.repository.CropRepository;
import com.unimib.eden.repository.PlantRepository;

import java.util.List;

/**
 * ViewModel class for HomeFragment.
 * This class handles the data related to HomeFragment.
 */
public class HomeViewModel extends AndroidViewModel {

    private static final String TAG = "HomeViewModel";

    private final List<Pianta> mPlants;
    private final List<Fase> mPhases;
    private final LiveData<List<Crop>> mCrops;
    private final PlantRepository plantsRepository;
    private final CropRepository cropRepository;
    private final PhaseRepository phaseRepository;

    /**
     * Constructor for HomeViewModel.
     *
     * @param application An instance of the application.
     */
    public HomeViewModel(Application application) {
        super(application);

        // Initialize repositories
        plantsRepository = new PlantRepository(application);
        phaseRepository = new PhaseRepository(application);
        cropRepository = new CropRepository(application);

        // Retrieve data from repositories
        mPlants = plantsRepository.getAllPlants();
        mPhases = phaseRepository.getAllPhases();
        mCrops = cropRepository.getAllCrops();
    }

    /**
     * Get a list of plants.
     *
     * @return A list of plants.
     */
    public List<Pianta> getPlants() {
        return mPlants;
    }

    /**
     * Get a list of phases.
     *
     * @return A list of phases.
     */
    public List<Fase> getPhases() {
        return mPhases;
    }

    /**
     * Get a list of crops.
     *
     * @return A list of crops.
     */
    public LiveData<List<Crop>> getCrops() {
        return mCrops;
    }

    /**
     * Get a plant by its ID.
     *
     * @param plantId The ID of the plant to retrieve.
     * @return The plant with the specified ID.
     */
    private Pianta getPlantById(String plantId) {
        return plantsRepository.getPlantById(plantId);
    }

    /**
     * Update the local database.
     *
     * @param currentUserId The ID of the current user.
     */
    public void updateDB(String currentUserId) {
        // Update the local database with crop data
        plantsRepository.updateLocalDB();
        phaseRepository.updateLocalDB();
        cropRepository.updateLocalDB(currentUserId);
    }
}

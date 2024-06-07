package com.unimib.eden.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.unimib.eden.model.Fase;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.FaseRepository;
import com.unimib.eden.repository.ColturaRepository;
import com.unimib.eden.repository.PiantaRepository;

import java.util.List;

/**
 * ViewModel class for HomeFragment.
 * This class handles the data related to HomeFragment.
 */
public class HomeViewModel extends AndroidViewModel {

    private static final String TAG = "HomeViewModel";

    private final List<Pianta> mPlants;
    private final List<Fase> mPhases;
    private final LiveData<List<Coltura>> mCrops;
    private final PiantaRepository plantsRepository;
    private final ColturaRepository cropRepository;
    private final FaseRepository phaseRepository;

    /**
     * Constructor for HomeViewModel.
     *
     * @param application An instance of the application.
     */
    public HomeViewModel(Application application) {
        super(application);

        // Initialize repositories
        plantsRepository = new PiantaRepository(application);
        phaseRepository = new FaseRepository(application);
        cropRepository = new ColturaRepository(application);

        // Retrieve data from repositories
        mPlants = plantsRepository.getAllPiante();
        mPhases = phaseRepository.getAllFasi();
        mCrops = cropRepository.getAllColture();
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
    public LiveData<List<Coltura>> getCrops() {
        return mCrops;
    }

    /**
     * Get a plant by its ID.
     *
     * @param plantId The ID of the plant to retrieve.
     * @return The plant with the specified ID.
     */
    private Pianta getPlantById(String plantId) {
        return plantsRepository.getPiantaById(plantId);
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

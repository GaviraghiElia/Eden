package com.unimib.eden.ui.insertCrop;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.model.Phase;
import com.unimib.eden.repository.CropRepository;
import com.unimib.eden.repository.PhaseRepository;
import com.unimib.eden.repository.PlantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * ViewModel for inserting a new crop.
 * This class manages the data related to inserting a new crop.
 */
public class InsertCropViewModel extends AndroidViewModel {
    private static final String TAG = "InsertCropViewModel";
    private final CropRepository cropRepository;

    private final PhaseRepository phaseRepository;
    private final PlantRepository plantRepository;

    /**
     * Constructor for InsertCropViewModel.
     *
     * @param application An instance of the application.
     */
    public InsertCropViewModel(Application application) {
        super(application);
        cropRepository = new CropRepository(this.getApplication());
        plantRepository = new PlantRepository(this.getApplication());
        phaseRepository = new PhaseRepository(this.getApplication());
    }

    /**
     * Adds a new crop.
     *
     * @param cropMap A map containing the details of the crop to be added.
     */
    public void addCrop(Map<String, Object> cropMap){
        cropRepository.addCrops(cropMap);
    }

    /**
     * Gets the list of phases from a plant ID.
     *
     * @param plantId The ID of the plant.
     * @return A list of strings representing the phases of the plant.
     */
    public ArrayList<String> getPhasesFromId(String plantId) {
        return plantRepository.getPlantById(plantId).getPhases();
    }

    /**
     * This method returns a list of watering frequencies from a list of phase IDs.
     *
     * @param phasesIds The list of phase IDs from which to get the watering frequencies.
     * @return An ArrayList of integers representing the watering frequencies corresponding to the provided phase IDs.
     * @throws ExecutionException   if an error occurs during the operation execution.
     * @throws InterruptedException if the current thread is interrupted while waiting.
     */
    public ArrayList<Integer> getWateringFrequency(List<String> phasesIds) throws ExecutionException, InterruptedException {
        ArrayList<Phase> phases = getPhasesList(phasesIds);
        ArrayList<Integer> frequencies = new ArrayList<>();
        for (Phase fase : phases) {
            frequencies.add(fase.getWateringFrequency());
        }
        return frequencies;
    }

    /**
     * This method returns a list of Phase objects for a specified list of phase IDs.
     *
     * @param phasesIds The list of phase IDs to retrieve.
     * @return An ArrayList of Phase objects corresponding to the provided phase IDs.
     * @throws ExecutionException   if an error occurs during the operation execution.
     * @throws InterruptedException if the current thread is interrupted while waiting.
     */
    public ArrayList<Phase> getPhasesList(List<String> phasesIds) throws ExecutionException, InterruptedException {
        try {
            return phaseRepository.getPhasesFromIds(phasesIds);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

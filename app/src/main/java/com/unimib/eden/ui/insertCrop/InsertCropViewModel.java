package com.unimib.eden.ui.insertCrop;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.model.Fase;
import com.unimib.eden.repository.ColturaRepository;
import com.unimib.eden.repository.FaseRepository;
import com.unimib.eden.repository.PiantaRepository;

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
    private final ColturaRepository cropRepository;

    private final FaseRepository phaseRepository;
    private final PiantaRepository plantRepository;

    /**
     * Constructor for InsertCropViewModel.
     *
     * @param application An instance of the application.
     */
    public InsertCropViewModel(Application application) {
        super(application);
        cropRepository = new ColturaRepository(this.getApplication());
        plantRepository = new PiantaRepository(this.getApplication());
        phaseRepository = new FaseRepository(this.getApplication());
    }

    /**
     * Adds a new crop.
     *
     * @param cropMap A map containing the details of the crop to be added.
     */
    public void addCrop(Map<String, Object> cropMap){
        cropRepository.aggiungiColtura(cropMap);
    }

    /**
     * Gets the list of phases from a plant ID.
     *
     * @param plantId The ID of the plant.
     * @return A list of strings representing the phases of the plant.
     */
    public ArrayList<String> getPhasesFromId(String plantId) {
        return plantRepository.getPiantaById(plantId).getFasi();
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
        ArrayList<Fase> phases = getPhasesList(phasesIds);
        ArrayList<Integer> frequencies = new ArrayList<>();
        for (Fase fase : phases) {
            frequencies.add(fase.getFrequenzaInnaffiamento());
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
    public ArrayList<Fase> getPhasesList(List<String> phasesIds) throws ExecutionException, InterruptedException {
        try {
            return phaseRepository.getFasiID(phasesIds);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

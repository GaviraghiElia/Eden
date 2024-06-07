package com.unimib.eden.ui.cropDetails;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Fase;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.ColturaRepository;
import com.unimib.eden.repository.FaseRepository;
import com.unimib.eden.repository.PiantaRepository;
import com.unimib.eden.utils.Transformer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * ViewModel class for managing the details of a crop.
 * This class handles the data related to displaying the details of a crop.
 */
public class CropDetailsViewModel extends AndroidViewModel {

    private static final String TAG = "CropDetailsViewModel";
    private PiantaRepository plantRepository;
    private FaseRepository phaseRepository;
    private ColturaRepository cropRepository;

    /**
     * Constructor for ColturaDetailsViewModel.
     *
     * @param application An instance of the application.
     */
    public CropDetailsViewModel(@NonNull Application application) {
        super(application);

        // Initialize repositories
        plantRepository = new PiantaRepository(application);
        phaseRepository = new FaseRepository(application);
        cropRepository = new ColturaRepository(application);
    }

    /**
     * Gets the formatted date of the next watering for the crop.
     *
     * @param context The context.
     * @param crop The crop.
     * @return The formatted date of the next watering.
     */
    public String getNextWatering(Context context, Coltura crop) {
        return Transformer.formatNextWatering(context, crop);
    }

    /**
     * Gets the name of the plant associated with the crop.
     *
     * @param crop The crop.
     * @return The name of the plant.
     */
    public String getPlantName(Coltura crop) {
        return getPlantById(crop.getIdPianta()).getNome();
    }

    /**
     * Gets the plant associated with the crop.
     *
     * @param crop The crop.
     * @return The plant.
     */
    public Pianta getPlant(Coltura crop) {
        return getPlantById(crop.getIdPianta());
    }

    /**
     * Gets a plant by its ID.
     *
     * @param plantId The ID of the plant.
     * @return The plant with the specified ID.
     */
    private Pianta getPlantById(String plantId) {
        return plantRepository.getPiantaById(plantId);
    }

    /**
     * Gets the name of the current phase the crop is in.
     *
     * @param crop The crop.
     * @return The name of the phase the crop is in.
     * @throws ExecutionException   If the computation threw an exception.
     * @throws InterruptedException If the current thread was interrupted while waiting.
     */
    public String getPhaseName(Coltura crop) throws ExecutionException, InterruptedException {
        String phaseId = getPlantById(crop.getIdPianta()).getFasi().get(crop.getFaseAttuale());
        ArrayList<String> phasesIds = new ArrayList<>();
        phasesIds.add(phaseId);
        List<Fase> phases = phaseRepository.getFasiID(phasesIds);
        return phases.get(0).getNomeFase();
    }

    /**
     * Initializes the ViewModel with the provided crop.
     *
     * @param crop The crop.
     */
    public void initialize(Coltura crop) {
        // Additional initialization if needed
    }

    /**
     * Updates the watering date of the crop.
     *
     * @param crop The crop.
     */
    public void updateWateringDate(Coltura crop) {
        cropRepository.updateDataInnaffiamentoColtura(crop);
    }

    /**
     * Updates the watering date of the crop to the provided date.
     *
     * @param crop The crop.
     * @param newDate The new watering date.
     */
    public void updateWateringDateCrop(Coltura crop, Date newDate) {
        cropRepository.updateDataInnaffiamentoColtura(crop, newDate);
    }
}

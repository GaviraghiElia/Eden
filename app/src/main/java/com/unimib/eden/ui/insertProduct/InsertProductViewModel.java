package com.unimib.eden.ui.insertProduct;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.model.Phase;
import com.unimib.eden.repository.PhaseRepository;
import com.unimib.eden.repository.PlantRepository;
import com.unimib.eden.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * ViewModel for inserting a new product.
 * This class handles the data related to inserting a new product.
 */
public class InsertProductViewModel extends AndroidViewModel {
    private static final String TAG = "InsertProductViewModel";
    private ProductRepository productRepository;

    private PhaseRepository phaseRepository;
    private PlantRepository plantRepository;

    /**
     * Constructor for InsertProductViewModel.
     *
     * @param application An instance of the application.
     */
    public InsertProductViewModel(Application application) {
        super(application);
        productRepository = new ProductRepository(this.getApplication());
        plantRepository = new PlantRepository(this.getApplication());
        phaseRepository = new PhaseRepository(this.getApplication());
    }

    /**
     * Adds a new product.
     *
     * @param productMap A map containing the details of the product to be added.
     */
    public void addProduct(Map<String, Object> productMap){
        productRepository.addProduct(productMap);
    }

    /**
     * Gets the list of phases from a plant ID.
     *
     * @param plantId The ID of the plant.
     * @return A list of strings representing the phases of the plant.
     */
    public ArrayList<String> getPhasesById(String plantId) {
        return plantRepository.getPlantById(plantId).getPhases();
    }

    /**
     * This method returns a list of Fase objects for a list of specified phase IDs.
     *
     * @param phasesIds The list of phase IDs to retrieve.
     * @return An ArrayList of Fase objects corresponding to the provided phase IDs.
     * @throws ExecutionException   If an error occurs during the operation.
     * @throws InterruptedException If the current thread is interrupted while waiting.
     */
    public List<Phase> getPhasesList(List<String> phasesIds) throws ExecutionException, InterruptedException {
        try {
            return phaseRepository.getPhasesFromIds(phasesIds);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

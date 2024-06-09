package com.unimib.eden.ui.productDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.model.Plant;
import com.unimib.eden.model.Product;
import com.unimib.eden.repository.PhaseRepository;
import com.unimib.eden.repository.PlantRepository;

import java.util.concurrent.ExecutionException;

/**
 * ViewModel class for ProductDetailsViewModel.
 * This class handles the data related to the product details view.
 */
public class ProductDetailsViewModel extends AndroidViewModel {

    private static final String TAG = "ProductDetailsViewModel";
    private final PlantRepository plantRepository;
    private final PhaseRepository phaseRepository;

    /**
     * Constructor for ProductDetailsViewModel.
     *
     * @param application An instance of the application.
     */
    public ProductDetailsViewModel(@NonNull Application application) {
        super(application);

        // Initialize repositories
        plantRepository = new PlantRepository(application);
        phaseRepository = new PhaseRepository(application);
    }

    /**
     * Get the name of the plant associated with the product.
     *
     * @param product The product.
     * @return The name of the plant.
     */
    public String getPlantName(Product product) {
        return getPlantById(product.getPlant()).getName();
    }

    /**
     * Get the plant associated with the product.
     *
     * @param product The product.
     * @return The plant.
     */
    public Plant getPlant(Product product) {
        return getPlantById(product.getPlant());
    }

    /**
     * Get a plant by its ID.
     *
     * @param plantId The ID of the plant.
     * @return The plant with the specified ID.
     */
    private Plant getPlantById(String plantId) {
        return plantRepository.getPlantById(plantId);
    }

    /**
     * Get the name of the phase in which the product was put on sale.
     *
     * @param product The product.
     * @return The name of the phase in which the product was put on sale.
     */
    public String getPhaseName(Product product) throws ExecutionException, InterruptedException {
        return phaseRepository.getPhaseById(product.getCurrentPhase()).getPhaseName();
    }

    /**
     * Initialize the ViewModel with the provided product.
     *
     * @param product The product.
     */
    public void initialize(Product product) {
        // Additional initialization if needed
    }
}

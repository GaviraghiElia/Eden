package com.unimib.eden.ui.productDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.model.Pianta;
import com.unimib.eden.model.Prodotto;
import com.unimib.eden.repository.FaseRepository;
import com.unimib.eden.repository.PiantaRepository;

import java.util.concurrent.ExecutionException;

/**
 * ViewModel class for ProductDetailsViewModel.
 * This class handles the data related to the product details view.
 */
public class ProductDetailsViewModel extends AndroidViewModel {

    private static final String TAG = "ProductDetailsViewModel";
    private final PiantaRepository plantRepository;
    private final FaseRepository phaseRepository;

    /**
     * Constructor for ProductDetailsViewModel.
     *
     * @param application An instance of the application.
     */
    public ProductDetailsViewModel(@NonNull Application application) {
        super(application);

        // Initialize repositories
        plantRepository = new PiantaRepository(application);
        phaseRepository = new FaseRepository(application);
    }

    /**
     * Get the name of the plant associated with the product.
     *
     * @param product The product.
     * @return The name of the plant.
     */
    public String getPlantName(Prodotto product) {
        return getPlantById(product.getPianta()).getNome();
    }

    /**
     * Get the plant associated with the product.
     *
     * @param product The product.
     * @return The plant.
     */
    public Pianta getPlant(Prodotto product) {
        return getPlantById(product.getPianta());
    }

    /**
     * Get a plant by its ID.
     *
     * @param plantId The ID of the plant.
     * @return The plant with the specified ID.
     */
    private Pianta getPlantById(String plantId) {
        return plantRepository.getPiantaById(plantId);
    }

    /**
     * Get the name of the phase in which the product was put on sale.
     *
     * @param product The product.
     * @return The name of the phase in which the product was put on sale.
     */
    public String getPhaseName(Prodotto product) throws ExecutionException, InterruptedException {
        return phaseRepository.getFaseById(product.getFaseAttuale()).getNomeFase();
    }

    /**
     * Initialize the ViewModel with the provided product.
     *
     * @param product The product.
     */
    public void initialize(Prodotto product) {
        // Additional initialization if needed
    }
}

package com.unimib.eden.ui.stand;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.unimib.eden.model.Prodotto;
import com.unimib.eden.repository.ProdottoRepository;

import java.util.List;

/**
 * ViewModel for managing the stand.
 * This class is responsible for handling data related to the display of products in the stand.
 */
public class StandViewModel extends AndroidViewModel {
    private static final String TAG = "StandViewModel";
    private final LiveData<List<Prodotto>> mProducts;
    private final ProdottoRepository productRepository;

    /**
     * Constructor for StandViewModel.
     *
     * @param application An instance of the application.
     */
    public StandViewModel(Application application) {
        super(application);

        productRepository = new ProdottoRepository(application);
        mProducts = productRepository.getAllProdotti();
    }

    /**
     * Gets the list of products in the stand.
     *
     * @return The list of products in the stand.
     */
    public LiveData<List<Prodotto>> getProducts() {
        return mProducts;
    }

    /**
     * Updates the local database with the available products.
     *
     * @param currentUserId The ID of the current user.
     */
    public void updateDB(String currentUserId) {
        productRepository.updateLocalDB(currentUserId);
    }
}
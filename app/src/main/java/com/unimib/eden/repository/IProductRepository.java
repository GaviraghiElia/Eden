package com.unimib.eden.repository;
import androidx.lifecycle.LiveData;

import com.unimib.eden.model.Prodotto;
import java.util.List;

/**
 * Interface for ProductRepository.
 * Defines methods for accessing product data.
 */
public interface IProductRepository {

    /**
     * Retrieves all products.
     *
     * @return A list of all products.
     */
    LiveData<List<Prodotto>> getAllProducts();

    /**
     * Deletes a product.
     *
     * @param product The product to delete.
     */
    void deleteProduct(Prodotto product);

    /**
     * Inserts a new product.
     *
     * @param product The product to insert.
     */
    void insert(Prodotto product);
}

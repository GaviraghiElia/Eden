package com.unimib.eden.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.unimib.eden.model.Product;

import java.util.List;

/**
 * ProductDao interface for Product.
 * This interface defines the data access operations for the Product table in the Room database.
 */
@Dao
public interface ProductDao {

    /**
     * Gets all the products present in the database.
     *
     * @return A list of all the products present in the database.
     */
    @Query("SELECT * FROM 'prodotto'")
    LiveData<List<Product>> getAll();

    /**
     * Gets all the products present in the database for testing purposes.
     *
     * @return A list of all the products present in the database.
     */
    @Query("SELECT * FROM 'prodotto'")
    List<Product> getAllTest();

    /**
     * Deletes the specified products from the database.
     *
     * @param product The products to delete.
     */
    @Delete
    void delete(Product... product);

    /**
     * Inserts a new product into the database.
     *
     * @param product The product to insert.
     */
    @Insert
    void insert(Product product);
}

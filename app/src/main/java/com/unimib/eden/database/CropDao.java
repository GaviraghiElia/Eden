package com.unimib.eden.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.unimib.eden.model.Crop;
import com.unimib.eden.utils.Converters;

import java.util.List;

/**
 * DAO interface for Crop.
 * This interface defines data access operations for the Crop table in the Room database.
 */
@Dao
@TypeConverters(Converters.class)
public interface CropDao {

    /**
     * Retrieves all crops present in the database.
     *
     * @return A list of all crops present in the database.
     */
    @Query("SELECT * FROM 'coltura' ORDER BY nome, ultimo_innaffiamento ASC")
    LiveData<List<Crop>> getAll();

    @Query("SELECT * FROM 'coltura'")
    List<Crop> getAllTest();

    /**
     * Deletes the specified crops from the database.
     *
     * @param crop The crops to be deleted.
     */
    @Delete
    void delete(Crop... crop);

    /**
     * Inserts a new crop into the database.
     *
     * @param crop The crop to be inserted.
     */
    @Insert
    void insert(Crop crop);

    /**
     * Retrieves a crop from the database based on the specified ID.
     *
     * @param cropId The ID of the crop to be searched in the database.
     * @return The crop corresponding to the specified ID, if present in the database; otherwise, null.
     */
    @Query("SELECT * FROM 'coltura' WHERE id = :cropId")
    Crop getById(String cropId);

    @Query("SELECT * FROM 'coltura' WHERE id IN (:ids)")
    List<Crop> getByIds(List<String> ids);

    /**
     * Retrieves a list of crops to be watered on the current day.
     *
     * @param date The current date converted to a timestamp.
     * @return A list of crops to be watered on the current day.
     */
    @Query("SELECT * FROM 'coltura' WHERE 0 < :date - (ultimo_innaffiamento / (1000 * 60 * 60 * 24)) ORDER BY :date - (ultimo_innaffiamento / (1000 * 60 * 60 * 24)) - frequenza_innaffiamento_attuale DESC")
    LiveData<List<Crop>> getAllToWater(long date);

    @Query("SELECT * FROM 'coltura' WHERE 0 < :date - (ultimo_innaffiamento / (1000 * 60 * 60 * 24))")
    List<Crop> getAllToWaterTest(long date);
}

package com.unimib.eden.repository;

import androidx.lifecycle.LiveData;

import com.unimib.eden.model.Coltura;

import java.util.Date;
import java.util.List;

/**
 * Interface for CropRepository.
 * Defines methods for accessing crop data.
 */
public interface ICropRepository {

    /**
     * Retrieves all crops.
     *
     * @return A list of all crops.
     */
    LiveData<List<Coltura>> getAllCrops();

    /**
     * Deletes a crop.
     *
     * @param crop The crop to delete.
     */
    void deleteCrop(Coltura crop);

    /**
     * Inserts a new crop.
     *
     * @param crop The crop to insert.
     */
    void insert(Coltura crop);

    /**
     * Retrieves a crop from the repository based on the specified ID.
     *
     * @param cropId The ID of the crop to search for in the repository.
     * @return The crop corresponding to the specified ID, if present in the repository, otherwise null.
     */
    Coltura getCropById(String cropId);

    /**
     * Updates the last watering date to the current date for the specified crop.
     *
     * @param crop The crop to update the last watering date for to the current date.
     */
    void updateCropWateringDate(Coltura crop);

    /**
     * Updates the last watering date for the specified crop to the date passed as a parameter.
     *
     * @param crop  The crop to update the last watering date for.
     * @param newDate  The date to update the last watering value of the crop passed as a parameter.
     */
    void updateCropWateringDate(Coltura crop, Date newDate);

    /**
     * Retrieves all irrigation crops on the specified date.
     *
     * @param date The date to filter irrigation crops for.
     * @return A list of all irrigation crops on the specified date.
     */
    LiveData<List<Coltura>> getAllCropsToWater(long date);

}

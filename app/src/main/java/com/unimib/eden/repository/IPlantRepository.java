package com.unimib.eden.repository;

import com.unimib.eden.model.Pianta;

import java.util.List;

/**
 * IPlantRepository interface for PlantRepository.
 * Defines methods for accessing plant data.
 */
public interface IPlantRepository {

     /**
     * Retrieves all plants.
     *
     * @return A list of all plants.
     */
    List<Pianta> getAllPlants();

    /**
     * Deletes the input plant.
     *
     * @param plant  The plant to delete.
     */
    void deletePlant(Pianta plant);

    /**
     * Inserts the input plant.
     *
     * @param plant  The plant to insert.
     */
    void insert(Pianta plant);

    /**
     * Retrieves a plant by its id.
     *
     * @param plantId  The id of the plant.
     * @return The plant with the specified id.
     */
    Pianta getPlantById(String plantId);
}

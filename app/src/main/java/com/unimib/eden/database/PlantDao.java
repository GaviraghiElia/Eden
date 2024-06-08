package com.unimib.eden.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.unimib.eden.model.Pianta;

import java.util.List;

/**
 * Dao interface for Plant.
 * This interface defines the data access operations for the Plant table in the Room database.
 */
@Dao
public interface PlantDao {

    /**
     * Gets all the plants present in the database.
     *
     * @return A list of all the plants present in the database.
     */
    @Query("SELECT * FROM 'pianta'")
    List<Pianta> getAll();

    /**
     * Gets a list of all the plants whose name contains the input string as a substring.
     *
     * @param query The name of the plant to search for.
     * @return The list of plants whose name contains the input string as a substring.
     */
    @Query("SELECT * FROM 'pianta' WHERE nome LIKE '%' || :query || '%'")
    List<Pianta> searchPlants(String query);

    /**
     * Gets a list of all the plants present in the database whose name contains the input string as a substring and satisfies all the input parameters.
     *
     * @param query        The name of the plant to search for.
     * @param sunExposure  The sun exposure that the plants must have.
     * @param sowingStart  The starting sowing month that the plants must have.
     * @param sowingEnd    The ending sowing month that the plants must have.
     * @return The list of all the plants whose name contains the input string as a substring and satisfies all the input parameters.
     */
    @Query("SELECT * FROM 'pianta' WHERE nome LIKE '%' || :query || '%' AND esposizione_sole = :sunExposure AND inizio_semina >= :sowingStart AND fine_semina <= :sowingEnd")
    List<Pianta> searchPlantsAllFilters(String query, String sunExposure, int sowingStart, int sowingEnd);

    /**
     * Gets a list of all the plants present in the database whose name contains the input string as a substring and satisfies all the input parameters.
     *
     * @param query        The name of the plant to search for.
     * @param sowingStart  The starting sowing month that the plants must have.
     * @param sowingEnd    The ending sowing month that the plants must have.
     * @return The list of all the plants whose name contains the input string as a substring and satisfies all the input parameters.
     */
    @Query("SELECT * FROM 'pianta' WHERE nome LIKE '%' || :query || '%' AND inizio_semina >= :sowingStart AND fine_semina <= :sowingEnd")
    List<Pianta> searchPlantsFilters(String query, int sowingStart, int sowingEnd);

    /**
     * Gets the plant present in the database with the given id.
     *
     * @param plantId The id of the plant to search for in the database.
     * @return The plant corresponding to the specified id if present in the database, otherwise null.
     */
    @Query("SELECT * FROM 'pianta' WHERE id = :plantId")
    Pianta getById(String plantId);

    /**
     * Deletes the input plant from the database.
     *
     * @param plant The plant to delete from the database.
     */
    @Delete
    void delete(Pianta... plant);

    /**
     * Inserts the input plant into the database.
     *
     * @param plant The plant to insert into the database.
     */
    @Insert
    void insert(Pianta plant);
}

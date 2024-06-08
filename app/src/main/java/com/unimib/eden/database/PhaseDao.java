package com.unimib.eden.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.unimib.eden.model.Fase;

import java.util.List;

/**
 * Dao interface for Phase.
 * This interface defines data access operations for the Phase table in the Room database.
 */
@Dao
public interface PhaseDao {

    /**
     * Gets all the phases present in the database.
     *
     * @return A list of all the phases present in the database.
     */
    @Query("SELECT * FROM 'fase'")
    List<Fase> getAll();

    /**
     * Gets all the phases present in the database with IDs matching the ones passed as input.
     *
     * @param ids The IDs of the phases that should match the IDs of the phases returned in the output.
     * @return A list of all the phases present in the database with IDs matching the ones passed as input.
     */
    @Query("SELECT * FROM 'fase' WHERE id IN (:ids)")
    List<Fase> getPhasesIds(List<String> ids);

    /**
     * Deletes the input phase from the database.
     *
     * @param phase The phase to delete from the database.
     */
    @Delete
    void delete(Fase... phase);

    /**
     * Inserts the input phase into the database.
     *
     * @param phase The phase to insert into the database.
     */
    @Insert
    void insert(Fase phase);

    /**
     * Gets a phase from the database based on the specified ID.
     *
     * @param phaseId The ID of the phase to search for in the database.
     * @return The phase corresponding to the specified ID if present in the database, otherwise null.
     */
    @Query("SELECT * FROM 'fase' WHERE id = :phaseId")
    Fase getById(String phaseId);
}

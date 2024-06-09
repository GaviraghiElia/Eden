package com.unimib.eden.repository;

import com.unimib.eden.model.Phase;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Interface IPhaseRepository for PhaseRepository.
 * Defines methods for accessing phase data.
 */
public interface IPhaseRepository {

    /**
     * Retrieves all phases.
     *
     * @return  A list of all phases.
     */
    List<Phase> getAllPhases();

    /**
     * Retrieves a phase from the repository based on the specified ID.
     *
     * @param phaseId The ID of the phase to search for in the repository.
     * @return The phase corresponding to the specified ID, if present in the repository, otherwise null.
     */
    Phase getPhaseById(String phaseId);

    /**
     * Retrieves all phases with IDs equal to those passed as input.
     *
     * @param ids The IDs of the phases that should match those of the phases returned in the output.
     * @return  A list of all phases with IDs that match those passed as input.
     */
    List<Phase> getPhasesFromIds(List<String> ids) throws ExecutionException, InterruptedException;

    /**
     * Deletes the input phase from the database.
     *
     * @param phase  The phase to delete from the database.
     */
    void deletePhase(Phase phase);

    /**
     * Inserts the input phase into the database.
     *
     * @param phase  The phase to insert into the database.
     */
    void insert(Phase phase);
}

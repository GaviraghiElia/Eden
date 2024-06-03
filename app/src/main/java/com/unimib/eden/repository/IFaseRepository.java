package com.unimib.eden.repository;

import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Fase;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Interfaccia IFaseRepository per FaseRepository.
 * Definisce i metodi per l'accesso ai dati della fase
 *
 * @author Alice Hoa Galli
 */
public interface IFaseRepository {

    /**
     * Ottiene tutte le fasi.
     *
     * @return  Una lista di tutte le fasi.
     */
    List<Fase> getAllFasi();

    /**
     * Ottiene una fase dal repository in base all'ID specificato.
     *
     * @param faseId L'ID della fase da cercare nel repository.
     * @return La fase corrispondente all'ID specificato, se presente nel repository, altrimenti null.
     */
    Fase getFaseById(String faseId);

    /**
     * Ottiene tutte le fasi che hanno gli ID uguali a quelli passati in input.
     *
     * @param ids Gli Id delle fasi che dovranno coincidere con quelli delle fasi restituite in output.
     * @return  Una lista di tutte le fasi che hanno gli ID che coincidono con quelli passati in input.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    List<Fase> getFasiID(List<String> ids) throws ExecutionException, InterruptedException;

    /**
     * Elimina la fase in input dal database.
     *
     * @param fase  La fase da eliminare all'interno del database.
     */
    void deleteFase(Fase fase);

    /**
     * Inserisce la fase in input nel database.
     *
     * @param fase  La fase da inserire all'interno del database.
     */
    void insert(Fase fase);
}

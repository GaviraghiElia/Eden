package com.unimib.eden.repository;

import com.unimib.eden.model.Pianta;

import java.util.List;

/**
 * Interfaccia IPiantaRepository per PiantaRepository.
 * Definisce i metodi per l'accesso ai dati della pianta
 *
 * @author Alice Hoa Galli
 */
public interface IPiantaRepository {

    /**
     * Ottiene tutte le piante.
     *
     * @return Una lista di tutte le piante.
     */
    List<Pianta> getAllPiante();

    /**
     * Elimina la pianta in input.
     *
     * @param pianta  La pianta da eliminare.
     */
    void deletePianta(Pianta pianta);

    /**
     * Inserisce la pianta in input.
     *
     * @param pianta  La pianta da inserire.
     */
    void insert(Pianta pianta);
}

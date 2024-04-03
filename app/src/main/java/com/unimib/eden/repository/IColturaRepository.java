package com.unimib.eden.repository;

import com.unimib.eden.model.Coltura;

import java.util.List;

/**
 * Interfaccia per ColturaRepository.
 * Definisce metodi per l'accesso ai dati della coltura.
 */
public interface IColturaRepository {

    /**
     * Ottiene tutte le colture.
     *
     * @return Una lista di tutte le colture.
     */
    List<Coltura> getAllColture();

    /**
     * Elimina una coltura.
     *
     * @param coltura La coltura da eliminare.
     */
    void deleteColtura(Coltura coltura);

    /**
     * Inserisce una nuova coltura.
     *
     * @param coltura La coltura da inserire.
     */
    void insert(Coltura coltura);
}

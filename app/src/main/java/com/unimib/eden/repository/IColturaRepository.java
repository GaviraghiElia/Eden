package com.unimib.eden.repository;

import androidx.lifecycle.LiveData;

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
    LiveData<List<Coltura>> getAllColture();

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

    /**
     * Ottiene una coltura dal repository in base all'ID specificato.
     *
     * @param colturaId L'ID della coltura da cercare nel repository.
     * @return La coltura corrispondente all'ID specificato, se presente nel repository, altrimenti null.
     */
    Coltura getColturaById(String colturaId);

}

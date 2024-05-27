package com.unimib.eden.repository;

import androidx.lifecycle.LiveData;

import com.unimib.eden.model.Coltura;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    /**
     * Aggiorna la data dell'ultimo innaffiamento a quella corrente per la coltura passata come parametro
     * @param coltura La coltura a cui bisogna aggiornare la data di ultimo innaffiamento  alla data corrente
     */
    void updateDataInnaffiamentoColtura(Coltura coltura);

    /**
     * Aggiorna la data dell'ultimo innaffiamento per la coltura passata come parametro alla data passata come parametro
     * @param coltura La coltura a cui bisogna aggiornare la data di ultimo innaffiamento alla data indicata
     * @param newDate  La data a cui bisogna aggiornare il valore di ultimo innaffiamento della coltura passata come parametro
     */
    void updateDataInnaffiamentoColtura(Coltura coltura, Date newDate);

    /**
     * Ottiene tutte le colture da irrigazione nella data indicata.
     *
     * @param date La data per cui filtrare le colture da irrigare
     * @return Una lista di tutte le colture da irrigare nella data indicata.
     */
    LiveData<List<Coltura>> getAllColtureDaInnaffiare(long date);

}

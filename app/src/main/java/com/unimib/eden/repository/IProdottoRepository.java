package com.unimib.eden.repository;
import androidx.lifecycle.LiveData;

import com.unimib.eden.model.Prodotto;
import java.util.List;

/**
 * Interfaccia per ProdottoRepository.
 * Definisce metodi per l'accesso ai dati del prodotto.
 */
public interface IProdottoRepository {

    /**
     * Ottiene tutti i prodotti.
     *
     * @return Una lista di tutti i prodotti.
     */
    LiveData<List<Prodotto>> getAllProdotti();

    /**
     * Elimina un prodotto.
     *
     * @param prodotto Il prodotto da eliminare.
     */
    void deleteProdotto(Prodotto prodotto);

    /**
     * Inserisce un nuovo prodotto.
     *
     * @param prodotto Il prodotto da inserire.
     */
    void insert(Prodotto prodotto);
}

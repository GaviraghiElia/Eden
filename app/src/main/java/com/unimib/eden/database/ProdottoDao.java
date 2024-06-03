package com.unimib.eden.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.unimib.eden.model.Pianta;
import com.unimib.eden.model.Prodotto;

import java.util.List;

/**
 * Interfaccia DAO per Prodotto.
 * Questa interfaccia definisce le operazioni di accesso ai dati per la tabella Prodotto nel database Room.
 */
@Dao
public interface ProdottoDao {

    /**
     * Ottiene tutti i prodotti presenti nel database.
     *
     * @return Una lista di tutti i prodotti presenti nel database.
     */
    @Query("SELECT * FROM 'prodotto'")
    List<Prodotto> getAll();

    /**
     * Elimina i prodotti specificati dal database.
     *
     * @param prodotto I prodotti da eliminare.
     */
    @Delete
    void delete(Prodotto... prodotto);

    /**
     * Inserisce un nuovo prodotto nel database.
     *
     * @param prodotto Il prodotto da inserire.
     */
    @Insert
    void insert(Prodotto prodotto);
}

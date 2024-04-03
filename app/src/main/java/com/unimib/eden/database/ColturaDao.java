package com.unimib.eden.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.unimib.eden.model.Coltura;

import java.util.List;

/**
 * Interfaccia DAO per Coltura.
 * Questa interfaccia definisce le operazioni di accesso ai dati per la tabella Coltura nel database Room.
 */
@Dao
public interface ColturaDao {

    /**
     * Ottiene tutte le colture presenti nel database.
     *
     * @return Una lista di tutte le colture presenti nel database.
     */
    @Query("SELECT * FROM 'coltura'")
    List<Coltura> getAll();

    /**
     * Elimina le colture specificate dal database.
     *
     * @param coltura Le colture da eliminare.
     */
    @Delete
    void delete(Coltura... coltura);

    /**
     * Inserisce una nuova coltura nel database.
     *
     * @param coltura La coltura da inserire.
     */
    @Insert
    void insert(Coltura coltura);
}

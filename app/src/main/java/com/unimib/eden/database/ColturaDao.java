package com.unimib.eden.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.unimib.eden.model.Coltura;
import com.unimib.eden.utils.Converters;

import java.util.Date;
import java.util.List;

/**
 * Interfaccia DAO per Coltura.
 * Questa interfaccia definisce le operazioni di accesso ai dati per la tabella Coltura nel database Room.
 */
@Dao
@TypeConverters(Converters.class)
public interface ColturaDao {

    /**
     * Ottiene tutte le colture presenti nel database.
     *
     * @return Una lista di tutte le colture presenti nel database.
     */
    @Query("SELECT * FROM 'coltura'")
    LiveData<List<Coltura>> getAll();

    @Query("SELECT * FROM 'coltura'")
    List<Coltura> getAllTest();

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

    /**
     * Ottiene una coltura dal database in base all'ID specificato.
     *
     * @param colturaId L'ID della coltura da cercare nel database.
     * @return La coltura corrispondente all'ID specificato, se presente nel database, altrimenti null.
     */
    @Query("SELECT * FROM 'coltura' WHERE id = :colturaId")
    Coltura getById(String colturaId);

    @Query("SELECT * FROM 'coltura' WHERE id IN (:ids)")
    List<Coltura> getByIds(List<String> ids);

    /**
     * Ottiene una lista delle colture da irrigare il giorno corrente
     * @param date La data corrente convertita in timestamp
     * @return La lista con le colture da irrigare il giorno corrente
     */

    @Query("SELECT * FROM 'coltura' WHERE 0 < :date - (ultimo_innaffiamento / (1000 * 60 * 60 * 24)) ORDER BY :date - (ultimo_innaffiamento / (1000 * 60 * 60 * 24)) - frequenza_innaffiamento_attuale DESC")
    LiveData<List<Coltura>> getAllDaIrrigare(long date);

    @Query("SELECT * FROM 'coltura' WHERE 0 < :date - (ultimo_innaffiamento / (1000 * 60 * 60 * 24))")
    List<Coltura> getAllDaIrrigareTest(long date);
}

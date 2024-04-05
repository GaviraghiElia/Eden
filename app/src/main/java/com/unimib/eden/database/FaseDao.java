package com.unimib.eden.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.unimib.eden.model.Fase;
import com.unimib.eden.model.Pianta;

import java.util.List;

/**
 * Interfaccia Dao per Fase.
 * Questa interfaccia definisce le operazioni di accesso ai dati per la tabella Fase nel database Room.
 *
 * @author Alice Hoa Galli
 */
@Dao
public interface FaseDao {

    /**
     * Ottiene tutte le fasi presenti nel database.
     *
     * @return Una lista di tutte le fasi presenti nel database.
     */
    @Query("SELECT * FROM 'fase'")
    List<Fase> getAll();

    /**
     * Ottiene tutte le fasi presenti nel database che hanno gli ID uguali a quelli passati in input.
     *
     * @param ids   Gli Id delle fasi che dovranno coincidere con quelli delle fasi restituite in output.
     * @return Una lista di tutte le fasi presenti nel database e che hanno gli ID che coincidono con quelli passati in input.
     */
    @Query("SELECT * FROM 'fase' WHERE id IN (:ids)")
    List<Fase> getFasiID(List<String> ids);

    /**
     * Elimina la fase in input dal database.
     *
     * @param fase  La fase da eliminare all'interno del database.
     */
    @Delete
    void delete(Fase... fase);

    /**
     * Inserisce la fase in input nel database.
     *
     * @param fase  La fase da inserire all'interno del database.
     */
    @Insert
    void insert(Fase fase);
}

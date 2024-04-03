package com.unimib.eden.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.unimib.eden.model.Pianta;

import java.util.List;

@Dao
public interface PiantaDao {
    @Query("SELECT * FROM 'pianta'")
    List<Pianta> getAll();

    @Query("SELECT * FROM 'pianta' WHERE nome LIKE '%' || :query || '%'")
    List<Pianta> searchPiante(String query);

    @Query("SELECT * FROM 'pianta' WHERE nome LIKE '%' || :query || '%' AND frequenza_innaffiamento >= :frequenzaInnaffiamento AND esposizione_sole = :esposizioneSole AND inizio_semina >= :inizioSemina AND fine_semina <= :fineSemina")
    List<Pianta> searchPianteFiltriAll(String query, int frequenzaInnaffiamento, String esposizioneSole, int inizioSemina, int fineSemina);

    @Query("SELECT * FROM 'pianta' WHERE nome LIKE '%' || :query || '%'AND frequenza_innaffiamento >= :frequenzaInnaffiamento AND inizio_semina >= :inizioSemina AND fine_semina <= :fineSemina")
    List<Pianta> searchPianteFiltri(String query, int frequenzaInnaffiamento, int inizioSemina, int fineSemina);

    @Delete
    void delete(Pianta... pianta);

    @Insert
    void insert(Pianta pianta);
}

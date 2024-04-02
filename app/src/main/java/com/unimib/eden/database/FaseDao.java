package com.unimib.eden.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.unimib.eden.model.Fase;
import com.unimib.eden.model.Pianta;

import java.util.List;

@Dao
public interface FaseDao {
    @Query("SELECT * FROM 'fase'")
    List<Fase> getAll();

    @Query("SELECT * FROM 'fase' WHERE id IN (:ids)")
    List<Fase> getFasiID(List<String> ids);

    @Delete
    void delete(Fase... fase);

    @Insert
    void insert(Fase fase);
}

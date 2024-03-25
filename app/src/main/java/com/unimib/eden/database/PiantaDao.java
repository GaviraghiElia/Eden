package com.unimib.eden.database;

import androidx.room.Dao;
import androidx.room.Query;

import com.unimib.eden.model.Pianta;

import java.util.List;

@Dao
public interface PiantaDao {
    @Query("SELECT * FROM 'pianta'")
    List<Pianta> getAll();
}

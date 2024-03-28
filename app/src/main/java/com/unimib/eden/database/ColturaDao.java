package com.unimib.eden.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.unimib.eden.model.Coltura;
import com.unimib.eden.utils.Constants;

import java.util.List;

@Dao
public interface ColturaDao {
    @Query("SELECT * FROM 'coltura'")
    List<Coltura> getAll();

    @Delete
    void delete(Coltura... coltura);

    @Insert
    void insert(Coltura coltura);
}

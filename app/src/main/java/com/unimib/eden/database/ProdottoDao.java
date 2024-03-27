package com.unimib.eden.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.unimib.eden.model.Pianta;
import com.unimib.eden.model.Prodotto;

import java.util.List;

@Dao
public interface ProdottoDao {

    @Query("SELECT * FROM 'prodotto'")
    List<Prodotto> getAll();

    @Delete
    void delete(Prodotto... prodotto);

    @Insert
    void insert(Prodotto prodotto);
}

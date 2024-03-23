package com.unimib.eden.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.unimib.eden.model.Prodotto;

import java.util.List;

@Dao
public interface ProdottoDao {

    @Query("SELECT * FROM prodotti")
    LiveData<List<Prodotto>> getProdotti(); //DA RIEMPIRE
//contiene tutte le query
}

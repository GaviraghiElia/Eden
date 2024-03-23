package com.unimib.eden.repository;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.unimib.eden.database.ProdottoDao;
import com.unimib.eden.model.Prodotto;
import java.util.List;

//collegato al ProdottoDao e ProdottoRoomDatabase
public class ProdottoRepository implements IProdottoRepository{
    private static final String TAG = "ProdottoRepository";
    private ProdottoDao prodottoDao;
    private static final String COLLECTION_NAME = "prodotti";
    String appID = "eden-hbbet";
    String email = "s.erba9@campus.unimib.it";
    String password = "Prova123";

    public ProdottoRepository(Application application) {
    }

    // Metodo per ottenere un prodotto dal database per ID
    public Prodotto getProdottoById(String id) {
        return null;
    }


    public LiveData<List<Prodotto>> getProdotti() {
        Log.d(TAG,"hai fatto getProdotti");
        return prodottoDao.getProdotti();
    }

    // Altri metodi per inserire, aggiornare, eliminare prodotti, ecc.
}

package com.unimib.eden.ui.bancarella;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.model.Prodotto;
import com.unimib.eden.repository.ProdottoRepository;

import java.util.List;

/**
 * ViewModel per la gestione della bancarella.
 * Questa classe si occupa di gestire i dati relativi alla visualizzazione dei prodotti nella bancarella.
 */
public class BancarellaViewModel extends AndroidViewModel {
    private static final String TAG = "BancarellaViewModel";
    private List<Prodotto> mProdotti;
    private ProdottoRepository prodottoRepository;

    /**
     * Costruttore per BancarellaViewModel.
     *
     * @param application Un'istanza dell'applicazione.
     */
    public BancarellaViewModel(Application application) {
        super(application);

        prodottoRepository = new ProdottoRepository(application);
        mProdotti = prodottoRepository.getAllProdotti();
    }

    /**
     * Ottiene la lista dei prodotti nella bancarella.
     *
     * @return La lista dei prodotti nella bancarella.
     */
    public List<Prodotto> getProdotti() {
        Log.d(TAG, "lunghezza mProdotti: " + mProdotti.size());
        return mProdotti;
    }

    /**
     * Aggiorna il database locale con i prodotti disponibili.
     */
    public void updateDB() {
        prodottoRepository.updateLocalDB();
    }
}

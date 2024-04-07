package com.unimib.eden.ui.inserimentoProdotto;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.repository.PiantaRepository;
import com.unimib.eden.repository.ProdottoRepository;

import java.util.ArrayList;
import java.util.Map;

/**
 * ViewModel per l'inserimento di un nuovo prodotto.
 * Questa classe si occupa di gestire i dati relativi all'inserimento di un nuovo prodotto.
 */
public class InserimentoProdottoViewModel extends AndroidViewModel {
    private static final String TAG = "InserimentoProdotto";
    private ProdottoRepository prodottoRepository;
    private PiantaRepository piantaRepository;

    /**
     * Costruttore per InserimentoProdottoViewModel.
     *
     * @param application Un'istanza dell'applicazione.
     */
    public InserimentoProdottoViewModel(Application application) {
        super(application);
        prodottoRepository = new ProdottoRepository(this.getApplication());
        piantaRepository = new PiantaRepository(this.getApplication());
    }

    /**
     * Aggiunge un nuovo prodotto.
     *
     * @param prodottoMap Mappa contenente i dettagli del prodotto da aggiungere.
     */
    public void aggiungiProdotto(Map<String, Object> prodottoMap){
        prodottoRepository.aggiungiProdotto(prodottoMap);
    }

    /**
     * Ottiene la lista delle fasi da un ID di pianta.
     *
     * @param piantaId L'ID della pianta.
     * @return Una lista di stringhe rappresentanti le fasi della pianta.
     */
    public ArrayList<String> getFasiDaId(String piantaId) {
        return piantaRepository.getPiantaById(piantaId).getFasi();
    }
}
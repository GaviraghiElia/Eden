package com.unimib.eden.ui.inserimentoProdotto;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.repository.ProdottoRepository;

import java.util.Map;

public class InserimentoProdottoViewModel extends AndroidViewModel {
    private static final String TAG = "InserimentoProdotto";
    private ProdottoRepository prodottoRepository;

    public InserimentoProdottoViewModel(Application application) {
        super(application);
        prodottoRepository = new ProdottoRepository(this.getApplication());
    }

    public void aggiungiProdotto(Map<String, Object> prodottoMap){
        prodottoRepository.aggiungiProdotto(prodottoMap);
    }
}
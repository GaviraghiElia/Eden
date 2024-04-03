package com.unimib.eden.ui.inserimentoProdotto;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.PiantaRepository;
import com.unimib.eden.repository.ProdottoRepository;

import java.util.ArrayList;
import java.util.Map;

public class InserimentoProdottoViewModel extends AndroidViewModel {
    private static final String TAG = "InserimentoProdotto";
    private ProdottoRepository prodottoRepository;
    private PiantaRepository piantaRepository;

    public InserimentoProdottoViewModel(Application application) {
        super(application);
        prodottoRepository = new ProdottoRepository(this.getApplication());
        piantaRepository = new PiantaRepository(this.getApplication());
    }

    public void aggiungiProdotto(Map<String, Object> prodottoMap){
        prodottoRepository.aggiungiProdotto(prodottoMap);
    }

    public ArrayList<String> getFasiDaId(String pomodoroId) {
        Pianta pomodoro = piantaRepository.getPiantaById(pomodoroId);
        Log.d(TAG, "il pomodoro è" + pomodoro.toString());
        return pomodoro.getFasi();
    }
}
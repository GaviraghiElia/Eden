package com.unimib.eden.ui.inserimentoColtura;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.model.Fase;
import com.unimib.eden.repository.ColturaRepository;
import com.unimib.eden.repository.FaseRepository;
import com.unimib.eden.repository.PiantaRepository;
import com.unimib.eden.repository.ProdottoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * ViewModel per l'inserimento di un nuovo prodotto.
 * Questa classe si occupa di gestire i dati relativi all'inserimento di un nuovo prodotto.
 */
public class InserimentoColturaViewModel extends AndroidViewModel {
    private static final String TAG = "InserimentoColtura";
    private ColturaRepository colturaRepository;

    private FaseRepository faseRepository;
    private PiantaRepository piantaRepository;

    /**
     * Costruttore per InserimentoColturaViewModel.
     *
     * @param application Un'istanza dell'applicazione.
     */
    public InserimentoColturaViewModel(Application application) {
        super(application);
        colturaRepository = new ColturaRepository(this.getApplication());
        piantaRepository = new PiantaRepository(this.getApplication());
        faseRepository = new FaseRepository(this.getApplication());
    }

    /**
     * Aggiunge una nuova coltura.
     *
     * @param colturaMap Mappa contenente i dettagli della coltura da aggiungere.
     */
    public void aggiungiColtura(Map<String, Object> colturaMap){
        colturaRepository.aggiungiColtura(colturaMap);
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

    public ArrayList<Integer> getFrequenzeInnaffiamento(List<String> fasiID) throws ExecutionException, InterruptedException {
        Log.d(TAG, "PRIMA del for:" + fasiID.toString()); //qui è ordinata
        ArrayList<Fase> fasi = getFasiList(fasiID);
        Log.d(TAG, "1:" + fasi.get(0).getId()); // qui NON è ordinata
        ArrayList<Integer> frequenze = new ArrayList<>();
        for (Fase fase : fasi) {
            frequenze.add(fase.getFrequenzaInnaffiamento());
            Log.d(TAG, "fase con ID:" + fase.getId() + "valore:" + fase.getFrequenzaInnaffiamento());
        }
        return frequenze;
    }

    //TODO: questo metodo le mescola e mette in ordine alfabetico
    public ArrayList<Fase> getFasiList(List<String> fasiID) throws ExecutionException, InterruptedException {
        try {
            return faseRepository.getFasiID(fasiID);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
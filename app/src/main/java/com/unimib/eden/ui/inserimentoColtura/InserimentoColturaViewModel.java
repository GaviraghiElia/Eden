package com.unimib.eden.ui.inserimentoColtura;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.model.Fase;
import com.unimib.eden.repository.ColturaRepository;
import com.unimib.eden.repository.FaseRepository;
import com.unimib.eden.repository.PiantaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * ViewModel per l'inserimento di un nuova coltura.
 * Questa classe si occupa di gestire i dati relativi all'inserimento di una nuova coltura.
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

    /**
     * Questo metodo restituisce una lista di frequenze di innaffiamento da una lista di ID di fasi.
     *
     * @param fasiID la lista degli ID delle fasi di cui si vogliono ottenere le frequenze di innaffiamento.
     * @return un'ArrayList di interi rappresentanti le frequenze di innaffiamento corrispondenti agli ID delle fasi fornite.
     * @throws ExecutionException se si verifica un errore durante l'esecuzione dell'operazione.
     * @throws InterruptedException se il thread corrente viene interrotto mentre attende.
     */
    public ArrayList<Integer> getFrequenzeInnaffiamento(List<String> fasiID) throws ExecutionException, InterruptedException {
        ArrayList<Fase> fasi = getFasiList(fasiID);
        ArrayList<Integer> frequenze = new ArrayList<>();
        for (Fase fase : fasi) {
            frequenze.add(fase.getFrequenzaInnaffiamento());
        }
        return frequenze;
    }

    /**
     * Questo metodo restituisce una lista di oggetti Fase per una lista di ID di fasi specificati.
     *
     * @param fasiID la lista degli ID delle fasi da recuperare.
     * @return un'ArrayList di oggetti Fase corrispondenti agli ID delle fasi forniti.
     * @throws ExecutionException se si verifica un errore durante l'esecuzione dell'operazione.
     * @throws InterruptedException se il thread corrente viene interrotto mentre attende.
     */
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
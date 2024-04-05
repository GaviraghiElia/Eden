package com.unimib.eden.ui.searchPianta;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.PiantaRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Classe SearchPiantaViewModel che rappresenta i ViewModel dell'activity SearchPiantaActivity.
 *
 * @author Alice Hoa Galli
 */
public class SearchPiantaViewModel extends AndroidViewModel {
    private static final String TAG = "SearchPiantaViewModel";
    private PiantaRepository piantaRepository;
    private MutableLiveData<List<Pianta>> piantaListLiveData = new MutableLiveData<>();

    /**
     * Costruttore che genera un'istanza del SearchPiantaViewModel con all'interno un'istanza del PiantaRepository e una lista di piante vuota.
     *
     * @param application   Il contesto dell'applicazione
     */
    public SearchPiantaViewModel(@NonNull Application application) {
        super(application);
        piantaRepository = new PiantaRepository(application);
        piantaListLiveData = new MutableLiveData<>();;

    }

    /**
     * Metodo getPiantaList che restituisce una lista di piante presenti nel database.
     *
     * @return  Una lista di piante presenti nel database.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public LiveData<List<Pianta>> getPiantaList() {
        if (piantaListLiveData.getValue() == null) {
            searchPianta("");
        }
        return piantaListLiveData;
    }

    /**
     * Metodo getPiantaList che restituisce una lista di piante che rispettano i filtri di ricerca impostati.
     *
     * @param filtriMap   Una mappa contenente i filtri di ricerca impostati.
     * @return  Una lista di piante che  che rispettano i filtri di ricerca impostati.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public LiveData<List<Pianta>> getPiantaList(Map<String, String> filtriMap) {
        if (piantaListLiveData.getValue() == null) {
            searchPianta("", filtriMap);
        }
        return piantaListLiveData;
    }

    /**
     * Metodo searchPianta che aggiorna la lista delle piante presente nella classe SearchPiantaViewModel.
     *
     * @param query Nome della pianta che deve comparire come sottostringa all'interno dei nomi delle piante individuate.
     */
    public void searchPianta(String query) {
        try {
            piantaListLiveData.postValue(piantaRepository.SearchPiante(query));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo searchPianta che aggiorna la lista delle piante presente nella classe SearchPiantaViewModel.
     *
     * @param query Nome della pianta che deve comparire come sottostringa all'interno dei nomi delle piante individuate.
     * @param filtriMap Mappa dei filtri di ricerca che devono essere applicati alla ricerca delle piante.
     */
    public void searchPianta(String query, Map<String, String> filtriMap) {
        try {
            piantaListLiveData.postValue(piantaRepository.SearchPiante(query,filtriMap));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

package com.unimib.eden.ui.piantaDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.model.Fase;
import com.unimib.eden.repository.FaseRepository;
import com.unimib.eden.repository.PiantaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Classe PiantaDetailsViewModel che rappresneta i ViewModel dell'activity PiantaDetailsActivity.
 *
 * @author Alice Hoa Galli
 */
public class PiantaDetailsViewModel extends AndroidViewModel {
    private static final String TAG = "PiantaDetailsViewModel";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private PiantaRepository piantaRepository;
    private FaseRepository faseRepository;

    private List<Fase> fasiList;

    /**
     * Costruttore che genera un'istanza del PiantaDetailsViewModel con all'interno un'istanza del PiantaRepository, del FaseRepository e una lista di fasi vuota.
     *
     * @param application   Il contesto dell'applicazione
     */
    public PiantaDetailsViewModel(@NonNull Application application) {
        super(application);
        piantaRepository = new PiantaRepository(application);
        faseRepository = new FaseRepository(application);

        fasiList =  new ArrayList<>();

    }

    /**
     * Metodo getFasiList che restituisce una lista di fasi che hanno gli Id che coincidono a quelli passati in input.
     *
      * @param fasiID   La lista di Id che le fasi restituite in output devono rispettare.
     * @return  Una lista di fasi che hanno gli Id che coincidono a quelli passati in input.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Fase> getFasiList(List<String> fasiID) throws ExecutionException, InterruptedException {
        try {
            return faseRepository.getFasiID(fasiID);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }






}

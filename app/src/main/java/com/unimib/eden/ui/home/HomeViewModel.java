package com.unimib.eden.ui.home;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.model.Fase;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.FaseRepository;
import com.unimib.eden.repository.ColturaRepository;
import com.unimib.eden.repository.PiantaRepository;

import java.util.List;

/**
 * Classe ViewModel per HomeFragment.
 * Questa classe si occupa di gestire i dati correlati a HomeFragment.
 */
public class HomeViewModel extends AndroidViewModel {

    private static final String TAG = "HomeViewModel";

    private List<Pianta> mPiante;

    private List<Fase> mFasi;

    private List<Coltura> mColture;
    private PiantaRepository piantaRepository;
    private ColturaRepository colturaRepository;

    private FaseRepository faseRepository;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Costruttore per HomeViewModel.
     *
     * @param application Un'istanza dell'applicazione.
     */
    public HomeViewModel(Application application) {
        super(application);

        // Inizializza i repository
        piantaRepository = new PiantaRepository(application);
        faseRepository = new FaseRepository(application);
        colturaRepository = new ColturaRepository(application);

        // Recupera i dati dai repository
        mPiante = piantaRepository.getAllPiante();
        mFasi = faseRepository.getAllFasi();
        mColture = colturaRepository.getAllColture();
    }

    /**
     * Ottieni una lista di piante.
     *
     * @return Una lista di piante.
     */
    public List<Pianta> getPiante() {
        return mPiante;
    }

    /**
     * Ottieni una lista di colture.
     *
     * @return Una lista di colture.
     */
    public List<Coltura> getColture() {
        return mColture;
    }

    /**
     * Ottieni una pianta dal suo ID.
     *
     * @param piantaId L'ID della pianta da recuperare.
     * @return La pianta con l'ID specificato.
     */
    private Pianta getPiantaById(String piantaId) {
        return piantaRepository.getPiantaById(piantaId);
    }

    /**
     * Aggiorna il database locale.
     */
    public void updateDB(String currentUserId) {
        // Aggiorna il database locale con i dati delle colture
        piantaRepository.updateLocalDB();
        faseRepository.updateLocalDB();
        colturaRepository.updateLocalDB(currentUserId);
    }
}

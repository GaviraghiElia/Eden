package com.unimib.eden.ui.prodottoDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.model.Pianta;
import com.unimib.eden.model.Prodotto;
import com.unimib.eden.repository.FaseRepository;
import com.unimib.eden.repository.PiantaRepository;

import java.util.concurrent.ExecutionException;

/**
 * Classe ViewModel per ProdottoDetailsViewModel.
 * Questa classe si occupa di gestire i dati relativi alla visualizzazione dei dettagli di un prodotto.
 */
public class ProdottoDetailsViewModel extends AndroidViewModel {

    private static final String TAG = "ProdottoDetailsViewModel";
    private PiantaRepository piantaRepository;
    private FaseRepository faseRepository;

    /**
     * Costruttore per ProdottoDetailsViewModel.
     *
     * @param application Un'istanza dell'applicazione.
     */
    public ProdottoDetailsViewModel(@NonNull Application application) {
        super(application);

        // Inizializza repositories
        piantaRepository = new PiantaRepository(application);
        faseRepository = new FaseRepository(application);
    }

    /**
     * Ottieni il nome della pianta associata al prodotto.
     *
     * @param prodotto Il prodotto.
     * @return Il nome della pianta.
     */
    public String getNomePianta(Prodotto prodotto) {
        return getPiantaById(prodotto.getPianta()).getNome();
    }

    /**
     * Ottieni la pianta associata al prodotto.
     *
     * @param prodotto Il prodotto.
     * @return La pianta.
     */
    public Pianta getPianta(Prodotto prodotto) {
        return getPiantaById(prodotto.getPianta());
    }

    /**
     * Ottieni una pianta dal suo ID.
     *
     * @param piantaId L'ID della pianta.
     * @return La pianta con l'ID specificato.
     */
    private Pianta getPiantaById(String piantaId) {
        return piantaRepository.getPiantaById(piantaId);
    }

    /**
     * Ottieni il nome della fase in cui è stato messo in vendita il prodotto.
     *
     * @param prodotto Il prodotto.
     * @return Il nome della fase in cui si è stato messo in vendita il prodotto.
     */
    public String getNomeFase(Prodotto prodotto) throws ExecutionException, InterruptedException {
        return faseRepository.getFaseById(prodotto.getFaseAttuale()).getNomeFase();
    }

    /**
     * Inizializza il ViewModel con il prodotto fornito.
     *
     * @param prodotto Il prodotto.
     */
    public void initialize(Prodotto prodotto) {
        // Inizializzazione aggiuntiva se necessario
    }
}

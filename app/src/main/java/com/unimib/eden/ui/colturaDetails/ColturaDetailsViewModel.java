package com.unimib.eden.ui.colturaDetails;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.PiantaRepository;
import com.unimib.eden.utils.Transformer;

/**
 * Classe ViewModel per ColturaDetailsFragment.
 * Questa classe si occupa di gestire i dati relativi alla visualizzazione dei dettagli di una coltura.
 */
public class ColturaDetailsViewModel extends AndroidViewModel {
    private PiantaRepository piantaRepository;

    /**
     * Costruttore per ColturaDetailsViewModel.
     *
     * @param application Un'istanza dell'applicazione.
     */
    public ColturaDetailsViewModel(@NonNull Application application) {
        super(application);

        // Inizializza PiantaRepository
        piantaRepository = new PiantaRepository(application);
    }

    /**
     * Ottieni la data del prossimo innaffiamento formattata come stringa.
     *
     * @param context Il contesto.
     * @param coltura La coltura.
     * @return La data del prossimo innaffiamento formattata come stringa.
     */
    public String getProssimoInnaffiamento(Context context, Coltura coltura) {
        return Transformer.formatProssimoInnaffiamento(context, coltura, getPiantaById(coltura.getIdPianta()));
    }

    /**
     * Ottieni il nome della pianta associata alla coltura.
     *
     * @param coltura La coltura.
     * @return Il nome della pianta.
     */
    public String getNomePianta(Coltura coltura) {
        return getPiantaById(coltura.getIdPianta()).getNome();
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
     * Inizializza il ViewModel con la coltura fornita.
     *
     * @param coltura La coltura.
     */
    public void initialize(Coltura coltura) {
        // Inizializzazione aggiuntiva se necessario
    }
}

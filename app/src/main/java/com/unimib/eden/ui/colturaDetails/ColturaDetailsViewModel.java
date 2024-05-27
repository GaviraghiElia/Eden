package com.unimib.eden.ui.colturaDetails;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Fase;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.ColturaRepository;
import com.unimib.eden.repository.FaseRepository;
import com.unimib.eden.repository.PiantaRepository;
import com.unimib.eden.utils.Transformer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Classe ViewModel per ColturaDetailsFragment.
 * Questa classe si occupa di gestire i dati relativi alla visualizzazione dei dettagli di una coltura.
 */
public class ColturaDetailsViewModel extends AndroidViewModel {

    private static final String TAG = "ColturaDetailsViewModel";
    private PiantaRepository piantaRepository;
    private FaseRepository faseRepository;
    private ColturaRepository colturaRepository;

    /**
     * Costruttore per ColturaDetailsViewModel.
     *
     * @param application Un'istanza dell'applicazione.
     */
    public ColturaDetailsViewModel(@NonNull Application application) {
        super(application);

        // Inizializza PiantaRepository
        piantaRepository = new PiantaRepository(application);
        faseRepository = new FaseRepository(application);
        colturaRepository = new ColturaRepository(application);
    }

    /**
     * Ottieni la data del prossimo innaffiamento formattata come stringa.
     *
     * @param context Il contesto.
     * @param coltura La coltura.
     * @return La data del prossimo innaffiamento formattata come stringa.
     */
    public String getProssimoInnaffiamento(Context context, Coltura coltura) {
        return Transformer.formatProssimoInnaffiamento(context, coltura);
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
     * Ottieni la pianta associata alla coltura.
     *
     * @param coltura La coltura.
     * @return La pianta.
     */
    public Pianta getPianta(Coltura coltura) {
        return getPiantaById(coltura.getIdPianta());
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
     * Ottieni il nome della fase in cui si trova attualmente la coltura.
     *
     * @param coltura La coltura.
     * @return Il nome della fase in cui si trova la coltura.
     */
    public String getNomeFase(Coltura coltura) throws ExecutionException, InterruptedException {
        String idFase = getPiantaById(coltura.getIdPianta()).getFasi().get(coltura.getFaseAttuale());
        ArrayList<String> fasiId = new ArrayList<>();
        fasiId.add(idFase);
        List<Fase> fasi = faseRepository.getFasiID(fasiId);
        return fasi.get(0).getNomeFase();
    }

    /**
     * Inizializza il ViewModel con la coltura fornita.
     *
     * @param coltura La coltura.
     */
    public void initialize(Coltura coltura) {
        // Inizializzazione aggiuntiva se necessario
    }

    public void updateDataInnaffiamentoColtura(Coltura coltura) {
        colturaRepository.updateDataInnaffiamentoColtura(coltura);
    }

    public void updateDataInnaffiamentoColtura(Coltura coltura, Date newDate) {
        colturaRepository.updateDataInnaffiamentoColtura(coltura, newDate);
    }
}

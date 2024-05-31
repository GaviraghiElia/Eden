package com.unimib.eden.ui.irrigazioni;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Fase;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.model.weather.Condition;
import com.unimib.eden.model.weather.Day;
import com.unimib.eden.model.weather.ForecastDay;
import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.model.weather.WeatherHistory;
import com.unimib.eden.model.weather.WeatherSearchLocation;
import com.unimib.eden.repository.ColturaRepository;
import com.unimib.eden.repository.FaseRepository;
import com.unimib.eden.repository.PiantaRepository;
import com.unimib.eden.repository.WeatherRepository;
import com.unimib.eden.utils.Transformer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe ViewModel per IrrigazioniFragment.
 * Questa classe si occupa di gestire i dati correlati a IrrigazioniFragment.
 *
 * @author Alice Hoa Galli
 */
public class IrrigazioniViewModel extends AndroidViewModel {
    private static final String TAG = "IrrigazioniViewModel";

    private List<Pianta> mPiante;

    private List<Fase> mFasi;

    private LiveData<List<Coltura>> mColture;
    private LiveData<List<Coltura>> mColtureDaIrrigare;
    private LiveData<List<ForecastDay>> forecastDayLiveData;
    private PiantaRepository piantaRepository;
    private ColturaRepository colturaRepository;

    private FaseRepository faseRepository;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //parte del weatherviewmodel
    private WeatherRepository repository;

    private LiveData<WeatherHistory> historyLiveData;
    private LiveData<List<WeatherSearchLocation>> searchLocationLiveData;
    private LiveData<WeatherForecast> forecastLiveData;


    /**
     * Costruttore per HomeViewModel.
     *
     * @param application Un'istanza dell'applicazione.
     */
    public IrrigazioniViewModel(Application application) {
        super(application);

        // Inizializza i repository
        piantaRepository = new PiantaRepository(application);
        faseRepository = new FaseRepository(application);
        colturaRepository = new ColturaRepository(application);

        // Recupera i dati dai repository
        mPiante = piantaRepository.getAllPiante();
        mFasi = faseRepository.getAllFasi();
        mColture = colturaRepository.getAllColture();
        mColtureDaIrrigare = colturaRepository.getAllColtureDaInnaffiare((new Date()).getTime()/ (1000 * 60 * 60 * 24));
        //Log.d(TAG, "IrrigazioniViewModel: " + (1716210570396L/ (1000 * 60 * 60 * 24)));
        //Log.d(TAG, "IrrigazioniViewModel: " + (new Date()).getTime()/ (1000 * 60 * 60 * 24));

        //qui devo prendere le previsioni e riempire il LiveData
        repository = new WeatherRepository();
    }

    //operazioni di Elia
    public LiveData<WeatherForecast> getForecast(String location, int days, String aqi, String alerts) {
        forecastLiveData = repository.getForecast(location, days, aqi, alerts);
        return forecastLiveData;
    }

    public LiveData<WeatherHistory> getHistory(String location, LocalDate localDate){
        historyLiveData = repository.getHistory(location, localDate);
        return historyLiveData;
    }

    public LiveData<List<WeatherSearchLocation>> getSearchLocation(String searchLocation){
        searchLocationLiveData = repository.getSearchlocation(searchLocation);
        return searchLocationLiveData;
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
     * Ottieni una lista di fasi.
     *
     * @return Una lista di fasi.
     */
    public List<Fase> getFasi() {return mFasi;}

    /**
     * Ottieni una lista di colture.
     *
     * @return Una lista di colture.
     */
    public LiveData<List<Coltura>> getColture() {
        return mColture;
    }

    /**
     * Ottiene tutte le colture da irrigazione nella data corrente.
     *
     * @return Una lista di tutte le colture da irrigare nella data corrente.
     */
    public LiveData<List<Coltura>> getColtureDaIrrigare() {
        Log.d(TAG, "getColtureDaIrrigare: " + mColtureDaIrrigare.getValue());
        return mColtureDaIrrigare;
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

    /**
     * Aggiorna la data dell'ultimo innaffiamento a quella corrente per la coltura passata come parametro
     * @param coltura La coltura a cui bisogna aggiornare la data di ultimo innaffiamento  alla data corrente
     */
    public void updateDataInnaffiamentoColtura(Coltura coltura) {
        colturaRepository.updateDataInnaffiamentoColtura(coltura);
    }

    /**
     * Aggiorna la data dell'ultimo innaffiamento per la coltura passata come parametro alla data passata come parametro
     * @param coltura La coltura a cui bisogna aggiornare la data di ultimo innaffiamento alla data indicata
     * @param newDate  La data a cui bisogna aggiornare il valore di ultimo innaffiamento della coltura passata come parametro
     */
    public void updateDataInnaffiamentoColtura(Coltura coltura, Date newDate) {
        colturaRepository.updateDataInnaffiamentoColtura(coltura, newDate);
    }
}
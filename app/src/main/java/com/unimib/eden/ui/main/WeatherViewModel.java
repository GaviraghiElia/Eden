package com.unimib.eden.ui.main;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.model.weather.WeatherHistory;
import com.unimib.eden.model.weather.WeatherSearchLocation;
import com.unimib.eden.repository.ColturaRepository;
import com.unimib.eden.repository.WeatherRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ViewModel per gestire e fornire dati meteorologici e aggiornare di conseguenza le colture.
 */
public class WeatherViewModel extends AndroidViewModel {
    private WeatherRepository repository;
    private ColturaRepository colturaRepository;
    private LiveData<WeatherForecast> forecastLiveData;
    private LiveData<WeatherHistory> historyLiveData;
    private LiveData<List<WeatherSearchLocation>> searchLocationLiveData;

    private LiveData<List<Coltura>> mColture;

    /**
     * Costruttore per WeatherViewModel.
     * Inizializza i repository e recupera tutte le colture.
     *
     * @param application Il contesto dell'applicazione.
     */
    public WeatherViewModel(Application application) {
        super(application);
        repository = new WeatherRepository();
        colturaRepository = new ColturaRepository(application);

        mColture = colturaRepository.getAllColture();
    }

    /**
     * Recupera i dati delle previsioni meteorologiche.
     *
     * @param location La località per la quale ottenere le previsioni.
     * @param days Il numero di giorni per le previsioni.
     * @param aqi Parametro dell'indice di qualità dell'aria.
     * @param alerts Parametro degli avvisi.
     * @return LiveData contenente le previsioni meteorologiche.
     */
    public LiveData<WeatherForecast> getForecast(String location, int days, String aqi, String alerts) {
        forecastLiveData = repository.getForecast(location, days, aqi, alerts);
        return forecastLiveData;
    }

    /**
     * Recupera i dati della storia meteorologica.
     *
     * @param location La località per la quale ottenere la storia.
     * @param localDate La data per la quale ottenere la storia.
     * @return LiveData contenente la storia meteorologica.
     */
    public LiveData<WeatherHistory> getHistory(String location, LocalDate localDate){
        historyLiveData = repository.getHistory(location, localDate);
        return historyLiveData;
    }

    /**
     * Recupera le località di ricerca meteorologica.
     *
     * @param searchLocation La query di ricerca della località.
     * @return LiveData contenente una lista di località di ricerca meteorologica.
     */
    public LiveData<List<WeatherSearchLocation>> getSearchLocation(String searchLocation){
        searchLocationLiveData = repository.getSearchlocation(searchLocation);
        return searchLocationLiveData;
    }

    /**
     * Aggiorna i dati di irrigazione delle colture in base alle precipitazioni passate.
     *
     * @param totalprecipMm Precipitazione totale in millimetri.
     * @param colture Lista delle colture da aggiornare.
     * @return True se le colture sono state aggiornate, altrimenti false.
     */
    public boolean updateInnaffiamenti(double totalprecipMm, List<Coltura> colture) {
        if(totalprecipMm > 20) {
            Date currentDate = new Date();
            for (Coltura coltura: colture) {
                Date ultimoInnaffiamento = coltura.getUltimoInnaffiamento();
                if(!(ultimoInnaffiamento.getDay() == currentDate.getDay()
                        && ultimoInnaffiamento.getMonth() == currentDate.getMonth()
                        && ultimoInnaffiamento.getYear() == currentDate.getYear())) {
                    colturaRepository.updateDataInnaffiamentoColture(colture);
                }
            }
            return colture.isEmpty();
        }
        else {
            return false;
        }
    }

    /**
     * Recupera tutte le colture.
     *
     * @return LiveData contenente una lista di colture.
     */
    public LiveData<List<Coltura>> getColture() {
        return mColture;
    }
}

package com.unimib.eden.ui.main;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.weather.Day;
import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.model.weather.WeatherHistory;
import com.unimib.eden.model.weather.WeatherSearchLocation;
import com.unimib.eden.repository.ColturaRepository;
import com.unimib.eden.repository.WeatherRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final String TAG = "WeatherViewModel";

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
     * @param date La data per la quale ottenere la storia.
     * @return LiveData contenente la storia meteorologica.
     */
    public LiveData<WeatherHistory> getHistory(String location, Date date){
        historyLiveData = repository.getHistory(location, date);
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
     * @param dayWeather Condizioni del giorno appena trascorso.
     * @param colture Lista delle colture da aggiornare.
     */
    public void updateInnaffiamenti(Day dayWeather, List<Coltura> colture) {
        Map<Coltura, Date> updates = updatedDatesOfInnaffiamento(colture, dayWeather);
        colturaRepository.updateDataInnaffiamentoColture(updates);
    }

    /**
     * Recupera tutte le colture.
     *
     * @return LiveData contenente una lista di colture.
     */
    public LiveData<List<Coltura>> getColture() {
        return mColture;
    }

    /*
     * Stabilisce la data di ultimo innaffiamento sulla base di ogni coltura sulla base delle condizioni metereologiche passate.
     *
     * @param colture La lista di colture.
     * @param dayWeather Le condizioni meteo passate.
     * @return La mappa contenente le coppie coltura-data da aggiornare.
     */
    public static Map<Coltura, Date> updatedDatesOfInnaffiamento(List<Coltura> colture, Day dayWeather) {
        Map<Coltura, Date> datesForColture = new HashMap<>();
        Date currentDate = new Date();
        for(Coltura coltura: colture) {
            Date ultimoAggiornamento = coltura.getUltimoAggiornamento();
            // Controlla se la coltura oggi non è già stata aggiornata
            if(!(ultimoAggiornamento.getDay() == currentDate.getDay()
                    && ultimoAggiornamento.getMonth() == currentDate.getMonth()
                    && ultimoAggiornamento.getYear() == currentDate.getYear())) {
                // Ha piovuto a sufficienza per considerare le piante come bagnate (ieri)
                if(dayWeather.getTotalprecip_mm() > 20) {
                    Date newDate = new Date(currentDate.getTime() - 1 * 24 * 60 * 60 * 1000);
                    datesForColture.put(coltura, newDate);
                }
                // Ha fatto troppo caldo e secco, anticipa di un giorno la data di innaffiamento
                else if(dayWeather.getAvgtemp_c() > 35 && dayWeather.getAvghumidity() < 50) {
                    Date newDate = new Date(coltura.getUltimoInnaffiamento().getTime() - 2 * 24 * 60 * 60 * 1000);
                    datesForColture.put(coltura, newDate);
                }
                else {
                    // Return null as a date when there is no need to update it
                    datesForColture.put(coltura, null);
                }
            }
        }
        return datesForColture;
    }
}

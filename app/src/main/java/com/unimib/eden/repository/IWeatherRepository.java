package com.unimib.eden.repository;

import androidx.lifecycle.LiveData;

import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.model.weather.WeatherHistory;
import com.unimib.eden.model.weather.WeatherSearchLocation;

import java.util.Date;
import java.util.List;

/**
 * Interfaccia per il repository di dati meteorologici.
 */
public interface IWeatherRepository {
    /**
     * Recupera i dati della storia meteorologica per una data località e data.
     *
     * @param location La località per la quale ottenere la storia.
     * @param date La data per la quale ottenere la storia.
     * @return LiveData contenente i dati della storia meteorologica.
     */
    LiveData<WeatherHistory> getHistory(String location, Date date);

    /**
     * Recupera le località di ricerca meteorologica in base alla query di ricerca.
     *
     * @param locationSearch La query di ricerca della località.
     * @return LiveData contenente una lista di località di ricerca meteorologica.
     */
    LiveData<List<WeatherSearchLocation>> getSearchlocation(String locationSearch);

    /**
     * Recupera i dati delle previsioni meteorologiche per una località e per un dato numero di giorni.
     *
     * @param location La località per la quale ottenere le previsioni.
     * @param days Il numero di giorni per le previsioni.
     * @param aqi Parametro per richiedere l'indice di qualità dell'aria.
     * @param alerts Parametro per richiedere eventuali avvisi.
     * @return LiveData contenente le previsioni meteorologiche.
     */
    LiveData<WeatherForecast> getForecast(String location, int days, String aqi, String alerts);
}

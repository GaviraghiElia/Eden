package com.unimib.eden.service;

import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.model.weather.WeatherHistory;
import com.unimib.eden.model.weather.WeatherSearchLocation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * WeatherService è un'interfaccia che definisce i metodi per interagire con l'API di servizi meteorologici
 * utilizzando Retrofit. Include metodi per ottenere previsioni meteorologiche, dati storici e ricerche di località.
 */
public interface WeatherService {

    /**
     * Ottiene le previsioni meteorologiche per una determinata località.
     *
     * @param apiKey La chiave API per autenticarsi con il servizio.
     * @param location La località per cui ottenere le previsioni meteorologiche.
     * @param days Il numero di giorni per cui ottenere le previsioni.
     * @param aqi Indica se includere o meno i dati sulla qualità dell'aria.
     * @param alerts Indica se includere o meno gli avvisi meteorologici.
     * @return Un oggetto {@link Call} che può essere utilizzato per richiedere le previsioni meteorologiche.
     */
    @GET("forecast.json")
    Call<WeatherForecast> getForecast(
            @Query("key") String apiKey,
            @Query("q") String location,
            @Query("days") int days,
            @Query("aqi") String aqi,
            @Query("alerts") String alerts
    );

    /**
     * Ottiene i dati meteorologici storici per una determinata località e data.
     *
     * @param apiKey La chiave API per autenticarsi con il servizio.
     * @param location La località per cui ottenere i dati storici.
     * @param date La data per cui ottenere i dati storici, nel formato "yyyy-MM-dd".
     * @return Un oggetto {@link Call} che può essere utilizzato per richiedere i dati meteorologici storici.
     */
    @GET("history.json")
    Call<WeatherHistory> getHistory(
            @Query("key") String apiKey,
            @Query("q") String location,
            @Query("dt") String date
    );

    /**
     * Esegue una ricerca automatica per le località corrispondenti a una determinata query.
     *
     * @param apiKey La chiave API per autenticarsi con il servizio.
     * @param locationSearch La query di ricerca per trovare le località corrispondenti.
     * @return Un oggetto {@link Call} che può essere utilizzato per richiedere i risultati della ricerca di località.
     */
    @GET("search.json")
    Call<List<WeatherSearchLocation>> getAutocompleteSearch(
            @Query("key") String apiKey,
            @Query("q") String locationSearch
    );
}

package com.unimib.eden.model.weather;

import java.util.List;

/**
 * La classe Forecast rappresenta le previsioni meteorologiche.
 * Include una lista di oggetti ForecastDay, ciascuno dei quali contiene le previsioni per un singolo giorno.
 */
public class Forecast {

    private List<ForecastDay> forecastday;

    /**
     * Costruttore della classe Forecast.
     *
     * @param forecastDay La lista delle previsioni giornaliere.
     */
    public Forecast(List<ForecastDay> forecastDay) {
        this.forecastday = forecastDay;
    }

    /**
     * Restituisce la lista delle previsioni giornaliere.
     *
     * @return La lista delle previsioni giornaliere.
     */
    public List<ForecastDay> getForecastday() {
        return forecastday;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "forecastday=" + forecastday.toString() +
                '}';
    }
}

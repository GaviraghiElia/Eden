package com.unimib.eden.model.weather;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * The Forecast class represents weather forecasts.
 * It includes a list of ForecastDay objects, each of which contains forecasts for a single day.
 */
public class Forecast {

    private final List<ForecastDay> forecastday;

    /**
     * Constructor for the Forecast class.
     *
     * @param forecastDay The list of daily forecasts.
     */
    public Forecast(List<ForecastDay> forecastDay) {
        this.forecastday = forecastDay;
    }

    /**
     * Returns the list of daily forecasts.
     *
     * @return The list of daily forecasts.
     */
    public List<ForecastDay> getForecastday() {
        return forecastday;
    }

    @NonNull
    @Override
    public String toString() {
        return "Forecast{" +
                "forecastday=" + forecastday.toString() +
                '}';
    }
}

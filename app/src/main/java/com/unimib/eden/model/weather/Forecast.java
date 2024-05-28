package com.unimib.eden.model.weather;

import java.util.List;

public class Forecast {

    private List<ForecastDay> forecastday;

    public Forecast(List<ForecastDay>  forecastDay){
        this.forecastday = forecastDay;
    }
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
package com.unimib.eden.model.weather;

import com.unimib.eden.model.weather.Forecast;
import com.unimib.eden.model.weather.Location;

public class WeatherHistory {
    private Location location;
    private Forecast forecast;

    public WeatherHistory(Location location, Forecast forecast) {
        this.location = location;
        this.forecast = forecast;
    }

    public WeatherHistory() {
    }

    @Override
    public String toString() {
        return "WeatherHistory{" +
                "location=" + location +
                ", forecast=" + forecast.toString() +
                '}';
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }
}
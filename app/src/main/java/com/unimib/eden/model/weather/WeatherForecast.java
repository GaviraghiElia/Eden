package com.unimib.eden.model.weather;

public class WeatherForecast {
    private Location location;
    private Forecast forecast;
    private Current current;

    public WeatherForecast(Location location, Forecast forecast, Current current){
        this.location = location;
        this.forecast = forecast;
        this.current = current;
    }

    @Override
    public String toString() {
        return "\n" + location +
               "\n" + current.toString() +
               "\n" + forecast.toString() +
               '}';
    }

    public Location getLocation() {
        return location;
    }

    public Forecast getForecast() {
        return forecast;
    }
}

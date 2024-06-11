package com.unimib.eden.model.weather;

import androidx.annotation.NonNull;

/**
 * This class represents the weather forecast for a specific location.
 */
public class WeatherForecast {
    private Location location;
    private Forecast forecast;
    private Current current;

    /**
     * Constructor for the WeatherForecast class.
     *
     * @param location The location for which the weather forecast is desired.
     * @param current  The current weather conditions for the location.
     * @param forecast The weather forecast for the location.
     */
    public WeatherForecast(Location location, Current current, Forecast forecast) {
        this.location = location;
        this.forecast = forecast;
        this.current = current;
    }

    /**
     * Empty constructor for the WeatherForecast class.
     */
    public WeatherForecast() {

    }

    /**
     * Returns a string representation of the weather forecast.
     *
     * @return A string representing the weather forecast.
     */
    @NonNull
    @Override
    public String toString() {
        return "\n" + location +
                "\n" + current.toString() +
                "\n" + forecast.toString() +
                '}';
    }

    /**
     * Returns the location.
     *
     * @return The location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns the weather forecast.
     *
     * @return The weather forecast.
     */
    public Forecast getForecast() {
        return forecast;
    }

    /**
     * Returns the current weather conditions.
     *
     * @return The current weather conditions.
     */
    public Current getCurrent() {
        return current;
    }
}

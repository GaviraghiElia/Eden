package com.unimib.eden.model.weather;

import androidx.annotation.NonNull;

/**
 * This class represents the weather history for a specific location.
 */
public class WeatherHistory {
    private Location location;
    private Forecast forecast;

    /**
     * Constructor for the WeatherHistory class.
     *
     * @param location The location for which the weather history is desired.
     * @param forecast The weather forecast associated with the location.
     */
    public WeatherHistory(Location location, Forecast forecast) {
        this.location = location;
        this.forecast = forecast;
    }

    /**
     * Returns a string representation of the weather history.
     *
     * @return A string representing the weather history.
     */
    @NonNull
    @Override
    public String toString() {
        return "WeatherHistory{" +
                "location=" + location +
                ", forecast=" + forecast.toString() +
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
     * Sets the location.
     *
     * @param location The location to set.
     */
    public void setLocation(Location location) {
        this.location = location;
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
     * Sets the weather forecast.
     *
     * @param forecast The weather forecast to set.
     */
    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }
}

package com.unimib.eden.model.weather;

/**
 * Questa classe rappresenta le previsioni meteorologiche per una data località.
 */
public class WeatherForecast {
    private Location location;
    private Forecast forecast;
    private Current current;

    /**
     * Costruttore della classe WeatherForecast.
     *
     * @param location la località per cui si desidera ottenere le previsioni meteorologiche.
     * @param current le condizioni meteorologiche attuali per la località.
     * @param forecast le previsioni meteorologiche per la località.
     */
    public WeatherForecast(Location location, Current current, Forecast forecast) {
        this.location = location;
        this.forecast = forecast;
        this.current = current;
    }

    /**
     * Costruttore vuoto della classe WeatherForecast.
     */
    public WeatherForecast() {

    }

    /**
     * Restituisce una rappresentazione in stringa delle previsioni meteorologiche.
     *
     * @return una stringa che rappresenta le previsioni meteorologiche.
     */
    @Override
    public String toString() {
        return "\n" + location +
                "\n" + current.toString() +
                "\n" + forecast.toString() +
                '}';
    }

    /**
     * Restituisce la località.
     *
     * @return la località.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Restituisce le previsioni meteorologiche.
     *
     * @return le previsioni meteorologiche.
     */
    public Forecast getForecast() {
        return forecast;
    }

    /**
     * Restituisce le condizioni meteorologiche attuali.
     *
     * @return le condizioni meteorologiche attuali.
     */
    public Current getCurrent() {
        return current;
    }
}
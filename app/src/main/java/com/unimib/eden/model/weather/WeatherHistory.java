package com.unimib.eden.model.weather;

/**
 * Questa classe rappresenta la storia meteorologica per una data località.
 */
public class WeatherHistory {
    private Location location;
    private Forecast forecast;

    /**
     * Costruttore della classe WeatherHistory.
     *
     * @param location la località per cui si desidera ottenere la storia meteorologica.
     * @param forecast le previsioni meteorologiche associate alla località.
     */
    public WeatherHistory(Location location, Forecast forecast) {
        this.location = location;
        this.forecast = forecast;
    }

    /**
     * Restituisce una rappresentazione in stringa della storia meteorologica.
     *
     * @return una stringa che rappresenta la storia meteorologica.
     */
    @Override
    public String toString() {
        return "WeatherHistory{" +
                "location=" + location +
                ", forecast=" + forecast.toString() +
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
     * Imposta la località.
     *
     * @param location la località da impostare.
     */
    public void setLocation(Location location) {
        this.location = location;
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
     * Imposta le previsioni meteorologiche.
     *
     * @param forecast le previsioni meteorologiche da impostare.
     */
    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }
}
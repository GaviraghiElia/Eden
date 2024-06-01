package com.unimib.eden.model.weather;

/**
 * Questa classe rappresenta una località con informazioni geografiche di base.
 */
public class Location {
    private String name;
    private String region;
    private String country;
    private double lat;
    private double lon;

    /**
     * Costruttore della classe Location.
     *
     * @param name    il nome della località.
     * @param region  la regione della località.
     * @param country il paese della località.
     * @param lat     la latitudine della località.
     * @param lon     la longitudine della località.
     */
    public Location(String name, String region, String country, double lat, double lon) {
        this.name = name;
        this.region = region;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Restituisce una rappresentazione in stringa della località.
     *
     * @return una stringa che rappresenta la località.
     */
    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }

    /**
     * Restituisce il nome della località.
     *
     * @return il nome della località.
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome della località.
     *
     * @param name il nome da impostare.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Restituisce la regione della località.
     *
     * @return la regione della località.
     */
    public String getRegion() {
        return region;
    }

    /**
     * Imposta la regione della località.
     *
     * @param region la regione da impostare.
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Restituisce il paese della località.
     *
     * @return il paese della località.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Imposta il paese della località.
     *
     * @param country il paese da impostare.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Restituisce la latitudine della località.
     *
     * @return la latitudine della località.
     */
    public double getLat() {
        return lat;
    }

    /**
     * Imposta la latitudine della località.
     *
     * @param lat la latitudine da impostare.
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Restituisce la longitudine della località.
     *
     * @return la longitudine della località.
     */
    public double getLon() {
        return lon;
    }

    /**
     * Imposta la longitudine della località.
     *
     * @param lon la longitudine da impostare.
     */
    public void setLon(double lon) {
        this.lon = lon;
    }
}
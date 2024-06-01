package com.unimib.eden.model.weather;

/**
 * Questa classe rappresenta una località di ricerca meteo.
 */
public class WeatherSearchLocation {

    private int id;
    private String name;
    private String region;
    private String country;
    private double lat;
    private double lon;
    private String url;

    /**
     * Restituisce l'identificativo della località.
     *
     * @return l'identificativo della località.
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta l'identificativo della località.
     *
     * @param id l'identificativo da impostare.
     */
    public void setId(int id) {
        this.id = id;
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

    /**
     * Restituisce l'URL della località.
     *
     * @return l'URL della località.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Imposta l'URL della località.
     *
     * @param url l'URL da impostare.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Restituisce una rappresentazione in stringa della località di ricerca meteo.
     *
     * @return una stringa che rappresenta la località di ricerca meteo.
     */
    @Override
    public String toString() {
        return "WeatherSearchLocation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", url='" + url + '\'' +
                '}';
    }
}
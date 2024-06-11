package com.unimib.eden.model.weather;

/**
 * This class represents a weather search location.
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
     * Returns the ID of the location.
     *
     * @return The ID of the location.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the location.
     *
     * @param id The ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the location.
     *
     * @return The name of the location.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the location.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the region of the location.
     *
     * @return The region of the location.
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the region of the location.
     *
     * @param region The region to set.
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Returns the country of the location.
     *
     * @return The country of the location.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of the location.
     *
     * @param country The country to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Returns the latitude of the location.
     *
     * @return The latitude of the location.
     */
    public double getLat() {
        return lat;
    }

    /**
     * Sets the latitude of the location.
     *
     * @param lat The latitude to set.
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * Returns the longitude of the location.
     *
     * @return The longitude of the location.
     */
    public double getLon() {
        return lon;
    }

    /**
     * Sets the longitude of the location.
     *
     * @param lon The longitude to set.
     */
    public void setLon(double lon) {
        this.lon = lon;
    }

    /**
     * Returns the URL of the location.
     *
     * @return The URL of the location.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL of the location.
     *
     * @param url The URL to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns a string representation of the weather search location.
     *
     * @return A string representing the weather search location.
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

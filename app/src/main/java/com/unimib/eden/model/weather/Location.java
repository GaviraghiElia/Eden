package com.unimib.eden.model.weather;

import androidx.annotation.NonNull;

/**
 * This class represents a location with basic geographical information.
 */
public class Location {
    private String name;
    private String region;
    private String country;
    private double lat;
    private double lon;

    /**
     * Constructor for the Location class.
     *
     * @param name    The name of the location.
     * @param region  The region of the location.
     * @param country The country of the location.
     * @param lat     The latitude of the location.
     * @param lon     The longitude of the location.
     */
    public Location(String name, String region, String country, double lat, double lon) {
        this.name = name;
        this.region = region;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Returns a string representation of the location.
     *
     * @return A string representing the location.
     */
    @NonNull
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
}

package com.unimib.eden.model.weather;

import java.util.List;

public class WeatherSearchLocation {

    /*
    private List<LocationSearch> locations;

    public List<LocationSearch> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationSearch> locations) {
        this.locations = locations;
    }

    @Override
    public String toString() {
        return "WeatherSearchLocation{" +
                "locations=" + locations +
                '}';
    }

     */

    private int id;
    private String name;
    private String region;
    private String country;
    private double lat;
    private double lon;
    private String url;

    @Override
    public String toString() {
        return "LocationSearch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", url='" + url + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
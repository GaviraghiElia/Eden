package com.unimib.eden.model.weather;

import androidx.annotation.NonNull;

/**
 * The Current class represents current weather data.
 * It includes information such as temperature, weather conditions, precipitation, and humidity.
 */
public class Current {
    private String lastUpdated;
    private double temp_c;
    private Condition condition;
    private double precipitations_mm;
    private double precipitations_in;
    private double humidity;

    /**
     * Constructor for the Current class.
     *
     * @param lastUpdated The date and time of the last data update.
     * @param temp_c The current temperature in degrees Celsius.
     * @param condition The current weather conditions.
     * @param precipitations_mm The current precipitation in millimeters.
     * @param precipitations_in The current precipitation in inches.
     * @param humidity The current humidity in percentage.
     */
    public Current(String lastUpdated, double temp_c, Condition condition, double precipitations_mm, double precipitations_in, double humidity) {
        this.lastUpdated = lastUpdated;
        this.temp_c = temp_c;
        this.condition = condition;
        this.precipitations_mm = precipitations_mm;
        this.precipitations_in = precipitations_in;
        this.humidity = humidity;
    }

    @NonNull
    @Override
    public String toString() {
        return "Current{" +
                "last_updated='" + lastUpdated + '\'' +
                ", temp_c=" + temp_c +
                ", condition=" + condition +
                ", precipitations_mm=" + precipitations_mm +
                ", precipitations_in=" + precipitations_in +
                ", humidity=" + humidity +
                '}';
    }

    /**
     * Returns the date and time of the last data update.
     *
     * @return A string representing the date and time of the last update.
     */
    public String getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Sets the date and time of the last data update.
     *
     * @param lastUpdated The date and time of the last update.
     */
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Returns the current temperature in degrees Celsius.
     *
     * @return The current temperature in degrees Celsius.
     */
    public double getTemp_c() {
        return temp_c;
    }

    /**
     * Sets the current temperature in degrees Celsius.
     *
     * @param temp_c The current temperature in degrees Celsius.
     */
    public void setTemp_c(double temp_c) {
        this.temp_c = temp_c;
    }

    /**
     * Returns the current weather conditions.
     *
     * @return The current weather conditions.
     */
    public Condition getCondition() {
        return condition;
    }

    /**
     * Sets the current weather conditions.
     *
     * @param condition The current weather conditions.
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    /**
     * Returns the current precipitation in millimeters.
     *
     * @return The current precipitation in millimeters.
     */
    public double getPrecipitations_mm() {
        return precipitations_mm;
    }

    /**
     * Sets the current precipitation in millimeters.
     *
     * @param precipitations_mm The current precipitation in millimeters.
     */
    public void setPrecipitations_mm(double precipitations_mm) {
        this.precipitations_mm = precipitations_mm;
    }

    /**
     * Returns the current precipitation in inches.
     *
     * @return The current precipitation in inches.
     */
    public double getPrecipitations_in() {
        return precipitations_in;
    }

    /**
     * Sets the current precipitation in inches.
     *
     * @param precipitations_in The current precipitation in inches.
     */
    public void setPrecipitations_in(double precipitations_in) {
        this.precipitations_in = precipitations_in;
    }

    /**
     * Returns the current humidity in percentage.
     *
     * @return The current humidity in percentage.
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * Sets the current humidity in percentage.
     *
     * @param humidity The current humidity in percentage.
     */
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}

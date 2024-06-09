package com.unimib.eden.model.weather;

import androidx.annotation.NonNull;

/**
 * The ForecastDay class represents weather forecasts for a single day.
 * It includes the date and a Day object containing weather condition details.
 */
public class ForecastDay {
    private final String date;
    private final Day day;

    /**
     * Constructor for the ForecastDay class.
     *
     * @param date The date of the forecast in String format.
     * @param day  The Day object containing weather condition details.
     */
    public ForecastDay(String date, Day day) {
        this.date = date;
        this.day = day;
    }

    /**
     * Returns the date of the forecast.
     *
     * @return The date of the forecast.
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the weather condition details for the day.
     *
     * @return The Day object with weather condition details.
     */
    public Day getDay() {
        return day;
    }

    @NonNull
    @Override
    public String toString() {
        return "\nForecastDay{" +
                "date='" + date + '\'' +
                "\nday=" + day.toString() +
                '}';
    }
}

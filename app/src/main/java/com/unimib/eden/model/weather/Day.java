package com.unimib.eden.model.weather;

import androidx.annotation.NonNull;

/**
 * The Day class represents daily weather data.
 * It includes information such as maximum, minimum, and average temperature, total precipitation, average humidity,
 * daily rain probability, and weather conditions.
 */
public class Day {
    private final double maxtemp_c;
    private final double mintemp_c;
    private final double avgtemp_c;
    private final double totalprecip_mm;
    private final int avghumidity;
    private final int daily_will_it_rain;
    private final int daily_chance_of_rain;
    private final Condition condition;

    /**
     * Constructor for the Day class.
     *
     * @param maxtemp_c Maximum temperature in degrees Celsius.
     * @param mintemp_c Minimum temperature in degrees Celsius.
     * @param avgtemp_c Average temperature in degrees Celsius.
     * @param totalprecip_mm Total precipitation in millimeters.
     * @param avghumidity Average humidity in percentage.
     * @param daily_will_it_rain Indicates whether it will rain (1 if likely, 0 otherwise).
     * @param daily_chance_of_rain Daily chance of rain in percentage.
     * @param condition Daily weather conditions.
     */
    public Day(double maxtemp_c, double mintemp_c, double avgtemp_c, double totalprecip_mm, int avghumidity, int daily_will_it_rain, int daily_chance_of_rain, Condition condition) {
        this.maxtemp_c = maxtemp_c;
        this.mintemp_c = mintemp_c;
        this.avgtemp_c = avgtemp_c;
        this.totalprecip_mm = totalprecip_mm;
        this.avghumidity = avghumidity;
        this.daily_will_it_rain = daily_will_it_rain;
        this.daily_chance_of_rain = daily_chance_of_rain;
        this.condition = condition;
    }

    @NonNull
    @Override
    public String toString() {
        return "Day{" +
                "maxtemp_c=" + maxtemp_c +
                ", mintemp_c=" + mintemp_c +
                ", avgtemp_c=" + avgtemp_c +
                ", totalprecip_mm=" + totalprecip_mm +
                ", avghumidity=" + avghumidity +
                ", daily_will_it_rain=" + daily_will_it_rain +
                ", daily_chance_of_rain=" + daily_chance_of_rain +
                ", condition=" + condition.toString() +
                '}';
    }

    /**
     * Returns the maximum temperature in degrees Celsius.
     *
     * @return The maximum temperature in degrees Celsius.
     */
    public double getMaxtemp_c() {
        return maxtemp_c;
    }

    /**
     * Returns the minimum temperature in degrees Celsius.
     *
     * @return The minimum temperature in degrees Celsius.
     */
    public double getMintemp_c() {
        return mintemp_c;
    }

    /**
     * Returns the average temperature in degrees Celsius.
     *
     * @return The average temperature in degrees Celsius.
     */
    public double getAvgtemp_c() {
        return avgtemp_c;
    }

    /**
     * Returns the total precipitation in millimeters.
     *
     * @return The total precipitation in millimeters.
     */
    public double getTotalprecip_mm() {
        return totalprecip_mm;
    }

    /**
     * Returns the average humidity in percentage.
     *
     * @return The average humidity in percentage.
     */
    public int getAvghumidity() {
        return avghumidity;
    }

    /**
     * Returns whether it will rain (1 if likely, 0 otherwise).
     *
     * @return An integer indicating whether it will rain.
     */
    public int getDaily_will_it_rain() {
        return daily_will_it_rain;
    }

    /**
     * Returns the daily chance of rain in percentage.
     *
     * @return The daily chance of rain in percentage.
     */
    public int getDaily_chance_of_rain() {
        return daily_chance_of_rain;
    }

    /**
     * Returns the daily weather conditions.
     *
     * @return The daily weather conditions.
     */
    public Condition getCondition() {
        return condition;
    }
}

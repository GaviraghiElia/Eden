package com.unimib.eden.model.weather;

import androidx.annotation.NonNull;

/**
 * The Day class represents daily weather data.
 * It includes information such as maximum, minimum, and average temperature, total precipitation, average humidity,
 * daily rain probability, and weather conditions.
 */
public class Day {
    private final double maxTemp_c;
    private final double minTemp_c;
    private final double avgTemp_c;
    private final double totalPrecipitations_mm;
    private final int avgHumidity;
    private final int dailyWillItRain;
    private final int dailyChanceOfRain;
    private final Condition condition;

    /**
     * Constructor for the Day class.
     *
     * @param maxTemp_c Maximum temperature in degrees Celsius.
     * @param minTemp_c Minimum temperature in degrees Celsius.
     * @param avgTemp_c Average temperature in degrees Celsius.
     * @param totalPrecipitations_mm Total precipitation in millimeters.
     * @param avgHumidity Average humidity in percentage.
     * @param dailyWillItRain Indicates whether it will rain (1 if likely, 0 otherwise).
     * @param dailyChanceOfRain Daily chance of rain in percentage.
     * @param condition Daily weather conditions.
     */
    public Day(double maxTemp_c, double minTemp_c, double avgTemp_c, double totalPrecipitations_mm, int avgHumidity, int dailyWillItRain, int dailyChanceOfRain, Condition condition) {
        this.maxTemp_c = maxTemp_c;
        this.minTemp_c = minTemp_c;
        this.avgTemp_c = avgTemp_c;
        this.totalPrecipitations_mm = totalPrecipitations_mm;
        this.avgHumidity = avgHumidity;
        this.dailyWillItRain = dailyWillItRain;
        this.dailyChanceOfRain = dailyChanceOfRain;
        this.condition = condition;
    }

    @NonNull
    @Override
    public String toString() {
        return "Day{" +
                "maxTemp_c=" + maxTemp_c +
                ", minTemp_c=" + minTemp_c +
                ", avgTemp_c=" + avgTemp_c +
                ", totalPrecipitations_mm=" + totalPrecipitations_mm +
                ", avgHumidity=" + avgHumidity +
                ", daily_will_it_rain=" + dailyWillItRain +
                ", daily_chance_of_rain=" + dailyChanceOfRain +
                ", condition=" + condition.toString() +
                '}';
    }

    /**
     * Returns the maximum temperature in degrees Celsius.
     *
     * @return The maximum temperature in degrees Celsius.
     */
    public double getMaxTemp_c() {
        return maxTemp_c;
    }

    /**
     * Returns the minimum temperature in degrees Celsius.
     *
     * @return The minimum temperature in degrees Celsius.
     */
    public double getMinTemp_c() {
        return minTemp_c;
    }

    /**
     * Returns the average temperature in degrees Celsius.
     *
     * @return The average temperature in degrees Celsius.
     */
    public double getAvgTemp_c() {
        return avgTemp_c;
    }

    /**
     * Returns the total precipitation in millimeters.
     *
     * @return The total precipitation in millimeters.
     */
    public double getTotalPrecipitations_mm() {
        return totalPrecipitations_mm;
    }

    /**
     * Returns the average humidity in percentage.
     *
     * @return The average humidity in percentage.
     */
    public int getAvgHumidity() {
        return avgHumidity;
    }

    /**
     * Returns whether it will rain (1 if likely, 0 otherwise).
     *
     * @return An integer indicating whether it will rain.
     */
    public int getDailyWillItRain() {
        return dailyWillItRain;
    }

    /**
     * Returns the daily chance of rain in percentage.
     *
     * @return The daily chance of rain in percentage.
     */
    public int getDailyChanceOfRain() {
        return dailyChanceOfRain;
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

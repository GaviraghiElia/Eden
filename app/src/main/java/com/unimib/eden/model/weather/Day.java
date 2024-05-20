package com.unimib.eden.model.weather;

public class Day {
    private double maxtemp_c;
    private double mintemp_c;
    private double avgtemp_c;
    private double totalprecip_mm;
    private int avghumidity;
    private int daily_will_it_rain;
    private int daily_chance_of_rain;
    private Condition condition;

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

    public double getMaxtemp_c() {
        return maxtemp_c;
    }

    public double getMintemp_c() {
        return mintemp_c;
    }

    public double getAvgtemp_c() {
        return avgtemp_c;
    }

    public double getTotalprecip_mm() {
        return totalprecip_mm;
    }

    public int getAvghumidity() {
        return avghumidity;
    }

    public int getDaily_will_it_rain() {
        return daily_will_it_rain;
    }

    public int getDaily_chance_of_rain() {
        return daily_chance_of_rain;
    }

    public Condition getCondition() {
        return condition;
    }
}
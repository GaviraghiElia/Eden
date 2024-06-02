package com.unimib.eden.model.weather;

/**
 * La classe Day rappresenta i dati meteorologici giornalieri.
 * Include informazioni come la temperatura massima, minima e media, la precipitazione totale, l'umidità media,
 * la probabilità di pioggia e le condizioni meteo.
 */
public class Day {
    private double maxtemp_c;
    private double mintemp_c;
    private double avgtemp_c;
    private double totalprecip_mm;
    private int avghumidity;
    private int daily_will_it_rain;
    private int daily_chance_of_rain;
    private Condition condition;

    /**
     * Costruttore della classe Day.
     *
     * @param maxtemp_c Temperatura massima in gradi Celsius.
     * @param mintemp_c Temperatura minima in gradi Celsius.
     * @param avgtemp_c Temperatura media in gradi Celsius.
     * @param totalprecip_mm Precipitazione totale in millimetri.
     * @param avghumidity Umidità media in percentuale.
     * @param daily_will_it_rain Indica se pioverà (1 se è probabile, 0 altrimenti).
     * @param daily_chance_of_rain Probabilità giornaliera di pioggia in percentuale.
     * @param condition Le condizioni meteorologiche giornaliere.
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
     * Restituisce la temperatura massima in gradi Celsius.
     *
     * @return La temperatura massima in gradi Celsius.
     */
    public double getMaxtemp_c() {
        return maxtemp_c;
    }

    /**
     * Restituisce la temperatura minima in gradi Celsius.
     *
     * @return La temperatura minima in gradi Celsius.
     */
    public double getMintemp_c() {
        return mintemp_c;
    }

    /**
     * Restituisce la temperatura media in gradi Celsius.
     *
     * @return La temperatura media in gradi Celsius.
     */
    public double getAvgtemp_c() {
        return avgtemp_c;
    }

    /**
     * Restituisce la precipitazione totale in millimetri.
     *
     * @return La precipitazione totale in millimetri.
     */
    public double getTotalprecip_mm() {
        return totalprecip_mm;
    }

    /**
     * Restituisce l'umidità media in percentuale.
     *
     * @return L'umidità media in percentuale.
     */
    public int getAvghumidity() {
        return avghumidity;
    }

    /**
     * Restituisce l'indicazione se pioverà (1 se è probabile, 0 altrimenti).
     *
     * @return Un intero che indica se pioverà.
     */
    public int getDaily_will_it_rain() {
        return daily_will_it_rain;
    }

    /**
     * Restituisce la probabilità giornaliera di pioggia in percentuale.
     *
     * @return La probabilità giornaliera di pioggia in percentuale.
     */
    public int getDaily_chance_of_rain() {
        return daily_chance_of_rain;
    }

    /**
     * Restituisce le condizioni meteorologiche giornaliere.
     *
     * @return Le condizioni meteorologiche giornaliere.
     */
    public Condition getCondition() {
        return condition;
    }
}

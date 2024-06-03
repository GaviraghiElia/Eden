package com.unimib.eden.model.weather;

/**
 * La classe Current rappresenta i dati meteorologici attuali.
 * Include informazioni come la temperatura, le condizioni meteo, la precipitazione e l'umidità.
 */
public class Current {
    private String last_updated;
    private double temp_c;
    private Condition condition;
    private double precip_mm;
    private double precip_in;
    private double humidity;

    /**
     * Costruttore della classe Current.
     *
     * @param last_updated La data e l'ora dell'ultimo aggiornamento dei dati.
     * @param temp_c La temperatura attuale in gradi Celsius.
     * @param condition Le condizioni meteorologiche attuali.
     * @param precip_mm La precipitazione attuale in millimetri.
     * @param precip_in La precipitazione attuale in pollici.
     * @param humidity L'umidità attuale in percentuale.
     */
    public Current(String last_updated, double temp_c, Condition condition, double precip_mm, double precip_in, double humidity) {
        this.last_updated = last_updated;
        this.temp_c = temp_c;
        this.condition = condition;
        this.precip_mm = precip_mm;
        this.precip_in = precip_in;
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "Current{" +
                "last_updated='" + last_updated + '\'' +
                ", temp_c=" + temp_c +
                ", condition=" + condition +
                ", precip_mm=" + precip_mm +
                ", precip_in=" + precip_in +
                ", humidity=" + humidity +
                '}';
    }

    /**
     * Restituisce la data e l'ora dell'ultimo aggiornamento dei dati.
     *
     * @return Una stringa che rappresenta la data e l'ora dell'ultimo aggiornamento.
     */
    public String getLast_updated() {
        return last_updated;
    }

    /**
     * Imposta la data e l'ora dell'ultimo aggiornamento dei dati.
     *
     * @param last_updated La data e l'ora dell'ultimo aggiornamento.
     */
    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    /**
     * Restituisce la temperatura attuale in gradi Celsius.
     *
     * @return La temperatura attuale in gradi Celsius.
     */
    public double getTemp_c() {
        return temp_c;
    }

    /**
     * Imposta la temperatura attuale in gradi Celsius.
     *
     * @param temp_c La temperatura attuale in gradi Celsius.
     */
    public void setTemp_c(double temp_c) {
        this.temp_c = temp_c;
    }

    /**
     * Restituisce le condizioni meteorologiche attuali.
     *
     * @return Le condizioni meteorologiche attuali.
     */
    public Condition getCondition() {
        return condition;
    }

    /**
     * Imposta le condizioni meteorologiche attuali.
     *
     * @param condition Le condizioni meteorologiche attuali.
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    /**
     * Restituisce la precipitazione attuale in millimetri.
     *
     * @return La precipitazione attuale in millimetri.
     */
    public double getPrecip_mm() {
        return precip_mm;
    }

    /**
     * Imposta la precipitazione attuale in millimetri.
     *
     * @param precip_mm La precipitazione attuale in millimetri.
     */
    public void setPrecip_mm(double precip_mm) {
        this.precip_mm = precip_mm;
    }

    /**
     * Restituisce la precipitazione attuale in pollici.
     *
     * @return La precipitazione attuale in pollici.
     */
    public double getPrecip_in() {
        return precip_in;
    }

    /**
     * Imposta la precipitazione attuale in pollici.
     *
     * @param precip_in La precipitazione attuale in pollici.
     */
    public void setPrecip_in(double precip_in) {
        this.precip_in = precip_in;
    }

    /**
     * Restituisce l'umidità attuale in percentuale.
     *
     * @return L'umidità attuale in percentuale.
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * Imposta l'umidità attuale in percentuale.
     *
     * @param humidity L'umidità attuale in percentuale.
     */
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}

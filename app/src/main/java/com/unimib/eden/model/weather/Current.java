package com.unimib.eden.model.weather;

public class Current {
    private String last_updated;
    private double temp_c;
    private Condition condition;
    private double precip_mm;
    private double precip_in;
    private double humidity;

    public Current(String last_updated, double temp_c, Condition condition, double precip_mm, double precip_in, double humidity) {
        this.last_updated = last_updated;
        this.temp_c = temp_c;
        this.condition = condition;
        this.precip_mm = precip_mm;
        this.precip_in = precip_in;
        this.humidity = humidity;
    }

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

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public double getTemp_c() {
        return temp_c;
    }

    public void setTemp_c(double temp_c) {
        this.temp_c = temp_c;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public double getPrecip_mm() {
        return precip_mm;
    }

    public void setPrecip_mm(double precip_mm) {
        this.precip_mm = precip_mm;
    }

    public double getPrecip_in() {
        return precip_in;
    }

    public void setPrecip_in(double precip_in) {
        this.precip_in = precip_in;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
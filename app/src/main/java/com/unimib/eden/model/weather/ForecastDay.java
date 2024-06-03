package com.unimib.eden.model.weather;

/**
 * La classe ForecastDay rappresenta le previsioni meteo per un singolo giorno.
 * Include la data e un oggetto Day che contiene i dettagli delle condizioni meteo.
 */
public class ForecastDay {
    private String date;
    private Day day;

    /**
     * Costruttore della classe ForecastDay.
     *
     * @param date La data della previsione in formato String.
     * @param day  L'oggetto Day che contiene i dettagli delle condizioni meteo.
     */
    public ForecastDay(String date, Day day) {
        this.date = date;
        this.day = day;
    }

    /**
     * Restituisce la data della previsione.
     *
     * @return La data della previsione.
     */
    public String getDate() {
        return date;
    }

    /**
     * Restituisce i dettagli delle condizioni meteo per il giorno.
     *
     * @return L'oggetto Day con i dettagli delle condizioni meteo.
     */
    public Day getDay() {
        return day;
    }

    @Override
    public String toString() {
        return "\nForecastDay{" +
                "date='" + date + '\'' +
                "\nday=" + day.toString() +
                '}';
    }
}

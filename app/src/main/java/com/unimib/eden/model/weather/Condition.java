package com.unimib.eden.model.weather;

/**
 * La classe Condition rappresenta le condizioni meteorologiche.
 * Include informazioni testuali e un'icona che descrive le condizioni del meteo.
 */
public class Condition {
    private String text;
    private String icon;

    /**
     * Costruttore della classe Condition.
     *
     * @param text Descrizione testuale delle condizioni meteorologiche.
     * @param icon URL dell'icona che rappresenta le condizioni meteorologiche.
     */
    public Condition(String text, String icon) {
        this.text = text;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "text='" + text + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }

    /**
     * Restituisce la descrizione testuale delle condizioni meteorologiche.
     *
     * @return Una stringa che descrive le condizioni meteorologiche.
     */
    public String getText() {
        return text;
    }

    /**
     * Restituisce l'URL dell'icona che rappresenta le condizioni meteorologiche.
     *
     * @return Una stringa che contiene l'URL dell'icona.
     */
    public String getIcon() {
        return icon;
    }
}

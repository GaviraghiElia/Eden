package com.unimib.eden.model.weather;

import androidx.annotation.NonNull;

/**
 * The Condition class represents weather conditions.
 * It includes textual information and an icon describing the weather conditions.
 */
public class Condition {
    private final String text;
    private final String icon;

    /**
     * Constructor for the Condition class.
     *
     * @param text Description of the weather conditions.
     * @param icon URL of the icon representing the weather conditions.
     */
    public Condition(String text, String icon) {
        this.text = text;
        this.icon = icon;
    }

    @NonNull
    @Override
    public String toString() {
        return "Condition{" +
                "text='" + text + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }

    /**
     * Returns the textual description of the weather conditions.
     *
     * @return A string describing the weather conditions.
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the URL of the icon representing the weather conditions.
     *
     * @return A string containing the icon URL.
     */
    public String getIcon() {
        return icon;
    }
}

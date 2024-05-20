package com.unimib.eden.model.weather;

public class Condition {
    private String text;
    private String icon;

    @Override
    public String toString() {
        return "Condition{" +
                "text='" + text + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }

    public String getText() {
        return text;
    }

    public String getIcon() {
        return icon;
    }
}
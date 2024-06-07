package com.unimib.eden.utils;

/**
 * ConvertIntMonthToString class to convert a month expressed as an integer to its corresponding string.
 *
 */

public class ConvertIntMonthToString {

    public ConvertIntMonthToString() {}

    /**
     * Converts an integer representing a month to its corresponding name in Italian.
     *
     * @param month the integer representing the month (1 to 12)
     * @return the corresponding month name
     */

    public static String getMonth(int month) {
        return switch (month) {
            case 1 -> "Gennaio";
            case 2 -> "Febbraio";
            case 3 -> "Marzo";
            case 4 -> "Aprile";
            case 5 -> "Maggio";
            case 6 -> "Giugno";
            case 7 -> "Luglio";
            case 8 -> "Agosto";
            case 9 -> "Settembre";
            case 10 -> "Ottobre";
            case 11 -> "Novembre";
            case 12 -> "Dicembre";
            default -> "";
        };
    }
}

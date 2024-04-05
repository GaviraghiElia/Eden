package com.unimib.eden.utils;

import android.util.Log;

/**
 * Classe ConvertIntMonthToString per convertire un mese espresso sotto forma di intero con la corrispettiva stringa.
 *
 * @author Alice Hoa Galli
 */
public class ConvertIntMonthToString {

    public ConvertIntMonthToString() {}

    public static String getMese(int mese) {
        String nomeMese = "";
        switch (mese) {
            case 1:
                nomeMese = "Gennaio";
                break;
            case 2:
                nomeMese = "Febbraio";
                break;
            case 3:
                nomeMese = "Marzo";
                break;
            case 4:
                nomeMese = "Aprile";
                break;
            case 5:
                nomeMese = "Maggio";
                break;
            case 6:
                nomeMese = "Giugno";
                break;
            case 7:
                nomeMese = "Luglio";
                break;
            case 8:
                nomeMese = "Agosto";
                break;
            case 9:
                nomeMese = "Settembre";
                break;
            case 10:
                nomeMese = "Ottobre";
                break;
            case 11:
                nomeMese = "Novembre";
                break;
            case 12:
                nomeMese = "Dicembre";
                break;
        }
        return nomeMese;
    }
}

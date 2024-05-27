package com.unimib.eden.utils;

import android.content.Context;

import com.unimib.eden.R;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Pianta;

import java.util.Date;

/**
 * Classe di utilità per la trasformazione dei dati relativi alle date.
 */
public class Transformer {
    private static final String TAG = "Transformer";

    /**
     * Calcola il numero di giorni fino al prossimo innaffiamento per una coltura.
     *
     * @param coltura La coltura.
     * @return Il numero di giorni fino al prossimo innaffiamento.
     */
    public static int daysToProssimoInnaffiamento(Coltura coltura) {
        Date currentDate = new Date();
        Date ultimoInnaffiamento = coltura.getUltimoInnaffiamento();
        long timeDifference = currentDate.getTime() - ultimoInnaffiamento.getTime();
        long daysDifference = timeDifference / (1000 * 60 * 60 * 24);
        int faseAttuale = coltura.getFaseAttuale();
        int frequenzaInnaffiamento = coltura.getFrequenzaInnaffiamento().get(faseAttuale);
        int daysRemaining = frequenzaInnaffiamento - (int) daysDifference;
        return daysRemaining;
    }

    /**
     * Formatta la data del prossimo innaffiamento come una stringa.
     *
     * @param context Il contesto.
     * @param coltura La coltura.
     * @return La stringa formattata rappresentante la data del prossimo innaffiamento.
     */
    public static String formatProssimoInnaffiamento(Context context, Coltura coltura) {
        int days = daysToProssimoInnaffiamento(coltura);
        if (days == 0) {
            return context.getString(R.string.oggi);
        } else if (days == 1) {
            return context.getString(R.string.domani);
        } else if (days > 0) {
            return String.format(context.getString(R.string.tra_giorni), days);
        } else if (days == -1) {
            return String.format(context.getString(R.string.ritardo_giorno));
        } else {
            return String.format(context.getString(R.string.giorni_fa), Math.abs(days));
        }
    }
}

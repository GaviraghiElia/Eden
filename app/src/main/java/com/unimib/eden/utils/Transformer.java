package com.unimib.eden.utils;

import android.content.Context;

import com.unimib.eden.R;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Pianta;

import java.util.Date;

public class Transformer {
    private static final String TAG = "Transformer";

    public static int daysToProssimoInnaffiamento(Coltura coltura, Pianta pianta) {
        Date currentDate = new Date();
        Date ultimoInnaffiamento = coltura.getUltimoInnaffiamento();
        long timeDifference = currentDate.getTime() - ultimoInnaffiamento.getTime();
        long daysDifference = timeDifference / (1000 * 60 * 60 * 24);
        int frequenzaInnaffiamento = pianta.getFrequenzaInnaffiamento();
        int daysRemaining = frequenzaInnaffiamento - (int) daysDifference;
        return daysRemaining;
    }

    public static String formatProssimoInnaffiamento(Context context, Coltura coltura, Pianta pianta) {
        int days = daysToProssimoInnaffiamento(coltura, pianta);
        //Log.d(TAG, getString(R.string.oggi));
        if (days == 0) {
            return context.getString(R.string.oggi);
        } else if (days == 1) {
            return context.getString(R.string.domani);
        } else if (days > 0) {
            return String.format(context.getString(R.string.tra_giorni), days);
        } else if (days == -1) {
            return String.format(context.getString(R.string.ritardo_giorno), Math.abs(days));
        }
        else {
            return String.format(context.getString(R.string.giorni_fa), Math.abs(days));
        }
    }
}

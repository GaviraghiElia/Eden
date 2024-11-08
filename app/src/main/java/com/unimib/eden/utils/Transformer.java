package com.unimib.eden.utils;

import android.content.Context;

import com.unimib.eden.R;
import com.unimib.eden.model.Crop;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Utility class for transforming date-related data.
 */
public class Transformer {
    private static final String TAG = "Transformer";

    /**
     * Calculates the number of days until the next watering for a crop.
     *
     * @param crop The crop.
     * @return The number of days until the next watering.
     */
    public static int daysToNextWatering(Crop crop) {
        Date currentDate = new Date();
        Date lastWatering = crop.getLastWatering();
        long timeDifference = currentDate.getTime() - lastWatering.getTime();
        long daysDifference = timeDifference / (1000 * 60 * 60 * 24);
        int currentPhase = crop.getCurrentPhase();
        int wateringFrequency = crop.getWateringFrequency().get(currentPhase);
        return wateringFrequency - (int) daysDifference;
    }

    /**
     * Formats the next watering date as a string.
     *
     * @param context The context.
     * @param crop The crop.
     * @return The formatted string representing the next watering date.
     */
    public static String formatNextWatering(Context context, Crop crop) {
        int days = daysToNextWatering(crop);
        if (days == 0) {
            return context.getString(R.string.today);
        } else if (days == 1) {
            return context.getString(R.string.tomorrow);
        } else if (days > 0) {
            return String.format(context.getString(R.string.within_days), days);
        } else if (days == -1) {
            return String.format(context.getString(R.string.days_delay));
        } else {
            return String.format(context.getString(R.string.days_ago), Math.abs(days));
        }
    }

    /**
     * Returns a string representing the relationship of the provided date with the current date,
     * indicating if it is "today", "tomorrow", "day after tomorrow", or "other".
     *
     * @param dateString The string representing the date in "yyyy-MM-dd" format.
     * @return A string indicating if the date is "today", "tomorrow", "day after tomorrow", or "other".
     */
    public static String getRelativeDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(dateString));
            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTime(new Date());
            int daysDifference = calendar.get(Calendar.DAY_OF_YEAR) - calendarNow.get(Calendar.DAY_OF_YEAR);
            int yearDifference = calendar.get(Calendar.YEAR) - calendarNow.get(Calendar.YEAR);

            if (yearDifference == 0) {
                if (daysDifference == 0) {
                    return "Oggi";
                } else if (daysDifference == 1) {
                    return "Domani";
                } else if (daysDifference == 2) {
                    return "Dopodomani";
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "altro";
    }

}
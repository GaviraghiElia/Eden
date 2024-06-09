package com.unimib.eden.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unimib.eden.R;
import com.unimib.eden.model.weather.ForecastDay;
import com.unimib.eden.utils.Transformer;

import java.util.List;

/**
 * Adapter for displaying weather forecasts in a RecyclerView.
 * This adapter handles the interaction between the weather forecast data and the RecyclerView that displays it.
 */
public class ForecastDayAdapter extends RecyclerView.Adapter<ForecastDayAdapter.ForecastDayViewHolder> {

    private static final String TAG = "ForecastDayAdapter";
    private final List<ForecastDay> mForecastDayList;
    private final int layout;

    /**
     * Constructor for the adapter.
     *
     * @param forecastDayList List of forecasts to display.
     * @param layout Layout to use for each item in the RecyclerView.
     */
    public ForecastDayAdapter(List<ForecastDay> forecastDayList, int layout) {
        this.mForecastDayList = forecastDayList;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ForecastDayViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        return new ForecastDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastDayViewHolder forecastDayViewHolder, int i) {
        forecastDayViewHolder.bind(mForecastDayList.get(i));
    }

    @Override
    public int getItemCount() {
        return mForecastDayList != null ? mForecastDayList.size() : 0;
    }

    /**
     * Updates the list of forecasts and notifies the RecyclerView of data changes.
     *
     * @param forecastDayList The new list of forecasts to display.
     */
    public void update(List<ForecastDay> forecastDayList) {
        if (this.mForecastDayList != null) {
            this.mForecastDayList.clear();
            this.mForecastDayList.addAll(forecastDayList);
            notifyDataSetChanged();
        }
    }

    /**
     * ViewHolder for each item in the RecyclerView.
     */
    public static class ForecastDayViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewTemperature;
        private final TextView textViewChanceOfRain;
        private final TextView textViewTotalPrecipitations;
        private final TextView textViewHumidity;
        private final TextView textViewDay;
        private final ImageView imageViewWeather;

        public ForecastDayViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewDay = itemView.findViewById(R.id.textViewGiorno);
            this.textViewTemperature = itemView.findViewById(R.id.textViewTemperatura);
            this.textViewChanceOfRain = itemView.findViewById(R.id.textViewChanceOfRain);
            this.textViewTotalPrecipitations = itemView.findViewById(R.id.textViewTotalPrec);
            this.textViewHumidity = itemView.findViewById(R.id.textViewUmidita);
            this.imageViewWeather = itemView.findViewById(R.id.imageViewMeteo);
        }

        /**
         * Associates the forecast data with the ViewHolder.
         *
         * @param forecastDay The forecast to display.
         */
        public void bind(ForecastDay forecastDay) {
            String relativeDate = Transformer.getRelativeDate(forecastDay.getDate());
            this.textViewDay.setText(relativeDate);

            double chanceOfRain = forecastDay.getDay().getDaily_chance_of_rain();
            this.textViewChanceOfRain.setText(String.valueOf(chanceOfRain) + " %");

            double totalPrecipitations = forecastDay.getDay().getTotalprecip_mm();
            this.textViewTotalPrecipitations.setText(String.valueOf(totalPrecipitations) + " mm");

            double avgTemp = forecastDay.getDay().getAvgtemp_c();
            this.textViewTemperature.setText(String.valueOf(avgTemp) + " °C");

            double avgHumidity = forecastDay.getDay().getAvghumidity();
            this.textViewHumidity.setText(String.valueOf(avgHumidity) + " %");

            String imageURL = "https:" + forecastDay.getDay().getCondition().getIcon();
            Log.d(TAG, imageURL);
            Picasso.get().load(imageURL).into(imageViewWeather);

            Context context = itemView.getContext();
            int color;
            if (chanceOfRain > 70 && totalPrecipitations > 20) {
                color = ContextCompat.getColor(context, R.color.md_theme_blueSurface);
            } else if (avgTemp > 35 && avgHumidity < 50) {
                color = ContextCompat.getColor(context, R.color.md_theme_redContainer);
            } else {
                color = ContextCompat.getColor(context, R.color.md_theme_secondaryContainer);
            }
            this.itemView.setBackgroundColor(color);
        }
    }
}

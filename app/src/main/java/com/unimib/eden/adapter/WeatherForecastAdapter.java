package com.unimib.eden.adapter;

import android.app.Application;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unimib.eden.R;
import com.unimib.eden.model.weather.ForecastDay;
import com.unimib.eden.utils.Transformer;

import java.util.List;

/**
 * Adapter per la visualizzazione delle colture in una RecyclerView.
 * Questo adapter si occupa di gestire l'interfacciamento tra i dati delle colture e la RecyclerView che li visualizza.
 */
public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.ForecastDayViewHolder> {

    private static final String TAG = "WeatherForecastAdapter";
    private View view;

    /**
     * Interfaccia per la gestione dei click sugli elementi della RecyclerView.
     */


    private List<ForecastDay> mWeatherForecastList;
    private int layout;

    /**
     * Costruttore dell'adapter.
     *
     * @param weatherForecastList       Lista delle colture da visualizzare.
     * @param layout             Layout da utilizzare per ogni elemento della RecyclerView.
     * @param application        Oggetto Application per l'accesso al repository delle piante.
     */
    public WeatherForecastAdapter(List<ForecastDay> weatherForecastList, int layout, Application application) {
        this.mWeatherForecastList = weatherForecastList;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ForecastDayViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        return new ForecastDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastDayViewHolder weatherForecastViewHolder, int i) {
        weatherForecastViewHolder.bind(mWeatherForecastList.get(i));
    }

    @Override
    public int getItemCount() {
        if (mWeatherForecastList != null) {
            return mWeatherForecastList.size();
        }
        return 0;
    }

    public void update(List<ForecastDay> weatherForecastList) {
        if (this.mWeatherForecastList != null) {
            this.mWeatherForecastList.clear();
            this.mWeatherForecastList.addAll(weatherForecastList);
            notifyDataSetChanged();
        }
    }

    /**
     * ViewHolder per ogni elemento della RecyclerView.
     */
    public class ForecastDayViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewTemperatura;
        private final TextView textViewChanceOfRain;
        private final TextView textViewTotalPrec;
        private final TextView textViewUmidita;
        private final TextView textViewGiorno;
        private final ImageView imageViewMeteo;

        public ForecastDayViewHolder(@NonNull View itemView) {
            super(itemView);

            this.textViewGiorno = itemView.findViewById(R.id.textViewGiorno);
            this.textViewTemperatura = itemView.findViewById(R.id.textViewTemperatura);
            this.textViewChanceOfRain = itemView.findViewById(R.id.textViewChanceOfRain);
            this.textViewTotalPrec = itemView.findViewById(R.id.textViewTotalPrec);
            this.textViewUmidita = itemView.findViewById(R.id.textViewUmidita);
            this.imageViewMeteo = itemView.findViewById(R.id.imageViewMeteo2);

        }

        /**
         * Associa i dati della coltura al ViewHolder.
         *
         * @param weatherForecast La coltura da visualizzare.
         */
        public void bind(ForecastDay weatherForecast) {
            String relativeDate = Transformer.getRelativeDate(weatherForecast.getDate());
            this.textViewGiorno.setText(relativeDate);
            this.textViewChanceOfRain.setText(String.valueOf(weatherForecast.getDay().getDaily_chance_of_rain()) + " %");
            this.textViewTotalPrec.setText(String.valueOf(weatherForecast.getDay().getTotalprecip_mm()) + " mm");
            this.textViewTemperatura.setText(String.valueOf(weatherForecast.getDay().getAvgtemp_c()) + " °C");
            this.textViewUmidita.setText(String.valueOf(weatherForecast.getDay().getAvghumidity()) + " %");


            String imageURL = "https:" + weatherForecast.getDay().getCondition().getIcon();
            Log.d(TAG, imageURL);
            Picasso.get().load(imageURL).into(imageViewMeteo);
        }
    }
}

package com.unimib.eden.adapter;

import static androidx.test.InstrumentationRegistry.getContext;
import static com.google.common.io.Resources.getResource;

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
 * Adapter per la visualizzazione delle previsioni in una RecyclerView.
 * Questo adapter si occupa di gestire l'interfacciamento tra i dati delle previsioni meteo e la RecyclerView che li visualizza.
 */
public class ForecastDayAdapter extends RecyclerView.Adapter<ForecastDayAdapter.ForecastDayViewHolder> {

    private static final String TAG = "ForecastDayAdapter";
    private View view;
    private List<ForecastDay> mForecastDayList;
    private int layout;

    /**
     * Costruttore dell'adapter.
     *
     * @param forecastDayList Lista delle previsioni da visualizzare.
     * @param layout Layout da utilizzare per ogni elemento della RecyclerView.
     */
    public ForecastDayAdapter(List<ForecastDay> forecastDayList, int layout) {
        this.mForecastDayList = forecastDayList;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ForecastDayViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        return new ForecastDayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastDayViewHolder forecastDayViewHolder, int i) {
        forecastDayViewHolder.bind(mForecastDayList.get(i));
    }

    @Override
    public int getItemCount() {
        if (mForecastDayList != null) {
            return mForecastDayList.size();
        }
        return 0;
    }

    /**
     * Aggiorna la lista delle previsioni e notifica il cambiamento dei dati alla RecyclerView.
     *
     * @param forecastDayList La nuova lista delle previsioni da visualizzare.
     */
    public void update(List<ForecastDay> forecastDayList) {
        if (this.mForecastDayList != null) {
            this.mForecastDayList.clear();
            this.mForecastDayList.addAll(forecastDayList);
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
            this.imageViewMeteo = itemView.findViewById(R.id.imageViewMeteo);
        }

        /**
         * Associa i dati della previsione al ViewHolder.
         *
         * @param forecastDay La previsione da visualizzare.
         */
        public void bind(ForecastDay forecastDay) {
            String relativeDate = Transformer.getRelativeDate(forecastDay.getDate());
            this.textViewGiorno.setText(relativeDate);
            double chanceOfRain = forecastDay.getDay().getDaily_chance_of_rain();
            this.textViewChanceOfRain.setText(String.valueOf(chanceOfRain) + " %");
            double totalprecip = forecastDay.getDay().getTotalprecip_mm();
            this.textViewTotalPrec.setText(String.valueOf(totalprecip) + " mm");
            double avgTemp = forecastDay.getDay().getAvgtemp_c();
            this.textViewTemperatura.setText(String.valueOf(avgTemp) + " °C");
            double avgHumidity = forecastDay.getDay().getAvghumidity();
            this.textViewUmidita.setText(String.valueOf(avgHumidity) + " %");
            String imageURL = "https:" + forecastDay.getDay().getCondition().getIcon();
            Log.d(TAG, imageURL);
            Picasso.get().load(imageURL).into(imageViewMeteo);

            Context context = itemView.getContext();
            int color;
            if(chanceOfRain > 70 && totalprecip > 20) {
                color = ContextCompat.getColor(context, R.color.md_theme_blueSurface);
            }
            else if(avgTemp > 35 && avgHumidity < 50) {
                color = ContextCompat.getColor(context, R.color.md_theme_redContainer);
            }
            else {
                color = ContextCompat.getColor(context, R.color.md_theme_secondaryContainer);
            }
            this.itemView.setBackgroundColor(color);
        }
    }
}

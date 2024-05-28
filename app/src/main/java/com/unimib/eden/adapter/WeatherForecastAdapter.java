package com.unimib.eden.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimib.eden.R;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.repository.PiantaRepository;
import com.unimib.eden.utils.Converters;
import com.unimib.eden.utils.Transformer;

import java.util.List;

/**
 * Adapter per la visualizzazione delle colture in una RecyclerView.
 * Questo adapter si occupa di gestire l'interfacciamento tra i dati delle colture e la RecyclerView che li visualizza.
 */
public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastViewHolder> {

    private static final String TAG = "WeatherForecastAdapter";
    private View view;

    /**
     * Interfaccia per la gestione dei click sugli elementi della RecyclerView.
     */


    private List<WeatherForecast> mWeatherForecastList;

    //private PiantaRepository piantaRepository;
    private int layout;

    /**
     * Costruttore dell'adapter.
     *
     * @param weatherForecastList       Lista delle colture da visualizzare.
     * @param layout             Layout da utilizzare per ogni elemento della RecyclerView.
     * @param application        Oggetto Application per l'accesso al repository delle piante.
     */
    public WeatherForecastAdapter(List<WeatherForecast> weatherForecastList, int layout, Application application) {
        this.mWeatherForecastList = weatherForecastList;
        this.layout = layout;
        //piantaRepository = new PiantaRepository(application);
    }

    @NonNull
    @Override
    public WeatherForecastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        return new WeatherForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherForecastViewHolder weatherForecastViewHolder, int i) {
        weatherForecastViewHolder.bind(mWeatherForecastList.get(i));
    }

    @Override
    public int getItemCount() {
        if (mWeatherForecastList != null) {
            return mWeatherForecastList.size();
        }
        return 0;
    }

    public void update(List<WeatherForecast> weatherForecastList) {
        if (this.mWeatherForecastList != null) {
            this.mWeatherForecastList.clear();
            this.mWeatherForecastList.addAll(weatherForecastList);
            notifyDataSetChanged();
        }
    }

    /**
     * ViewHolder per ogni elemento della RecyclerView.
     */
    public class WeatherForecastViewHolder extends RecyclerView.ViewHolder {
        /*private final TextView textViewColturaPianta;
        private final TextView textViewGiorniInnaffiamento;
        private final TextView textViewDataInserimento;
        private final TextView textViewNote;

        private final CheckBox checkBox;*/
        private final TextView textViewProva;


        public WeatherForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            /*this.textViewColturaPianta = itemView.findViewById(R.id.textViewPianta);
            this.textViewGiorniInnaffiamento = itemView.findViewById(R.id.textViewDaysNumber);
            this.textViewDataInserimento = itemView.findViewById(R.id.textViewDate);
            this.textViewNote = itemView.findViewById(R.id.textViewNote);
            this.checkBox = itemView.findViewById(R.id.irrigazioniChecbox);*/
            this.textViewProva = itemView.findViewById(R.id.textViewProva);
        }

        /**
         * Associa i dati della coltura al ViewHolder.
         *
         * @param weatherForecast La coltura da visualizzare.
         */
        public void bind(WeatherForecast weatherForecast) {
            //this.textViewColturaPianta.setText(piantaRepository.getPiantaById(coltura.getIdPianta()).getNome());
            //this.textViewGiorniInnaffiamento.setText(Transformer.formatProssimoInnaffiamento(itemView.getContext(), coltura, piantaRepository.getPiantaById(coltura.getIdPianta())));
            /*this.textViewColturaPianta.setText(coltura.getNomePianta());

            this.textViewGiorniInnaffiamento.setText(Transformer.formatProssimoInnaffiamento(itemView.getContext(), coltura));
            if(Transformer.daysToProssimoInnaffiamento(coltura) >= 0){
                this.textViewGiorniInnaffiamento.setCompoundDrawablesWithIntrinsicBounds(R.drawable.garden_watering_can_24_ok, 0, 0, 0);
            }else{
                this.textViewGiorniInnaffiamento.setCompoundDrawablesWithIntrinsicBounds(R.drawable.garden_watering_can_24_delay, 0, 0, 0);
            }

            if (layout == R.layout.coltura_item) {
                this.textViewDataInserimento.setText(Converters.dateToString(coltura.getDataInserimento()));
                if (coltura.getNote().isEmpty()) {
                    this.textViewNote.setVisibility(View.GONE);
                } else {
                    this.textViewNote.setText(coltura.getNote());
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(coltura);
                    }
                });
            }
            if (layout == R.layout.irrigazioni_item)  {
                this.textViewDataInserimento.setText(Converters.dateToString(coltura.getUltimoInnaffiamento()));
                checkBox.setChecked(false);

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(coltura);
                    }
                });
            }*/

            this.textViewProva.setText("PROVA");

        }
    }
}

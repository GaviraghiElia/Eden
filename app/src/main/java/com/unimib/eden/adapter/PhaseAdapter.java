package com.unimib.eden.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimib.eden.R;
import com.unimib.eden.model.Fase;
import com.unimib.eden.utils.ConvertIntMonthToString;

import java.util.List;

/**
 * PhaseAdapter class that displays phase data in a RecyclerView.
 */
public class PhaseAdapter extends RecyclerView.Adapter<PhaseAdapter.PhaseViewHolder> {

    private int itemLayout = R.layout.fase_details_item;
    private final List<Fase> phasesList;

    /**
     * Constructor for the PhaseAdapter.
     *
     * @param phasesList List of phases to display.
     */
    public PhaseAdapter(List<Fase> phasesList) {
        this.phasesList = phasesList;
    }

    public PhaseAdapter(List<Fase> phasesList, int itemLayout) {
        this.phasesList = phasesList;
        this.itemLayout = itemLayout;
    }

    @NonNull
    @Override
    public PhaseAdapter.PhaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new PhaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhaseAdapter.PhaseViewHolder phaseViewHolder, @SuppressLint("RecyclerView") int position) {
        Fase phase = phasesList.get(position);
        phaseViewHolder.bind(phase, position);
    }

    @Override
    public int getItemCount() {
        if (phasesList != null) {
            return phasesList.size();
        }
        return 0;
    }

    public void update(List<Fase> fasiList) {
        if (this.phasesList != null) {
            this.phasesList.clear();
            this.phasesList.addAll(fasiList);
            notifyDataSetChanged();
        }
    }

    /**
     * PhaseViewHolder class for each item in the RecyclerView.
     */
    static class PhaseViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewPhasePlant;
        private final TextView getNamePhaseLabel;
        private final TextView namePhaseDetails;
        private final TextView phaseStartDetails;
        private final TextView phaseDurationDetails;
        private final TextView phaseDescriptionDetails;
        private final TextView wateringFrequencyPhaseDetails;

        public PhaseViewHolder(View view) {
            super(view);
            this.imageViewPhasePlant = view.findViewById(R.id.imageViewFasePianta);
            this.getNamePhaseLabel = view.findViewById(R.id.nomeFaseLabel);
            this.namePhaseDetails = view.findViewById(R.id.nomeFaseDetails);
            this.phaseStartDetails = view.findViewById(R.id.inizioFaseDetails);
            this.phaseDurationDetails = view.findViewById(R.id.durataFaseDetails);
            this.phaseDescriptionDetails = view.findViewById(R.id.descrizioneFaseDetails);
            this.wateringFrequencyPhaseDetails = view.findViewById(R.id.frequenzaInnaffiamentoFaseDetails);
        }

        /**
         * Bind method that associates phase data with the ViewHolder.
         *
         * @param phase The phase to display.
         * @param pos   The position of the phase in the list.
         */
        public void bind(Fase phase, int pos) {
            pos = pos + 1;
            this.getNamePhaseLabel.setText("Fase " + pos + ":");
            this.namePhaseDetails.setText(phase.getNomeFase());
            this.phaseStartDetails.setText(ConvertIntMonthToString.getMonth(phase.getInizioFase()));
            this.phaseDurationDetails.setText(String.valueOf(phase.getDurataFase()));
            this.phaseDescriptionDetails.setText(phase.getDescrizione());
            this.wateringFrequencyPhaseDetails.setText(String.valueOf(phase.getFrequenzaInnaffiamento()));

            String phaseName = phase.getNomeFase().toLowerCase().split(" ")[0];

            int resID = itemView.getContext().getResources()
                    .getIdentifier(phaseName, "drawable", itemView.getContext().getPackageName());

            if (resID != 0) { // If the image exists in the drawable
                this.imageViewPhasePlant.setImageResource(resID);
            } else {
                int fallbackResID = itemView.getContext().getResources().getIdentifier("fase_card_illustration", "drawable", itemView.getContext().getPackageName());
                this.imageViewPhasePlant.setImageResource(fallbackResID);
            }
        }
    }
}

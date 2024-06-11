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
import com.unimib.eden.model.Phase;
import com.unimib.eden.utils.ConvertIntMonthToString;

import java.util.List;

/**
 * PhaseAdapter class that displays phase data in a RecyclerView.
 */
public class PhaseAdapter extends RecyclerView.Adapter<PhaseAdapter.PhaseViewHolder> {

    private int itemLayout = R.layout.phase_details_item;
    private final List<Phase> phasesList;

    /**
     * Constructor for the PhaseAdapter.
     *
     * @param phasesList List of phases to display.
     */
    public PhaseAdapter(List<Phase> phasesList) {
        this.phasesList = phasesList;
    }

    public PhaseAdapter(List<Phase> phasesList, int itemLayout) {
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
        Phase phase = phasesList.get(position);
        phaseViewHolder.bind(phase, position);
    }

    @Override
    public int getItemCount() {
        if (phasesList != null) {
            return phasesList.size();
        }
        return 0;
    }

    public void update(List<Phase> phasesList) {
        if (this.phasesList != null) {
            this.phasesList.clear();
            this.phasesList.addAll(phasesList);
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
            this.imageViewPhasePlant = view.findViewById(R.id.imageViewPhasePlant);
            this.getNamePhaseLabel = view.findViewById(R.id.phaseNameLabel);
            this.namePhaseDetails = view.findViewById(R.id.phaseNameDetails);
            this.phaseStartDetails = view.findViewById(R.id.phaseStartDetails);
            this.phaseDurationDetails = view.findViewById(R.id.phaseDurationDetails);
            this.phaseDescriptionDetails = view.findViewById(R.id.phaseDescriptionDetails);
            this.wateringFrequencyPhaseDetails = view.findViewById(R.id.wateringFrequencyFaseDetails);
        }

        /**
         * Bind method that associates phase data with the ViewHolder.
         *
         * @param phase The phase to display.
         * @param pos   The position of the phase in the list.
         */
        public void bind(Phase phase, int pos) {
            pos = pos + 1;
            this.getNamePhaseLabel.setText("Fase " + pos + ":");
            this.namePhaseDetails.setText(phase.getPhaseName());
            this.phaseStartDetails.setText(ConvertIntMonthToString.getMonth(phase.getPhaseStart()));
            this.phaseDurationDetails.setText(String.valueOf(phase.getPhaseDuration()));
            this.phaseDescriptionDetails.setText(phase.getDescription());
            this.wateringFrequencyPhaseDetails.setText(String.valueOf(phase.getWateringFrequency()));

            String phaseName = phase.getPhaseName().toLowerCase().split(" ")[0];

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

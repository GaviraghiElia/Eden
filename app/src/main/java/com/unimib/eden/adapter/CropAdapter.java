package com.unimib.eden.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimib.eden.R;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.repository.PlantRepository;
import com.unimib.eden.utils.Converters;
import com.unimib.eden.utils.Transformer;

import java.util.List;

/**
 * Adapter for displaying crops in a RecyclerView.
 * This adapter manages the interface between crop data and the RecyclerView that displays it.
 */
public class CropAdapter extends RecyclerView.Adapter<CropAdapter.CropViewHolder> {

    private static final String TAG = "CropAdapter";
    private View view;

    /**
     * Interface for handling clicks on RecyclerView items.
     */
    public interface OnItemClickListener {
        void onItemClick(Coltura coltura);
    }

    private final List<Coltura> mCropsList;
    private final OnItemClickListener onItemClickListener;
    private final PlantRepository plantRepository;
    private final int layout;

    /**
     * Adapter constructor.
     *
     * @param cropsList       List of crops to display.
     * @param onItemClickListener Click handler for RecyclerView items.
     * @param layout             Layout to use for each RecyclerView item.
     * @param application        Application object for accessing the plant repository.
     */
    public CropAdapter(List<Coltura> cropsList, OnItemClickListener onItemClickListener, int layout, Application application) {
        this.mCropsList = cropsList;
        this.onItemClickListener = onItemClickListener;
        this.layout = layout;
        plantRepository = new PlantRepository(application);
    }

    @NonNull
    @Override
    public CropViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        return new CropViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CropViewHolder cropViewHolder, int i) {
        cropViewHolder.bind(mCropsList.get(i));
    }

    @Override
    public int getItemCount() {
        if (mCropsList != null) {
            return mCropsList.size();
        }
        return 0;
    }

    public void update(List<Coltura> cropsList) {
        if (this.mCropsList != null) {
            this.mCropsList.clear();
            this.mCropsList.addAll(cropsList);
            notifyDataSetChanged();
        }
    }

    /**
     * ViewHolder for each RecyclerView item.
     */
    public class CropViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewCrop;
        private final TextView textViewPlant;
        private final TextView textViewWateringDays;
        private final TextView textViewLastWatering;
        private final TextView textViewNote;
        private final CheckBox checkBox;

        public CropViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewCrop = itemView.findViewById(R.id.imageViewColtura);
            this.textViewPlant = itemView.findViewById(R.id.textViewPianta);
            this.textViewWateringDays = itemView.findViewById(R.id.textViewDaysNumber);
            this.textViewLastWatering = itemView.findViewById(R.id.textViewDate);
            this.textViewNote = itemView.findViewById(R.id.textViewNote);
            this.checkBox = itemView.findViewById(R.id.irrigazioniChecbox);
        }

        /**
         * Binds the crop data to the ViewHolder.
         *
         * @param crop The crop to display.
         */
        public void bind(Coltura crop) {
            this.textViewPlant.setText(crop.getNomePianta());

            this.textViewWateringDays.setText(Transformer.formatNextWatering(itemView.getContext(), crop));
            if (Transformer.daysToNextWatering(crop) >= 0) {
                this.textViewWateringDays.setCompoundDrawablesWithIntrinsicBounds(R.drawable.garden_watering_can_24_ok, 0, 0, 0);
            } else {
                this.textViewWateringDays.setCompoundDrawablesWithIntrinsicBounds(R.drawable.garden_watering_can_24_delay, 0, 0, 0);
            }

            if (layout == R.layout.crop_item) {
                // add imageViewPlant
                String plantName = crop.getNomePianta().toLowerCase();
                int resID = itemView.getContext().getResources().getIdentifier(plantName, "drawable", itemView.getContext().getPackageName());

                if (resID != 0) { // If the image exists in the drawable
                    this.imageViewCrop.setImageResource(resID);
                } else {
                    int fallbackResID = itemView.getContext().getResources().getIdentifier("note_illustration", "drawable", itemView.getContext().getPackageName());
                    this.imageViewCrop.setImageResource(fallbackResID);
                }

                this.textViewLastWatering.setText(Converters.dateToString(crop.getUltimoInnaffiamento()));
                if (crop.getNote().isEmpty()) {
                    this.textViewNote.setVisibility(View.GONE);
                } else {
                    this.textViewNote.setText(crop.getNote());
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(crop);
                    }
                });
            }
            if (layout == R.layout.irrigazioni_item) {
                // add imageViewPlant
                String plantName = crop.getNomePianta().toLowerCase();
                int resID = itemView.getContext().getResources().getIdentifier(plantName, "drawable", itemView.getContext().getPackageName());

                if (resID != 0) { // If the image exists in the drawable
                    this.imageViewCrop.setImageResource(resID);
                } else {
                    int fallbackResID = itemView.getContext().getResources().getIdentifier("note_illustration", "drawable", itemView.getContext().getPackageName());
                    this.imageViewCrop.setImageResource(fallbackResID);
                }

                this.textViewLastWatering.setText(Converters.dateToString(crop.getUltimoInnaffiamento()));
                checkBox.setChecked(false);

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(crop);
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkBox.setChecked(!checkBox.isChecked());
                        onItemClickListener.onItemClick(crop);
                    }
                });
            }
        }
    }
}

package com.unimib.eden.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimib.eden.R;
import com.unimib.eden.model.Plant;
import com.unimib.eden.ui.insertCrop.InsertCropActivity;
import com.unimib.eden.ui.insertProduct.InsertProductActivity;
import com.unimib.eden.ui.plantDetails.PlantDetailsActivity;
import com.unimib.eden.ui.searchPlant.SearchPlantActivity;
import com.unimib.eden.utils.Constants;

import java.util.List;

/**
 * PlantAdapter class that displays plant data in a RecyclerView.
 */
public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    private final static String TAG = "PlantAdapter";
    private final List<Plant> plantsList;
    private int itemLayout = R.layout.search_plant_item;

    /**
     * Integer to handle the correct destination following the onClick of a plant.
     */
    private int  operationCode;

    /**
     * Constructor for the PlantAdapter.
     *
     * @param plantsList List of plants to display.
     */
    public PlantAdapter(List<Plant> plantsList) {
        this.plantsList = plantsList;
    }

    public PlantAdapter(List<Plant> plantsList, int itemLayout) {
        this.plantsList = plantsList;
        this.itemLayout = itemLayout;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.search_plant_item, parent, false);

        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder plantViewHolder, @SuppressLint("RecyclerView") int position) {

        // Create an instance of the ChildItem
        // class for the given position

        Plant pianta = plantsList.get(position);
        plantViewHolder.bind(pianta);

        plantViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                switch (operationCode) {
                    case Constants.SEARCH_PLANT_OPERATION_CODE:
                        intent = new Intent(v.getContext(), PlantDetailsActivity.class);
                        intent.putExtra("pianta", plantsList.get(position));
                        intent.putExtra("operationCode", Constants.SEARCH_PLANT_OPERATION_CODE);
                        v.getContext().startActivity(intent);
                        break;
                    case Constants.CREATE_CROP_OPERATION_CODE:
                        intent = new Intent(v.getContext(), InsertCropActivity.class);
                        intent.putExtra("pianta", plantsList.get(position));
                        intent.putExtra("operationCode", Constants.CREATE_CROP_OPERATION_CODE);
                        ((SearchPlantActivity) v.getContext()).setResult(Activity.RESULT_OK, intent);
                        ((SearchPlantActivity) v.getContext()).finish();
                        break;
                    case Constants.CREATE_PRODUCT_OPERATION_CODE:
                        intent = new Intent(v.getContext(), InsertProductActivity.class);
                        intent.putExtra("pianta", plantsList.get(position));
                        intent.putExtra("operationCode", Constants.CREATE_PRODUCT_OPERATION_CODE);
                        ((SearchPlantActivity) v.getContext()).setResult(Activity.RESULT_OK, intent);
                        ((SearchPlantActivity) v.getContext()).finish();
                        break;
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        if (plantsList != null) {
            return plantsList.size();
        }
        return 0;
    }

    public void update(List<Plant> piantaList, int operationCode) {
        if (this.plantsList != null) {
            this.plantsList.clear();
            this.plantsList.addAll(piantaList);
            this.operationCode = operationCode;
            notifyDataSetChanged();
        }
    }

    /**
     * PlantViewHolder class for the ViewHolder of each item in the RecyclerView.
     */
    static class PlantViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewPlantName;
        private final ImageView imageViewPlant;

        public PlantViewHolder(View itemView) {
            super(itemView);
            this.textViewPlantName = itemView.findViewById(R.id.plantName);
            this.imageViewPlant = itemView.findViewById(R.id.imageViewPlantSearch);

        }

        /**
         * Bind method that associates plant data with the ViewHolder.
         * @param plant The plant to display.
         */
        public void bind(Plant plant) {
            this.textViewPlantName.setText(plant.getName());

            String plantName = plant.getName().toLowerCase();
            int resID = itemView.getContext().getResources().getIdentifier(plantName, "drawable", itemView.getContext().getPackageName());

            if(resID != 0) { // If the image exists in the drawable
                this.imageViewPlant.setImageResource(resID);
            } else {
                int fallbackResID = itemView.getContext().getResources().getIdentifier("note_illustration", "drawable", itemView.getContext().getPackageName());
                this.imageViewPlant.setImageResource(fallbackResID);
            }

        }
    }
}

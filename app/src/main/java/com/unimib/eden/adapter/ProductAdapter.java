package com.unimib.eden.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.unimib.eden.R;
import com.unimib.eden.model.Product;
import com.unimib.eden.repository.PhaseRepository;
import com.unimib.eden.repository.PlantRepository;
import com.unimib.eden.utils.Constants;

import java.util.List;
import java.util.Objects;

/**
 * Adapter for displaying products in a RecyclerView.
 * This adapter manages the interface between product data and the RecyclerView that displays it.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private static final String TAG = "ProductAdapter";
    private View view;
    private final PlantRepository plantRepository;
    private final PhaseRepository phaseRepository;


    private final List<Product> mProductsList;
    private final ProductAdapter.OnItemClickListener onItemClickListener;
    private final int layout;

    /**
     * Interface for handling clicks on RecyclerView items.
     */
    public interface OnItemClickListener {
        void onItemClick(Product prodotto);
    }

    /**
     * Constructor for the adapter.
     *
     * @param productsList       List of products to display.
     * @param onItemClickListener Click handler for RecyclerView items.
     * @param layout             Layout to use for each item in the RecyclerView.
     */
    public ProductAdapter(List<Product> productsList, ProductAdapter.OnItemClickListener onItemClickListener, int layout, Application application) {
        this.mProductsList = productsList;
        this.onItemClickListener = onItemClickListener;
        this.layout = layout;
        this.plantRepository = new PlantRepository(application);
        this.phaseRepository = new PhaseRepository(application);
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        return new ProductAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder productViewHolder, int i) {
        productViewHolder.bind(mProductsList.get(i));
    }

    @Override
    public int getItemCount() {
        if (mProductsList != null) {
            return mProductsList.size();
        }
        return 0;
    }

    public void update(List<Product> prodottiList) {
        if (this.mProductsList != null) {
            this.mProductsList.clear();
            this.mProductsList.addAll(prodottiList);
            notifyDataSetChanged();
        }
    }

    /**
     * ViewHolder for each item in the RecyclerView.
     */
    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewProductStand;
        private final TextView textViewPlantProduct;
        private final TextView textViewPhaseProduct;
        private final TextView textViewQuantityProduct;
        private final TextView textViewPriceProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewProductStand = itemView.findViewById(R.id.imageViewProductStand);
            this.textViewPlantProduct = itemView.findViewById(R.id.textViewPlantProduct);
            this.textViewPhaseProduct = itemView.findViewById(R.id.textViewPhaseProduct);
            this.textViewQuantityProduct = itemView.findViewById(R.id.textViewQuantityProduct);
            this.textViewPriceProduct = itemView.findViewById(R.id.textViewPriceProduct);
        }

        /**
         * Associates product data with the ViewHolder.
         *
         * @param product The product to display.
         */
        public void bind(Product product) {

            String namePlantProduct = plantRepository.getPlantById(product.getPlant()).getName();

            int resID = itemView.getContext().getResources()
                    .getIdentifier(
                            namePlantProduct.toLowerCase(),
                            "drawable",
                            itemView.getContext().getPackageName()
                    );

            if(resID != 0) { // If the image exists in the drawable
                this.imageViewProductStand.setImageResource(resID);
            } else {
                int fallbackResID = itemView.getContext().getResources().getIdentifier("fase_card_illustration", "drawable", itemView.getContext().getPackageName());
                this.imageViewProductStand.setImageResource(fallbackResID);
            }

            this.textViewPlantProduct.setText(namePlantProduct);
            this.textViewPhaseProduct.setText(phaseRepository.getPhaseById(product.getCurrentPhase()).getPhaseName());

            String unitMeasure;
            if (Objects.equals(product.getCurrentPhase(), Constants.LAST_PHASE)) {
                unitMeasure = " grams";
            } else {
                unitMeasure = " plants";
            }
            this.textViewQuantityProduct.setText(String.valueOf(product.getQuantity()) + unitMeasure);

            this.textViewPriceProduct.setText(String.format("%.2f", product.getPrice()) + " €");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(product);
                }
            });
        }
    }
}

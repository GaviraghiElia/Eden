package com.unimib.eden.ui.productDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.unimib.eden.R;
import com.unimib.eden.databinding.ActivityProductDetailsBinding;
import com.unimib.eden.model.Product;
import com.unimib.eden.ui.plantDetails.PlantDetailsActivity;
import com.unimib.eden.utils.Constants;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Activity ProductDetailsActivity that shows all details of a product.
 */
public class ProductDetailsActivity extends AppCompatActivity {

    private static final String TAG = "ProductDetailsActivity";

    private Product product;
    private ProductDetailsViewModel productDetailsViewModel;
    private ActivityProductDetailsBinding mBinding;

    /**
     * Default constructor for ProductDetailsActivity.
     */
    public ProductDetailsActivity() {}

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // Set the app bar
        mBinding.toolbarProductDetails.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        mBinding.toolbarProductDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Initialize ViewModel
        productDetailsViewModel = new ViewModelProvider(this).get(ProductDetailsViewModel.class);
        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("prodotto");
        productDetailsViewModel.initialize(product);

        // Set the title of the toolbar
        mBinding.toolbarProductDetails.setTitle(productDetailsViewModel.getPlantName(product));

        // Set product phase name
        try {
            mBinding.textViewPhaseFull.setText(productDetailsViewModel.getPhaseName(product));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Set quantity and unit of measure
        String unitMeasure = Objects.equals(product.getCurrentPhase(), Constants.LAST_PHASE) ? " grammi" : " piante";
        mBinding.textViewQuantitaProdottoFull.setText(String.valueOf(product.getQuantity()) + unitMeasure);

        // Set product price
        mBinding.textViewProductPriceFull.setText(String.format("%.2f", product.getPrice()) + " €");

        // Set exchange availability
        if (product.getExchangeAvailable()) {
            mBinding.textViewProductExchangeFull.setText(R.string.yes);
            mBinding.cardProductExchange.setCardBackgroundColor(getResources().getColor(R.color.md_theme_secondaryContainer));
        } else {
            mBinding.textViewProductExchangeFull.setText(R.string.no);
            mBinding.cardProductExchange.setCardBackgroundColor(getResources().getColor(R.color.md_theme_redContainer));
        }

        // Set additional product information
        if (product.getOtherInformation().isEmpty()) {
            mBinding.cardProdottoNote.setVisibility(View.GONE);
        } else {
            mBinding.textViewInformazioniProdottoFull.setText(product.getOtherInformation());
        }

        // Button click to view all details
        mBinding.buttonViewAllDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlantDetailsActivity.class);
                intent.putExtra("operationCode", Constants.PLANT_DETAILS_OPERATION_CODE);
                intent.putExtra("pianta", productDetailsViewModel.getPlant(product));
                startActivity(intent);
            }
        });
    }
}

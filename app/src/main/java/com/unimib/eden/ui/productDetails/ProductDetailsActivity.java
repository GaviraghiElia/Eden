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
import com.unimib.eden.model.Prodotto;
import com.unimib.eden.ui.plantDetails.PlantDetailsActivity;
import com.unimib.eden.utils.Constants;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Activity ProductDetailsActivity that shows all details of a product.
 */
public class ProductDetailsActivity extends AppCompatActivity {

    private static final String TAG = "ProductDetailsActivity";

    private Prodotto product;
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
        mBinding.toolbarProdottoDetails.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        mBinding.toolbarProdottoDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Initialize ViewModel
        productDetailsViewModel = new ViewModelProvider(this).get(ProductDetailsViewModel.class);
        Intent intent = getIntent();
        product = (Prodotto) intent.getSerializableExtra("prodotto");
        productDetailsViewModel.initialize(product);

        // Set the title of the toolbar
        mBinding.toolbarProdottoDetails.setTitle(productDetailsViewModel.getPlantName(product));

        // Set product phase name
        try {
            mBinding.textViewFaseFull.setText(productDetailsViewModel.getPhaseName(product));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Set quantity and unit of measure
        String unitMeasure = Objects.equals(product.getFaseAttuale(), Constants.LAST_PHASE) ? " grammi" : " piante";
        mBinding.textViewQuantitaProdottoFull.setText(String.valueOf(product.getQuantita()) + unitMeasure);

        // Set product price
        mBinding.textViewPrezzoProdottoFull.setText(String.format("%.2f", product.getPrezzo()) + " €");

        // Set exchange availability
        if (product.getScambioDisponibile()) {
            mBinding.textViewScambiProdottoFull.setText(R.string.si);
            mBinding.cardProdottoScambi.setCardBackgroundColor(getResources().getColor(R.color.md_theme_secondaryContainer));
        } else {
            mBinding.textViewScambiProdottoFull.setText(R.string.no);
            mBinding.cardProdottoScambi.setCardBackgroundColor(getResources().getColor(R.color.md_theme_redContainer));
        }

        // Set additional product information
        if (product.getAltreInformazioni().isEmpty()) {
            mBinding.cardProdottoNote.setVisibility(View.GONE);
        } else {
            mBinding.textViewInformazioniProdottoFull.setText(product.getAltreInformazioni());
        }

        // Button click to view all details
        mBinding.buttonViewAllDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlantDetailsActivity.class);
                intent.putExtra("operationCode", Constants.PIANTA_DETAILS_OPERATION_CODE);
                intent.putExtra("pianta", productDetailsViewModel.getPlant(product));
                startActivity(intent);
            }
        });
    }
}

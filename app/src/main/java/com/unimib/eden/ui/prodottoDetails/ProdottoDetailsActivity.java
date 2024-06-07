package com.unimib.eden.ui.prodottoDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.card.MaterialCardView;
import com.unimib.eden.R;
import com.unimib.eden.databinding.ActivityProductDetailsBinding;
import com.unimib.eden.model.Prodotto;
import com.unimib.eden.ui.piantaDetails.PiantaDetailsActivity;
import com.unimib.eden.utils.Constants;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class ProdottoDetailsActivity extends AppCompatActivity {

    private Prodotto prodotto;
    private ProdottoDetailsViewModel prodottoDetailsViewModel;
    private ActivityProductDetailsBinding mBinding;

    private static final String TAG = "ProdottoDetailsActivity";


    public ProdottoDetailsActivity() {}

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityProductDetailsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // settare l'app bar
        mBinding.toolbarProdottoDetails.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        mBinding.toolbarProdottoDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        prodottoDetailsViewModel = new ViewModelProvider(this).get(ProdottoDetailsViewModel.class);
        Intent intent = getIntent();
        prodotto = (Prodotto) intent.getSerializableExtra("prodotto");
        prodottoDetailsViewModel.initialize(prodotto);
        mBinding.toolbarProdottoDetails.setTitle(prodottoDetailsViewModel.getNomePianta(prodotto));
        try {
            mBinding.textViewFaseFull.setText(prodottoDetailsViewModel.getNomeFase(prodotto));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String unitMeasure = "";
        if (Objects.equals(prodotto.getFaseAttuale(), "yTgppWsyv9XsdmncYDoH")) {
            unitMeasure = " grammi";
        } else {
            unitMeasure = " piante";
        }
        mBinding.textViewQuantitaProdottoFull.setText(String.valueOf(prodotto.getQuantita()) + unitMeasure);

        mBinding.textViewPrezzoProdottoFull.setText(String.format("%.2f", prodotto.getPrezzo()) + " €");
        if (prodotto.getScambioDisponibile()) {
            mBinding.textViewScambiProdottoFull.setText(R.string.si);
            mBinding.cardProdottoScambi.setCardBackgroundColor(getResources().getColor(R.color.md_theme_secondaryContainer));
        }
        else {
            mBinding.textViewScambiProdottoFull.setText(R.string.no);
            mBinding.cardProdottoScambi.setCardBackgroundColor(getResources().getColor(R.color.md_theme_redContainer));
        }
        if (prodotto.getAltreInformazioni().isEmpty()) {
            mBinding.cardProdottoNote.setVisibility(View.GONE);
        } else {
            mBinding.textViewInformazioniProdottoFull.setText(prodotto.getAltreInformazioni());
        }

        mBinding.buttonViewAllDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PiantaDetailsActivity.class);
                intent.putExtra("operationCode", Constants.PIANTA_DETAILS_OPERATION_CODE);
                intent.putExtra("pianta", prodottoDetailsViewModel.getPianta(prodotto));
                startActivity(intent);
            }
        });

    }

}

package com.unimib.eden.ui.prodottoDetails;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.unimib.eden.R;
import com.unimib.eden.databinding.ActivityProdottoDetailsBinding;
import com.unimib.eden.model.Prodotto;
import com.unimib.eden.ui.piantaDetails.PiantaDetailsActivity;
import com.unimib.eden.utils.Constants;

import java.util.concurrent.ExecutionException;

public class ProdottoDetailsActivity extends AppCompatActivity {

    private Prodotto prodotto;
    private ProdottoDetailsViewModel prodottoDetailsViewModel;
    private ActivityProdottoDetailsBinding mBinding;

    private static final String TAG = "ProdottoDetailsActivity";


    public ProdottoDetailsActivity() {}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityProdottoDetailsBinding.inflate(getLayoutInflater());
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
        mBinding.textViewQuantitaProdottoFull.setText(String.valueOf(prodotto.getQuantita()));
        mBinding.textViewPrezzoProdottoFull.setText(String.valueOf(prodotto.getPrezzo()) + " €");
        if (!prodotto.getScambioDisponibile()) {
            mBinding.textViewScambiProdottoFull.setPaintFlags(mBinding.textViewScambiProdottoFull.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (prodotto.getAltreInformazioni().isEmpty()) {
            mBinding.textViewInformazioniProdottoFull.setVisibility(View.GONE);
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

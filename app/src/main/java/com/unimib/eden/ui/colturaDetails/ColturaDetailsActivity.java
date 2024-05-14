package com.unimib.eden.ui.colturaDetails;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.unimib.eden.R;
import com.unimib.eden.databinding.ActivityColturaDetailsBinding;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.ui.piantaDetails.PiantaDetailsActivity;
import com.unimib.eden.ui.searchPianta.SearchPiantaActivity;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.Converters;

import java.util.concurrent.ExecutionException;

public class ColturaDetailsActivity extends AppCompatActivity {

    private Coltura coltura;
    private ColturaDetailsViewModel colturaDetailsViewModel;
    private ActivityColturaDetailsBinding mBinding;

    private static final String TAG = "coltura_activity";


    public ColturaDetailsActivity() {}

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityColturaDetailsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        // settare l'app bar
        mBinding.toolbarColturaDetails.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24_white);
        mBinding.toolbarColturaDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    onBackPressed();
            }
        });

        colturaDetailsViewModel = new ViewModelProvider(this).get(ColturaDetailsViewModel.class);
        Log.d(TAG, "siamo prima dell'intent");
        Intent intent = getIntent();
        coltura = (Coltura) intent.getSerializableExtra("coltura");
        Log.d(TAG, "dopo il get della coltura");
        colturaDetailsViewModel.initialize(coltura);
        Log.d(TAG, "dopo initialize");
        mBinding.toolbarColturaDetails.setTitle(coltura.getNomePianta());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBinding.toolbarColturaDetails.setTitleTextColor(getColor(R.color.white));
        }
        mBinding.textViewDataInserimentoFull.setText(colturaDetailsViewModel.getProssimoInnaffiamento(this, coltura));
        mBinding.textViewDataInserimentoFull.setText(Converters.dateToString(coltura.getDataInserimento()));
        try{
            mBinding.textViewFaseAttualeFull.setText(colturaDetailsViewModel.getNomeFase(coltura));
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (coltura.getNote().isEmpty()) {
            mBinding.textViewNoteFull.setVisibility(View.GONE);
        } else {
            mBinding.textViewNoteFull.setText(coltura.getNote());
        }
        mBinding.textViewQuantityFull.setText(String.valueOf(coltura.getQuantita()));

        mBinding.buttonViewAllDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PiantaDetailsActivity.class);
                intent.putExtra("operationCode", Constants.PIANTA_DETAILS_OPERATION_CODE);
                intent.putExtra("pianta", colturaDetailsViewModel.getPianta(coltura));
                startActivity(intent);
            }
        });

    }

}

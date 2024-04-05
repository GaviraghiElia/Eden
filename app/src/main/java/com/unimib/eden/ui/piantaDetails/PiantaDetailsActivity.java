package com.unimib.eden.ui.piantaDetails;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.unimib.eden.R;
import com.unimib.eden.adapter.FaseAdapter;
import com.unimib.eden.adapter.PiantaAdapter;
import com.unimib.eden.databinding.ActivityPiantaDetailsBinding;
import com.unimib.eden.model.Fase;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.ui.searchPianta.SearchPiantaActivity;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.ConvertIntMonthToString;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PiantaDetailsActivity extends AppCompatActivity {

    private static final String TAG = "PiantaDetailsActivity";
    private ActivityPiantaDetailsBinding binding;
    private PiantaDetailsViewModel piantaDetailsViewModel;
    private FaseAdapter faseAdapter;
    private LiveData<Pianta> game = new LiveData<Pianta>() {};
    String idNotification = null;
    Pianta pianta = null;
    List<Fase> fasi = null;

    public PiantaDetailsActivity() {}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPiantaDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.topAppBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchPiantaActivity.class);
                intent.putExtra("operationCode", Constants.SEARCH_PIANTA_OPERATION_CODE);
                intent.setFlags(FLAG_ACTIVITY_NO_HISTORY);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

        piantaDetailsViewModel = new ViewModelProvider(this).get(PiantaDetailsViewModel.class);

        Intent intent = getIntent();
        pianta = (Pianta) intent.getSerializableExtra("pianta");

        binding.piantaNomeDetails.setText(pianta.getNome());
        binding.famigliaBotanicaDetails.setText(pianta.getFamigliaBotanica());
        binding.frequenzaInnaffiamentoDetails.setText(String.valueOf(pianta.getFrequenzaInnaffiamento()) + " al giorno");
        binding.minTemperaturaDetails.setText(String.valueOf(pianta.getMinTemperatura()) + "°");
        binding.maxTemperaturaDetails.setText(String.valueOf(pianta.getMaxTemperatura())+ "°");
        binding.spazioNecessarioDetails.setText(String.valueOf(pianta.getSpazioNecessario()) + " cm");
        binding.altezzaMaxPrevistaDetails.setText(String.valueOf(pianta.getAltezzaMaxPrevista()) + " cm");
        binding.tipoTerrenoDetails.setText(pianta.getTipoTerreno());
        binding.esposizioneSoleDetails.setText(pianta.getEsposizioneSole());
        binding.inizioSeminaDetails.setText(ConvertIntMonthToString.getMese(pianta.getInizioSemina()));
        binding.fineSeminaDetails.setText(ConvertIntMonthToString.getMese(pianta.getFineSemina()));
        binding.descriptionDetails.setText(pianta.getDescrizione());


        try {
            fasi = piantaDetailsViewModel.getFasiList(pianta.getFasi());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        binding.fasiTitleSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: button clicked");
                if (binding.fasiConstraintLayout.getVisibility() == binding.fasiConstraintLayout.VISIBLE) {
                    binding.fasiConstraintLayout.setVisibility(binding.fasiConstraintLayout.GONE);
                } else {
                    binding.fasiConstraintLayout.setVisibility(binding.fasiConstraintLayout.VISIBLE);
                }
            }
        });

        binding.fasiRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        faseAdapter = new FaseAdapter(new ArrayList<>());
        binding.fasiRecyclerView.setAdapter(faseAdapter);
        faseAdapter.update(fasi);

        Log.d(TAG, "onCreate: FASI_LIST " + fasi.get(0).getNomeFase());



    }
}

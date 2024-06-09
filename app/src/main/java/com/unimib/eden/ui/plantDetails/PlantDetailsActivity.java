package com.unimib.eden.ui.plantDetails;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.unimib.eden.R;
import com.unimib.eden.adapter.PhaseAdapter;
import com.unimib.eden.databinding.ActivityPiantaDetailsBinding;
import com.unimib.eden.model.Phase;
import com.unimib.eden.model.Plant;
import com.unimib.eden.ui.searchPlant.SearchPlantActivity;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.ConvertIntMonthToString;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * PlantDetailsActivity that displays all the data of a plant.
 */
public class PlantDetailsActivity extends AppCompatActivity {

    private static final String TAG = "PlantDetailsActivity";
    private ActivityPiantaDetailsBinding binding;
    private PlantDetailsViewModel plantDetailsViewModel;
    private PhaseAdapter phaseAdapter;

    private int operationCode;
    Plant plant = null;
    List<Phase> phases = null;

    /**
     * Constructor that returns an instance of the activity.
     */
    public PlantDetailsActivity() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPiantaDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent i = getIntent();
        operationCode = (int) i.getSerializableExtra("operationCode");

        binding.topAppBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (operationCode == Constants.SEARCH_PLANT_OPERATION_CODE) { // from plant search details
                    intent = new Intent(getApplicationContext(), SearchPlantActivity.class);
                    intent.putExtra("operationCode", Constants.SEARCH_PLANT_OPERATION_CODE);
                    intent.setFlags(FLAG_ACTIVITY_NO_HISTORY);
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
                } else { // from cultivation details
                    onBackPressed();
                }
            }
        });

        plantDetailsViewModel = new ViewModelProvider(this).get(PlantDetailsViewModel.class);

        Intent intent = getIntent();
        plant = (Plant) intent.getSerializableExtra("pianta");
        String plantName = plant.getName();

        binding.topAppBar.setTitle(plant.getName());

        // binding.piantaNomeDetails.setText(nomePianta);
        int resID = getResources().getIdentifier(plantName.toLowerCase(), "drawable", getPackageName());
        if(resID != 0) {
            binding.imageViewPiantaDeailts.setImageResource(getResources().getIdentifier(plant.getName().toLowerCase(), "drawable", getPackageName()));
        } else {
            binding.imageViewPiantaDeailts.setVisibility(View.GONE);
        }

        binding.famigliaBotanicaDetails.setText(plant.getBotanicalFamily());
        binding.minTemperaturaDetails.setText(String.valueOf(plant.getMinTemperature()) + "°");
        binding.maxTemperaturaDetails.setText(String.valueOf(plant.getMaxTemperature()) + "°");
        binding.spazioNecessarioDetails.setText(String.valueOf(plant.getRequiredSpace()) + " cm");
        binding.altezzaMaxPrevistaDetails.setText(String.valueOf(plant.getMaxExpectedHeight()) + " cm");
        binding.tipoTerrenoDetails.setText(plant.getSoilType());
        binding.esposizioneSoleDetails.setText(plant.getSunExposure());
        binding.inizioSeminaDetails.setText(ConvertIntMonthToString.getMonth(plant.getSowingStart()));
        binding.fineSeminaDetails.setText(ConvertIntMonthToString.getMonth(plant.getSowingEnd()));
        binding.descriptionDetails.setText(plant.getDescription());

        try {
            phases = plantDetailsViewModel.getPhasesList(plant.getPhases());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        binding.fasiTitleSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.linearLayoutFasiView.getVisibility() == binding.fasiRecyclerView.VISIBLE) {
                    binding.linearLayoutFasiView.setVisibility(binding.fasiRecyclerView.GONE);
                } else {
                    binding.linearLayoutFasiView.setVisibility(binding.fasiRecyclerView.VISIBLE);
                }
            }
        });

        binding.fasiRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        phaseAdapter = new PhaseAdapter(new ArrayList<>());
        binding.fasiRecyclerView.setAdapter(phaseAdapter);
        phaseAdapter.update(phases);
    }
}

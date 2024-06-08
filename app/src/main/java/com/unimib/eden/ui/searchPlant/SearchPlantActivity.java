package com.unimib.eden.ui.searchPlant;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.unimib.eden.R;
import com.unimib.eden.adapter.PiantaAdapter;
import com.unimib.eden.databinding.ActivitySearchPiantaBinding;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.ui.filterSearch.FilterSearchActivity;
import com.unimib.eden.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Activity that allows searching for plants in the database.
 */
public class SearchPlantActivity extends AppCompatActivity {
    private static final String TAG = "SearchPlantActivity";
    private ActivitySearchPiantaBinding binding;
    private SearchPlantViewModel searchPlantViewModel;
    private final Handler mHandler = new Handler();
    private PiantaAdapter plantAdapter;
    private int operationCode;
    private Map<String, String> filtersMap = new HashMap<>();
    private boolean hasFiltri = false;
    private LiveData<List<Pianta>> plantList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchPiantaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get the intent and check for filters
        Intent intent = getIntent();
        operationCode = (int) intent.getSerializableExtra("operationCode");
        if (intent.hasExtra("filtriMap")) {
            filtersMap = (HashMap<String, String>) intent.getSerializableExtra("filtriMap");
            hasFiltri = true;
        }

        // Set up the toolbar navigation
        binding.searchPiantaToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.searchPiantaToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasFiltri = false;
                filtersMap.clear();
                onBackPressed();
            }
        });

        // Set up the filter menu
        Menu menu = binding.searchPiantaToolbar.getMenu();
        menu.findItem(R.id.filter_pianta).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getApplicationContext(), FilterSearchActivity.class);
                intent.putExtra("operationCode", Constants.SEARCH_PLANT_OPERATION_CODE);
                if (!filtersMap.isEmpty()) {
                    intent.putExtra("filtriMap", (Serializable) filtersMap);
                }
                intent.setFlags(FLAG_ACTIVITY_NO_HISTORY | FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                return true;
            }
        });

        // Initialize ViewModel
        searchPlantViewModel = new ViewModelProvider(this).get(SearchPlantViewModel.class);

        // Show the progress bar while loading data
        binding.progressBarSearchPianta.setVisibility(View.VISIBLE);

        // Set up the RecyclerView with an adapter
        binding.searchPiantaRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        plantAdapter = new PiantaAdapter(new ArrayList<>());
        binding.searchPiantaRecyclerView.setAdapter(plantAdapter);

        // Observe the plant list LiveData from ViewModel
        if (hasFiltri) {
            searchPlantViewModel.getPlantsList(filtersMap).observe(this, new Observer<List<Pianta>>() {
                @Override
                public void onChanged(List<Pianta> plants) {
                    plantAdapter.update(plants, operationCode);
                    binding.progressBarSearchPianta.setVisibility(View.GONE);
                }
            });
        } else {
            searchPlantViewModel.getPlantsList().observe(this, new Observer<List<Pianta>>() {
                @Override
                public void onChanged(List<Pianta> plants) {
                    plantAdapter.update(plants, operationCode);
                    binding.progressBarSearchPianta.setVisibility(View.GONE);
                }
            });
        }

        // Set up search input listener
        binding.textInputSearchPianta.requestFocus();
        binding.textInputSearchPianta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No action needed here
            }

            @Override
            public void afterTextChanged(Editable s) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.progressBarSearchPianta.setVisibility(View.VISIBLE);
                        if (hasFiltri) {
                            searchPlantViewModel.searchPlant(s.toString(), filtersMap);
                        } else {
                            searchPlantViewModel.searchPlant(s.toString());
                        }
                    }
                }, Constants.API_SEARCH_DELAY);
            }
        });
    }
}

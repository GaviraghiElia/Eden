package com.unimib.eden.ui.filterSearch;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.unimib.eden.R;
import com.unimib.eden.databinding.ActivityFilterSearchBinding;
import com.unimib.eden.ui.searchPlant.SearchPlantActivity;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.NumberPickerDialog;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * FilterSearchActivity class to set search filters for plant searches.
 * Handles filtering by sowing start and end, type of sunlight exposure (partial shade, sunny, full sun), and watering frequency.
 */
public class FilterSearchActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {

    private static final String TAG = "FilterSearchActivity";
    private ActivityFilterSearchBinding binding;
    private FilterSearchViewModel filterSearchViewModel;

    Map<String, String> filtersMap = new HashMap<>();
    private boolean hasPreviousFiltri = false;

    private boolean hasSelectedZeroFiltri = false;
    String[] esposizioneSole = {"mezz'ombra", "soleggiato", "pieno sole"};
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFilterSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        filterSearchViewModel = new ViewModelProvider(this).get(FilterSearchViewModel.class);

        binding.searchPlantToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.searchPlantToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchPlantActivity.class);
                intent.putExtra("operationCode", Constants.SEARCH_PLANT_OPERATION_CODE);
                intent.setFlags(FLAG_ACTIVITY_NO_HISTORY | FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

        Intent intent2 = getIntent();
        if (intent2.hasExtra("filtriMap")) {
            filtersMap = (HashMap<String, String>) intent2.getSerializableExtra("filtriMap");
            hasPreviousFiltri = true;
        }

        if (hasPreviousFiltri) {
            if (filtersMap.get("esposizioneSole") != null) {
                binding.sunExposureAutoComplete.setText(filtersMap.get("esposizioneSole"));
            }
            if (filtersMap.get("inizioSemina") != null) {
                binding.textInputEditSowingStart.setText(filtersMap.get("inizioSemina"));
            }
            if (filtersMap.get("fineSemina") != null) {
                binding.textInputEditSowingEnd.setText(filtersMap.get("fineSemina"));
            }
        }

        adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, esposizioneSole);
        binding.sunExposureAutoComplete.setAdapter(adapter);

        // InizioSeminaNumberPicker
        binding.textInputEditSowingStart.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showNumberPicker(v, 0);
                binding.textInputEditSowingStart.clearFocus();
            }
        });

        // FineSeminaNumberPicker
        binding.textInputEditSowingEnd.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showNumberPicker(v, 1);
                binding.textInputEditSowingEnd.clearFocus();
            }
        });

        binding.confirmButton.setOnClickListener(view -> {
            if (binding.sunExposureAutoComplete.getText().toString().equals("") &&
                    binding.textInputEditSowingStart.getText().toString().equals("") &&
                    binding.textInputEditSowingEnd.getText().toString().equals("")) {
                hasSelectedZeroFiltri = true;
                new MaterialAlertDialogBuilder(FilterSearchActivity.this)
                        .setTitle(R.string.alert_dialog_no_filter_applied_title)
                        .setMessage(R.string.alert_dialog_no_filter_applied_message)
                        .setPositiveButton(R.string.alert_dialog_no_filter_applied_positive_button, (dialog, which) -> {
                            Intent intent = new Intent(getApplicationContext(), SearchPlantActivity.class);
                            intent.putExtra("operationCode", Constants.SEARCH_PLANT_OPERATION_CODE);
                            intent.setFlags(FLAG_ACTIVITY_NO_HISTORY | FLAG_ACTIVITY_NEW_TASK);
                            getApplicationContext().startActivity(intent);
                        })
                        .setNegativeButton(R.string.alert_dialog_no_filter_applied_negative_button, (dialog, which) -> {
                            //dialog.cancel();
                        }).show();
            } else {
                if (!binding.sunExposureAutoComplete.getText().toString().equals("")) {
                    filtersMap.put("esposizioneSole", binding.sunExposureAutoComplete.getText().toString());
                }
                if (!binding.textInputEditSowingStart.getText().toString().equals("")) {
                    filtersMap.put("inizioSemina", binding.textInputEditSowingStart.getText().toString());
                }
                if (!binding.textInputEditSowingEnd.getText().toString().equals("")) {
                    filtersMap.put("fineSemina", binding.textInputEditSowingEnd.getText().toString());
                }
                Intent intent = new Intent(getApplicationContext(), SearchPlantActivity.class);
                intent.putExtra("operationCode", Constants.SEARCH_PLANT_OPERATION_CODE);
                intent.putExtra("filtriMap", (Serializable) filtersMap);
                intent.setFlags(FLAG_ACTIVITY_NO_HISTORY | FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
    }

    /**
     * ShowNumberPicker method to correctly display the number picker for sowing start and end fields.
     *
     * @param view The selected view on which the strings of the field name must be correctly set.
     * @param sowingId The ID that determines whether the selected field is the sowing start or sowing end field.
     */
    public void showNumberPicker(View view, int sowingId) {
        NumberPickerDialog newFragment;

        if (sowingId == 0) {
            TextInputEditText fineSemina = this.findViewById(R.id.textInputEditSowingEnd);
            if (String.valueOf(fineSemina.getText()).equals("")) {
                newFragment = new NumberPickerDialog(1, 12, sowingId);
            } else {
                newFragment = new NumberPickerDialog(1, Integer.valueOf(String.valueOf(fineSemina.getText())), sowingId);
            }
        } else {
            TextInputEditText inizioSemina = this.findViewById(R.id.textInputEditSowingStart);
            if (String.valueOf(inizioSemina.getText()).equals("")) {
                newFragment = new NumberPickerDialog(1, 12, sowingId);
            } else {
                newFragment = new NumberPickerDialog(Integer.valueOf(String.valueOf(inizioSemina.getText())), 12, sowingId);
            }
        }
        if (newFragment != null) {
            newFragment.setValueChangeListener(this);
            newFragment.show(getSupportFragmentManager(), "time picker");
        }
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        // Implement the behavior when the value of the NumberPicker changes
    }
}

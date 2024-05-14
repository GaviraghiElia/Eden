package com.unimib.eden.ui.filterSearch;


import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.unimib.eden.ui.searchPianta.SearchPiantaActivity;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.NumberPickerDialog;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Activity FilterSearchActivity per impostare i filtri di ricerca da applicare durante la ricerca delle piante.
 *
 * @author Alice Hoa Galli
 */
public class FilterSearchActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    // filtra per inizio e fine semina, tipo di sole (mezz'ombra, soleggiato, pieno sole), frequenza innaffiamento

    private static final String TAG = "FilterSearchActivity";
    private ActivityFilterSearchBinding binding;
    private FilterSearchViewModel filterSearchViewModel;

    Map<String, String> filtriMap = new HashMap<>();
    private boolean hasPreviousFiltri = false;

    private boolean hasSelectedZeroFiltri = false;
    String[] esposizioneSole = {"mezz'ombra","soleggiato","pieno sole"};
    ArrayAdapter<String> adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFilterSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        filterSearchViewModel = new ViewModelProvider(this).get(FilterSearchViewModel.class);

        binding.searchPiantaToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.searchPiantaToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchPiantaActivity.class);
                intent.putExtra("operationCode", Constants.SEARCH_PIANTA_OPERATION_CODE);
                intent.setFlags(FLAG_ACTIVITY_NO_HISTORY);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

        Intent intent2 = getIntent();
        if (intent2.hasExtra("filtriMap")) {
            filtriMap = (HashMap<String, String>) intent2.getSerializableExtra("filtriMap");
            hasPreviousFiltri = true;
        }

        if(hasPreviousFiltri) {
            if(filtriMap.get("frequenzaInnaffiamento") != null) {
                binding.textInputFrequenzaInnaffiamento.setText(filtriMap.get("frequenzaInnaffiamento").toString());
            }
            if(filtriMap.get("esposizioneSole") != null) {
                binding.esposizioneSoleAutoComplete.setText(filtriMap.get("esposizioneSole").toString());
            }
            if(filtriMap.get("inizioSemina") != null) {
                binding.textInputEditInizioSemina.setText(filtriMap.get("inizioSemina").toString());
            }
            if(filtriMap.get("fineSemina") != null) {
                binding.textInputEditFineSemina.setText(filtriMap.get("fineSemina").toString());
            }

        }


        adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, esposizioneSole);
        binding.esposizioneSoleAutoComplete.setAdapter(adapter);
        //Log.d(TAG, "onCreate: ESPOSIZIONE_SOLE: " + binding.esposizioneSoleAutoComplete.getText());

        // InizioSeminaNumberPicker
        binding.textInputEditInizioSemina.setOnFocusChangeListener(((v, hasFocus) -> {
            if (hasFocus) {
                showNumberPicker(v, 0);
                binding.textInputEditInizioSemina.clearFocus();
            }
        }));

        // FineSeminaNumberPicker
        binding.textInputEditFineSemina.setOnFocusChangeListener(((v, hasFocus) -> {
            if (hasFocus) {
                showNumberPicker(v, 1);
                binding.textInputEditFineSemina.clearFocus();
            }
        }));

        binding.confirmButton.setOnClickListener(view -> {

            if (binding.textInputFrequenzaInnaffiamento.getText().toString().equals("") &&
                    binding.esposizioneSoleAutoComplete.getText().toString().equals("") &&
                    binding.textInputEditInizioSemina.getText().toString().equals("") &&
                    binding.textInputEditFineSemina.getText().toString().equals("")) {
                // noFilterSelected();
                hasSelectedZeroFiltri = true;
                new MaterialAlertDialogBuilder(FilterSearchActivity.this)
                        .setTitle(R.string.alert_dialog_no_filter_applied_title)
                        .setMessage(R.string.alert_dialog_no_filter_applied_message)
                        .setPositiveButton(R.string.alert_dialog_no_filter_applied_positive_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(), SearchPiantaActivity.class);
                                intent.putExtra("operationCode", Constants.SEARCH_PIANTA_OPERATION_CODE);
                                intent.setFlags(FLAG_ACTIVITY_NO_HISTORY);
                                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                                getApplicationContext().startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.alert_dialog_no_filter_applied_negative_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //dialog.cancel();
                            }
                        }).show();


            } else {
                if (!binding.textInputFrequenzaInnaffiamento.getText().toString().equals("")) {
                    filtriMap.put("frequenzaInnaffiamento", binding.textInputFrequenzaInnaffiamento.getText().toString());
                }
                if (!binding.esposizioneSoleAutoComplete.getText().toString().equals("")) {
                    filtriMap.put("esposizioneSole", binding.esposizioneSoleAutoComplete.getText().toString());
                }
                if (!binding.textInputEditInizioSemina.getText().toString().equals("")) {
                    filtriMap.put("inizioSemina", binding.textInputEditInizioSemina.getText().toString());
                }
                if (!binding.textInputEditFineSemina.getText().toString().equals("")) {
                    filtriMap.put("fineSemina", binding.textInputEditFineSemina.getText().toString());
                }
                Intent intent = new Intent(getApplicationContext(), SearchPiantaActivity.class);
                intent.putExtra("operationCode", Constants.SEARCH_PIANTA_OPERATION_CODE);
                intent.putExtra("filtriMap", (Serializable) filtriMap);
                intent.setFlags(FLAG_ACTIVITY_NO_HISTORY);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }

        });

    }

    public void noFilterSelected() {
        // Da capire perchè da activity leak
        /*
        new MaterialAlertDialogBuilder(FilterSearchActivity.this)
                .setTitle(R.string.alert_dialog_no_filter_applied_title)
                .setMessage(R.string.alert_dialog_no_filter_applied_message)
                .setPositiveButton(R.string.alert_dialog_no_filter_applied_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FilterSearchActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton(R.string.alert_dialog_no_filter_applied_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dialog.cancel();
                    }
                }).show();

         */

    }

    /**
     * Metodo showNumberPicker per mostrare correttamente il numberPicker per i campi di inizioSemina e fineSemina.
     *
     * @param view  La view selezionata sulla quale bisognerà impostare le stringhe del nome del campo correttamente.
     * @param idSemina  L'Id che determina se il campo selezionato è quello di inizioSemina o fineSemina.
     */
    public void showNumberPicker(View view, int idSemina) {
       NumberPickerDialog newFragment = null;

        // controllo se idSemina è 0, in questo caso imposto intervallo di default

        // se è 1, allora prendo il valore inserito in inizio semina e lo setto come lower bound per fine semina

        if (idSemina == 0) {
            TextInputEditText fineSemina = this.findViewById(R.id.textInputEditFineSemina);
            if (String.valueOf(fineSemina.getText()).equals("")) {
                newFragment = new NumberPickerDialog(1, 12, idSemina);
                newFragment.setValueChangeListener(this);
                newFragment.show(getSupportFragmentManager(), "time picker");
            } else {
                newFragment = new NumberPickerDialog(1, Integer.valueOf(String.valueOf(fineSemina.getText())), idSemina);
                newFragment.setValueChangeListener(this);
                newFragment.show(getSupportFragmentManager(), "time picker");
            }
        } else {
            TextInputEditText inizioSemina = this.findViewById(R.id.textInputEditInizioSemina);
            if (String.valueOf(inizioSemina.getText()).equals("")) {
                newFragment = new NumberPickerDialog(1, 12, idSemina);
                newFragment.setValueChangeListener(this);
                newFragment.show(getSupportFragmentManager(), "time picker");
            } else {
                newFragment = new NumberPickerDialog(Integer.valueOf(String.valueOf(inizioSemina.getText())), 12, idSemina);
                newFragment.setValueChangeListener(this);
                newFragment.show(getSupportFragmentManager(), "time picker");
            }
        }


    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

    }
}

package com.unimib.eden.ui.filterSearch;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.unimib.eden.R;
import com.unimib.eden.databinding.ActivityFilterSearchBinding;
import com.unimib.eden.utils.NumberPickerDialog;


public class FilterSearchActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener {
    // filtra per inizio e fine semina, tipo di sole (mezz'ombra, soleggiato, pieno sole), frequenza innaffiamento

    private static final String TAG = "FilterSearchActivity";
    private ActivityFilterSearchBinding binding;
    private FilterSearchViewModel filterSearchViewModel;
    String[] esposizioneSole = {"mezz'ombra","soleggiato","pieno sole"};
    ArrayAdapter<String> adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFilterSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        filterSearchViewModel = new ViewModelProvider(this).get(FilterSearchViewModel.class);


        adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, esposizioneSole);
        binding.esposizioneSoleAutoComplete.setAdapter(adapter);

        // MinNumberPicker
        binding.textInputEditInizioSemina.setOnFocusChangeListener(((v, hasFocus) -> {
            if (hasFocus) {
                showNumberPicker(v, 0);
                binding.textInputEditInizioSemina.clearFocus();
            }
        }));

        // MaxNumberPicker
        binding.textInputEditFineSemina.setOnFocusChangeListener(((v, hasFocus) -> {
            if (hasFocus) {
                showNumberPicker(v, 1);
                binding.textInputEditFineSemina.clearFocus();
            }
        }));

    }

    public String getMese(int mese) {
        String nomeMese = "";
        switch (mese) {
            case 1:
                nomeMese = "Gennaio";
                break;
            case 2:
                nomeMese = "Febbraio";
                break;
            case 3:
                nomeMese = "Marzo";
                break;
            case 4:
                nomeMese = "Aprile";
                break;
            case 5:
                nomeMese = "Maggio";
                break;
            case 6:
                nomeMese = "Giugno";
                break;
            case 7:
                nomeMese = "Luglio";
                break;
            case 8:
                nomeMese = "Agosto";
                break;
            case 9:
                nomeMese = "Settembre";
                break;
            case 10:
                nomeMese = "Ottobre";
                break;
            case 11:
                nomeMese = "Novembre";
                break;
            case 12:
                nomeMese = "Dicembre";
                break;
        }
        return nomeMese;
    }

    public void showNumberPicker(View view, int idSemina) {
       NumberPickerDialog newFragment = null;

        // controllo se idSemina è 0, in questo caso imposto intervallo di default

        // se è 1, allora prendo il valore inserito in inizio semina e lo setto come lower bound per fine semina

        if (idSemina == 0) {
            TextInputEditText fineSemina = this.findViewById(R.id.textInputEditFineSemina);
            Log.d(TAG, "showNumberPicker: FINE_SEMINA: " + fineSemina.getText());
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
            Log.d(TAG, "showNumberPicker: INIZIO_SEMINA: " + inizioSemina);
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

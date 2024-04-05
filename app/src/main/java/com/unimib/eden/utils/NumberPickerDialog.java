package com.unimib.eden.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.unimib.eden.R;

/**
 * Classe NumberPickerDialog per creare un Number Picker da mostrare nella FilterSearchActivity per i campi inizioSemina e fineSemia.
 *
 * @author Alice Hoa Galli
 */
public class NumberPickerDialog extends DialogFragment {
    private NumberPicker.OnValueChangeListener valueChangeListener;
    private int inizioSemina;
    private int fineSemina;
    private int idSemina;

    public NumberPickerDialog(int inizioSemina, int fineSemina, int idSemina) {
        this.inizioSemina = inizioSemina;
        this.fineSemina = fineSemina;
        this.idSemina = idSemina;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final NumberPicker numberPickerMax = new NumberPicker(getActivity());

        numberPickerMax.setMinValue(inizioSemina);
        numberPickerMax.setMaxValue(fineSemina);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (idSemina == 0) {
            builder.setTitle(R.string.choose_inizio_semina);
        } else {
            builder.setTitle(R.string.choose_fine_semina);
        }
        builder.setMessage(R.string.choose_number);

        numberPickerMax.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int pickedValueMax = numberPickerMax.getValue();
                TextInputEditText textInputEditText = null;
                if (idSemina == 0) { //setto il campo di inizio semina
                    textInputEditText = (TextInputEditText) getActivity().findViewById(R.id.textInputEditInizioSemina);
                } else { //setto il campo di fine semina
                    textInputEditText = (TextInputEditText) getActivity().findViewById(R.id.textInputEditFineSemina);
                }

                textInputEditText.setText(Integer.toString(pickedValueMax));

            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setView(numberPickerMax);
        return builder.create();
    }

    public NumberPicker.OnValueChangeListener getValueChangeListener() {
        return valueChangeListener;
    }

    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }
}

package com.unimib.eden.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.unimib.eden.R;

/**
 * A dialog fragment to create a Number Picker to be displayed in the FilterSearchActivity
 * for the fields sowingStart and sowingEnd.
 * <p>
 * This dialog allows the user to select a number within a specified range.
 * It is used to set the starting and ending sowing dates.
 */
public class NumberPickerDialog extends DialogFragment {
    private NumberPicker.OnValueChangeListener valueChangeListener;
    private final int sowingStart;
    private final int sowingEnd;
    private final int sowingId;

    /**
     * Constructs a new NumberPickerDialog with the specified sowing start, end, and identifier values.
     *
     * @param sowingStart the minimum value for the NumberPicker
     * @param sowingEnd the maximum value for the NumberPicker
     * @param sowingId an identifier to distinguish between start and end sowing dates
     */
    public NumberPickerDialog(int sowingStart, int sowingEnd, int sowingId) {
        this.sowingStart = sowingStart;
        this.sowingEnd = sowingEnd;
        this.sowingId = sowingId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final NumberPicker numberPickerMax = new NumberPicker(getActivity());

        numberPickerMax.setMinValue(sowingStart);
        numberPickerMax.setMaxValue(sowingEnd);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if (sowingId == 0) {
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
                TextInputEditText textInputEditText;
                if (sowingId == 0) { // Set the starting sowing date field
                    textInputEditText = (TextInputEditText) getActivity().findViewById(R.id.textInputEditSowingStart);
                } else { // Set the ending sowing date field
                    textInputEditText = (TextInputEditText) getActivity().findViewById(R.id.textInputEditSowingEnd);
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

    /**
     * Gets the value change listener for the NumberPicker.
     *
     * @return the value change listener
     */
    public NumberPicker.OnValueChangeListener getValueChangeListener() {
        return valueChangeListener;
    }

    /**
     * Sets the value change listener for the NumberPicker.
     *
     * @param valueChangeListener the value change listener to set
     */
    public void setValueChangeListener(NumberPicker.OnValueChangeListener valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }
}

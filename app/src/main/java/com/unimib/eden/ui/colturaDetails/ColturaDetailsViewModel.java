package com.unimib.eden.ui.colturaDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.model.Coltura;

public class ColturaDetailsViewModel extends AndroidViewModel {
    public ColturaDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    public void initialize(Coltura coltura) {
    }
}

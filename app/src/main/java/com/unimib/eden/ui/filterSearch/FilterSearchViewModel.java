package com.unimib.eden.ui.filterSearch;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.PiantaRepository;

import java.util.List;

public class FilterSearchViewModel extends AndroidViewModel {

    private static final String TAG = "FilterSearchViewModel";

    public FilterSearchViewModel(@NonNull Application application) {
        super(application);

    }

}

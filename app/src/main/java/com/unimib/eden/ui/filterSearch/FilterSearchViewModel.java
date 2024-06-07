package com.unimib.eden.ui.filterSearch;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

/**
 * FilterSearchViewModel class representing the ViewModel for the FilterSearchActivity.
 */
public class FilterSearchViewModel extends AndroidViewModel {

    private static final String TAG = "FilterSearchViewModel";

    /**
     * Constructor that creates an instance of FilterSearchViewModel.
     *
     * @param application The application context
     */
    public FilterSearchViewModel(@NonNull Application application) {
        super(application);
    }
}

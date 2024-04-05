package com.unimib.eden.ui.filterSearch;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.PiantaRepository;

import java.util.List;

/**
 * Classe FilterSearchViewModel che rappsenta i ViewModel per l'activity FilterSearchActvity.
 *
 * @author Alice Hoa Galli
 */
public class FilterSearchViewModel extends AndroidViewModel {

    private static final String TAG = "FilterSearchViewModel";

    /**
     * Costruttore che genera un'istanza del FilterSearchViewModel.
     *
     * @param application   Contesto dell'applicazione
     */
    public FilterSearchViewModel(@NonNull Application application) {
        super(application);

    }

}

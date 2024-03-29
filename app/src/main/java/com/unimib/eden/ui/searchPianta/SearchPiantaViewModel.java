package com.unimib.eden.ui.searchPianta;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.PiantaRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

public class SearchPiantaViewModel extends AndroidViewModel {
    private static final String TAG = "SearchPiantaViewModel";
    private PiantaRepository piantaRepository;
    private MutableLiveData<List<Pianta>> piantaListLiveData = new MutableLiveData<>();

    public SearchPiantaViewModel(@NonNull Application application) {
        super(application);
        piantaRepository = new PiantaRepository(application);
        piantaListLiveData = new MutableLiveData<>();;

    }

    public LiveData<List<Pianta>> getPiantaList() {
        if (piantaListLiveData.getValue() == null) {
            searchPianta("");
        }
        return piantaListLiveData;
    }

    public void searchPianta(String query) {
        try {
            piantaListLiveData.postValue(piantaRepository.SearchPiante(query));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

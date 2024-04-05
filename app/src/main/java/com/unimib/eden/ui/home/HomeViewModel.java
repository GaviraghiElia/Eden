package com.unimib.eden.ui.home;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.model.Fase;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.FaseRepository;
import com.unimib.eden.repository.PiantaRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private static final String TAG = "HomeViewModel";

    private List<Pianta> mPiante;

    private List<Fase> mFasi;

    private PiantaRepository piantaRepository;

    private FaseRepository faseRepository;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public HomeViewModel(Application application) {
        super(application);

        piantaRepository = new PiantaRepository(application);
        faseRepository = new FaseRepository(application);

        mPiante = piantaRepository.getAllPiante();
        mFasi = faseRepository.getAllFasi();
    }

    public List<Pianta> getPiante() { return mPiante;}

    public void updateDB() {
        piantaRepository.updateLocalDB();
        faseRepository.updateLocalDB();
    }
}

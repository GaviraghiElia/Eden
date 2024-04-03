package com.unimib.eden.ui.home;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.PiantaRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private static final String TAG = "HomeViewModel";

    private List<Pianta> mPiante;

    private PiantaRepository piantaRepository;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public HomeViewModel(Application application) {
        super(application);

        piantaRepository = new PiantaRepository(application);

        mPiante = piantaRepository.getAllPiante();
    }

    public List<Pianta> getPiante() { return mPiante;}

    public void updateDB() {
        piantaRepository.updateLocalDB();
    }
}

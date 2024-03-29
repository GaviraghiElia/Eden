package com.unimib.eden.ui.piantaDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.databinding.ActivityPiantaDetailsBinding;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.PiantaRepository;

import java.util.List;

public class PiantaDetailsViewModel extends AndroidViewModel {
    private static final String TAG = "MatchDetailsViewModel";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private PiantaRepository piantaRepository;

    public PiantaDetailsViewModel(@NonNull Application application) {
        super(application);
        piantaRepository = new PiantaRepository(application);
    }

}

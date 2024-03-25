package com.unimib.eden.repository;

import android.app.Application;

import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.database.PiantaDao;
import com.unimib.eden.database.PiantaRoomDatabase;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.utils.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

public class PiantaRepository implements IPiantaRepository {
    private static final String TAG = "MatchRepository";

    private final PiantaDao mPiantaDao;

    List<String> piante = new ArrayList<String>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Pianta> allPiante;

    public PiantaRepository(Application application) {
        PiantaRoomDatabase piantaRoomDatabase = ServiceLocator.getInstance().getPiantaDao(application);
        this.mPiantaDao = piantaRoomDatabase.piantaDao();
        allPiante = mPiantaDao.getAll();
    }

    @Override
    public List<Pianta> getAllPiante() {
        return allPiante;
    }
}

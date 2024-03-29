package com.unimib.eden.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unimib.eden.database.PiantaDao;
import com.unimib.eden.database.PiantaRoomDatabase;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.Converters;
import com.unimib.eden.utils.ServiceLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public void deletePianta(Pianta pianta) {

    }

    @Override
    public void insert(Pianta pianta) {

    }

    @Override
    public Pianta getPiantaById(String piantaId) {
        return mPiantaDao.getById(piantaId);
    }

    public void updateLocalDB() {
        Pianta newPianta = new Pianta("beVITqkLHWCerI1XLRxj", "Pomodoro", "Descrizione bla bla", "Latinum", 3, 4, 2, new ArrayList<>(), 1.0, "pieno sole","drenato",18, 24, 2);
        insert(newPianta);
    }

}

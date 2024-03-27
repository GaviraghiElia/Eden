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
import com.unimib.eden.database.ColturaDao;
import com.unimib.eden.database.ColturaRoomDatabase;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.ServiceLocator;

import java.util.List;

public class ColturaRepository implements IColturaRepository {
    private static final String TAG = "ColturaRepository";

    private final ColturaDao mColturaDao;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Coltura> allColture;

    public ColturaRepository(Application application) {
        ColturaRoomDatabase colturaRoomDatabase = ServiceLocator.getInstance().getColturaDao(application);
        this.mColturaDao = colturaRoomDatabase.colturaDao();
        allColture = mColturaDao.getAll();
    }

    @Override
    public List<Coltura> getAllColture() {
        return allColture;
    }

    @Override
    public void deleteColtura(Coltura coltura) {
        new DeleteColturaAsyncTask(mColturaDao).execute(coltura);
    }

    @Override
    public void insert(Coltura coltura) {
        new InsertColturaAsyncTask(mColturaDao).execute(coltura);
    }

    public void updateLocalDB() {
        if(allColture.isEmpty()) {
            Log.d(TAG, "Scaricamento colture personali...");
            db.collection(Constants.FIRESTORE_COLLECTION_COLTURE)
                    .whereEqualTo(Constants.COLTURA_PROPRIETARIO, "g.colombo147@campus.unimib.it") //TODO: currentUser
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    insert(new Coltura(document));
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        else {
            Log.d(TAG, "Colture già presenti nel db");
        }
    }

    private static class DeleteColturaAsyncTask extends AsyncTask<Coltura, Void, Void> {
        private ColturaDao colturaDao;

        private DeleteColturaAsyncTask(ColturaDao colturaDao) {
            this.colturaDao = colturaDao;
        }

        @Override
        protected Void doInBackground(Coltura... colture) {
            colturaDao.delete(colture[0]);
            return null;
        }
    }

    private static class InsertColturaAsyncTask extends AsyncTask<Coltura, Void, Void> {
        private ColturaDao mColturaDao;

        private InsertColturaAsyncTask(ColturaDao colturaDao) {
            this.mColturaDao = colturaDao;
        }

        @Override
        protected Void doInBackground(Coltura... colture) {
            mColturaDao.insert(colture[0]);
            return null;
        }
    }

}

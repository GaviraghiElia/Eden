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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ColturaRepository implements IColturaRepository {
    private static final String TAG = "ColturaRepository";

    private final ColturaDao mColturaDao;

    List<String> colture = new ArrayList<String>();

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

        db.collection(Constants.FIRESTORE_COLLECTION_COLTURE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                List<Coltura> tempColture = allColture;
                                boolean isColturaPresent = false;
                                boolean isColturaChanged = false;
                                Coltura oldColtura = null;
                                Coltura newColtura = null;
//                                Log.d(TAG, "onComplete: TEMPMATCH1 " + tempMatch.toString());
                                assert tempColture != null;
                                for (Coltura c : tempColture) {
                                    if (c.getId().equals(document.getId())) {
                                        isColturaPresent = true;
                                    }
                                    Log.d(TAG, "onComplete: ARRAYLIST " + (c.equals(document)));
                                    if (c.getId().equals(document.getId()) && c.equals(document)) {
                                        //m.hashCode() != document.getData().hashCode()
                                        //!m.equals(document.getData())
                                        oldColtura = c;
                                        isColturaChanged = true;
                                    }
                                    boolean colturaFireBaseDBNotPresent = false;
                                    for (QueryDocumentSnapshot d : task.getResult()) {
                                        if (c.getId().equals(d.getId())) {
                                            colturaFireBaseDBNotPresent = true;
                                        }
                                    }
                                    if (!colturaFireBaseDBNotPresent) {
                                        deleteColtura(c);
                                    }


                                }
                                if (!isColturaPresent) {
                                    Map<String, Object> tempMap = document.getData();
                                    //ArrayList<String> tmpListFasi = (ArrayList) document.getData().get(FIRESTORE_COLLECTION_COLTURE);

                                    Log.d(TAG, "onComplete: TEMP_MAP" + tempMap.toString());
                                    //Log.d(TAG, "onComplete: FASI " + tmpListFasi);
                                    newColtura = new Coltura(document);
                                    insert(newColtura);
                                }
                                if (isColturaChanged) {
                                    deleteColtura(oldColtura);
                                    Log.d(TAG, "onComplete: DATABASE_DATA " + getAllColture().toString());
                                    Map<String, Object> tempMap = document.getData();
                                    //ArrayList<String> tmpListFasi = (ArrayList) document.getData().get(FIRESTORE_COLLECTION_COLTURE);


                                    newColtura = new Coltura(document);
                                    insert(newColtura);


                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private static class DeleteColturaAsyncTask extends AsyncTask<Coltura, Void, Void> {
        private ColturaDao colturaDao;

        private DeleteColturaAsyncTask(ColturaDao colturaDao) {
            this.colturaDao = colturaDao;
        }

        @Override
        protected Void doInBackground(Coltura... matches) {
            colturaDao.delete(matches[0]);
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

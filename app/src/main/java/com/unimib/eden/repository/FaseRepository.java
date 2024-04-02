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
import com.unimib.eden.database.FaseDao;
import com.unimib.eden.database.FaseRoomDatabase;
import com.unimib.eden.model.Fase;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.ServiceLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FaseRepository implements IFaseRepository {

    private static final String TAG = "FaseRepository";

    private final FaseDao mFaseDao;

    List<String> fasi = new ArrayList<String>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Fase> allFasi;

    public FaseRepository(Application application) {
        FaseRoomDatabase faseRoomDatabase = ServiceLocator.getInstance().getFaseDao(application);
        this.mFaseDao = faseRoomDatabase.faseDao();
        allFasi = mFaseDao.getAll();
    }

    @Override
    public List<Fase> getAllFasi() {
        return allFasi;
    }

    @Override
    public void deleteFase(Fase fase) {
        new DeleteFaseAsyncTask(mFaseDao).execute(fase);
    }

    private static class DeleteFaseAsyncTask extends AsyncTask<Fase, Void, Void> {
        private FaseDao faseDao;

        private DeleteFaseAsyncTask(FaseDao faseDao) {
            this.faseDao = faseDao;
        }

        @Override
        protected Void doInBackground(Fase... fasi) {
            faseDao.delete(fasi[0]);
            return null;
        }

    }

    @Override
    public void insert(Fase fase) {
        new InsertFasehAsyncTask(mFaseDao).execute(fase);
    }

    private static class InsertFasehAsyncTask extends AsyncTask<Fase, Void, Void> {
        private FaseDao mFaseDao;

        private InsertFasehAsyncTask(FaseDao faseDao) {
            this. mFaseDao = faseDao;
        }

        @Override
        protected Void doInBackground(Fase... fasi) {
            mFaseDao.insert(fasi[0]);
            return null;
        }
    }

    public void updateLocalDB() {

        db.collection(Constants.FIRESTORE_COLLECTION_FASI)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                List<Fase> tempPiante = allFasi;
                                boolean isFasePresent = false;
                                boolean isFaseChanged = false;
                                Fase oldFase = null;
                                Fase newFase = null;
//                                Log.d(TAG, "onComplete: TEMPMATCH1 " + tempMatch.toString());
                                assert tempPiante != null;
                                for (Fase f : tempPiante) {
                                    if (f.getId().equals(document.getId())) {
                                        isFasePresent = true;
                                    }
                                    if (f.getId().equals(document.getId())) {
                                        //m.hashCode() != document.getData().hashCode()
                                        //!m.equals(document.getData())
                                        oldFase = f;
                                        isFaseChanged = true;
                                    }
                                    boolean faseFireBaseDBNotPresent = false;
                                    for (QueryDocumentSnapshot d : task.getResult()) {
                                        if (f.getId().equals(d.getId())) {
                                            faseFireBaseDBNotPresent = true;
                                        }
                                    }
                                    if (!faseFireBaseDBNotPresent) {
                                        deleteFase(f);
                                    }


                                }
                                if (!isFasePresent) {
                                    Map<String, Object> tempMap = document.getData();

                                    Log.d(TAG, "onComplete: FASE" + tempMap.toString());
                                    newFase = new Fase(
                                            document.getId(),
                                            String.valueOf(tempMap.get(Constants.FASE_NOME_FASE)),
                                            Integer.parseInt(String.valueOf(tempMap.get(Constants.FASE_INIZIO_FASE))),
                                            Integer.parseInt(String.valueOf(tempMap.get(Constants.FASE_DURATA_FASE))),
                                            String.valueOf(tempMap.get(Constants.FASE_DESCRIZIONE)),
                                            String.valueOf(tempMap.get(Constants.FASE_IMMAGINE)));
                                    insert(newFase);
                                }
                                if (isFaseChanged) {
                                    deleteFase(oldFase);
                                    Log.d(TAG, "onComplete: DATABASE_DATA " + getAllFasi().toString());
                                    Map<String, Object> tempMap = document.getData();
                                    ArrayList<String> tmpListFasi = (ArrayList) document.getData().get("fasi");

                                    newFase = new Fase(
                                            document.getId(),
                                            String.valueOf(tempMap.get(Constants.FASE_NOME_FASE)),
                                            Integer.parseInt(String.valueOf(tempMap.get(Constants.FASE_INIZIO_FASE))),
                                            Integer.parseInt(String.valueOf(tempMap.get(Constants.FASE_DURATA_FASE))),
                                            String.valueOf(tempMap.get(Constants.FASE_DESCRIZIONE)),
                                            String.valueOf(tempMap.get(Constants.FASE_IMMAGINE)));
                                    insert(newFase);


                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    @Override
    public List<Fase> getFasiID(List<String> ids) throws ExecutionException, InterruptedException {
        AsyncTask asyncTask = new GetFasiAsyncTask(mFaseDao).execute(ids);

        Log.d(TAG, "getFasiID: ASYNKTASK " + asyncTask.get().toString());
        return (List<Fase>) asyncTask.get();
    }

    private static class GetFasiAsyncTask extends AsyncTask<List<String>, Void, List<Fase>> {
        private FaseDao mFaseDao;

        private GetFasiAsyncTask(FaseDao faseDao) {
            this.mFaseDao = faseDao;
        }


        @Override
        protected List<Fase> doInBackground(List<String>... lists) {
            return mFaseDao.getFasiID(lists[0]);
        }

    }
}

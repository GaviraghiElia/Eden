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
import com.unimib.eden.utils.ServiceLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
        new DeleteMatchAsyncTask(mPiantaDao).execute(pianta);
    }

    @Override
    public void insert(Pianta pianta) {
        new InsertMatchAsyncTask(mPiantaDao).execute(pianta);
    }

    public void updateLocalDB() {

        db.collection(Constants.FIRESTORE_COLLECTION_PIANTE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                List<Pianta> tempPiante = allPiante;
                                boolean isPiantaPresent = false;
                                boolean isPiantaChanged = false;
                                Pianta oldPianta = null;
                                Pianta newPianta = null;
//                                Log.d(TAG, "onComplete: TEMPMATCH1 " + tempMatch.toString());
                                assert tempPiante != null;
                                for (Pianta p : tempPiante) {
                                    if (p.getId().equals(document.getId())) {
                                        isPiantaPresent = true;
                                    }
                                    Log.d(TAG, "onComplete: ARRAYLIST " + (p.getFasi() != (ArrayList) document.getData().get("fasi")));
                                    if (p.getId().equals(document.getId()) && p.getFasi() != (ArrayList) document.getData().get("fasi")) {
                                        //m.hashCode() != document.getData().hashCode()
                                        //!m.equals(document.getData())
                                        oldPianta = p;
                                        isPiantaChanged = true;
                                    }
                                    boolean piantaFireBaseDBNotPresent = false;
                                    for (QueryDocumentSnapshot d : task.getResult()) {
                                        if (p.getId().equals(d.getId())) {
                                            piantaFireBaseDBNotPresent = true;
                                        }
                                    }
                                    if (!piantaFireBaseDBNotPresent) {
                                        deletePianta(p);
                                    }


                                }
                                if (!isPiantaPresent) {
                                    Map<String, Object> tempMap = document.getData();
                                    ArrayList<String> tmpListFasi = (ArrayList) document.getData().get("fasi");

                                    Log.d(TAG, "onComplete: TEMP_MAP" + tempMap.toString());
                                    Log.d(TAG, "onComplete: FASI " + tmpListFasi );
                                    Log.d(TAG, "onComplete: INIZIO_SEMINA: " + tempMap.get("inizio_semina"));
                                    newPianta = new Pianta(
                                            document.getId(),
                                            String.valueOf(tempMap.get("nome")),
                                            String.valueOf(tempMap.get("descrizione")),
                                            String.valueOf(tempMap.get("famiglia_botanica")),
                                            Integer.parseInt(String.valueOf(tempMap.get("inizio_semina"))),
                                            Integer.parseInt(tempMap.get("fine_semina").toString()),
                                            Integer.parseInt(tempMap.get("frequenza_innaffiamento").toString()),
                                            tmpListFasi,
                                            Double.parseDouble(String.valueOf(tempMap.get("spazio_necessario"))),
                                            String.valueOf(tempMap.get("esposizione_sole")),
                                            String.valueOf(tempMap.get("tipo_terreno")),
                                            Integer.parseInt(tempMap.get("min_temperatura").toString()),
                                            Integer.parseInt(tempMap.get("max_temperatura").toString()),
                                            Double.parseDouble(String.valueOf(tempMap.get("altezza_max_prevista"))));
                                    insert(newPianta);
                                }
                                if (isPiantaChanged) {
                                    deletePianta(oldPianta);
                                    Log.d(TAG, "onComplete: DATABASE_DATA " + getAllPiante().toString());
                                    Map<String, Object> tempMap = document.getData();
                                    ArrayList<String> tmpListFasi = (ArrayList) document.getData().get("fasi");

                                    newPianta = new Pianta(
                                            document.getId(),
                                            String.valueOf(tempMap.get("nome")),
                                            String.valueOf(tempMap.get("descrizione")),
                                            String.valueOf(tempMap.get("famiglia_botanica")),
                                            Integer.parseInt(String.valueOf(tempMap.get("inizio_semina"))),
                                            Integer.parseInt(tempMap.get("fine_semina").toString()),
                                            Integer.parseInt(tempMap.get("frequenza_innaffiamento").toString()),
                                            tmpListFasi,
                                            Double.parseDouble(String.valueOf(tempMap.get("spazio_necessario"))),
                                            String.valueOf(tempMap.get("esposizione_sole")),
                                            String.valueOf(tempMap.get("tipo_terreno")),
                                            Integer.parseInt(tempMap.get("min_temperatura").toString()),
                                            Integer.parseInt(tempMap.get("max_temperatura").toString()),
                                            Double.parseDouble(String.valueOf(tempMap.get("altezza_max_prevista"))));
                                    insert(newPianta);


                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private static class DeleteMatchAsyncTask extends AsyncTask<Pianta, Void, Void> {
        private PiantaDao piantaDao;

        private DeleteMatchAsyncTask(PiantaDao piantaDao) {
            this.piantaDao = piantaDao;
        }

        @Override
        protected Void doInBackground(Pianta... matches) {
            piantaDao.delete(matches[0]);
            return null;
        }
    }

    private static class InsertMatchAsyncTask extends AsyncTask<Pianta, Void, Void> {
        private PiantaDao mPiantaDao;

        private InsertMatchAsyncTask(PiantaDao piantaDao) {
            this.mPiantaDao = piantaDao;
        }

        @Override
        protected Void doInBackground(Pianta... piante) {
            mPiantaDao.insert(piante[0]);
            return null;
        }
    }

    public List<Pianta> SearchPiante(String query) throws ExecutionException, InterruptedException {
        AsyncTask asyncTask = new SearchPianteAsyncTask(mPiantaDao).execute(query);

        return (List<Pianta>) asyncTask.get();
    }

    private static class SearchPianteAsyncTask extends AsyncTask<String, Void, List<Pianta>> {
        private PiantaDao piantaDao;

        private SearchPianteAsyncTask(PiantaDao piantaDao) {
            this.piantaDao = piantaDao;
        }

        @Override
        protected List<Pianta> doInBackground(String... strings) {
            return piantaDao.searchMatches(strings[0]);
        }
    }

}

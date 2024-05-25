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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Classe FaseRepository che rappresenta il repository per la gestione dell'entità Fase.
 * Fornisce le operazioni di accesso e di sincronizzazione dei dati con il Firestore Database di Firebase.
 *
 * @author Alice Hoa Galli
 */
public class FaseRepository implements IFaseRepository {

    private static final String TAG = "FaseRepository";

    private final FaseDao mFaseDao;

    List<String> fasi = new ArrayList<String>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Fase> allFasi;

    /**
     * Costruttore della classe che genera un'istanza di FaseRepository.
     *
     * @param application   Il contesto dell'applicazione
     */
    public FaseRepository(Application application) {
        FaseRoomDatabase faseRoomDatabase = ServiceLocator.getInstance().getFaseDao(application);
        this.mFaseDao = faseRoomDatabase.faseDao();
        allFasi = mFaseDao.getAll();
    }

    /**
     * Ottiene tutte le fasi.
     *
     * @return  Una lista di tutte le fasi.
     */
    @Override
    public List<Fase> getAllFasi() {
        return allFasi;
    }

    /**
     * Elimina l'istanza di Fase passata in input.
     *
     * @param fase  La fase da eliminare all'interno del database.
     */
    @Override
    public void deleteFase(Fase fase) {
        new DeleteFaseAsyncTask(mFaseDao).execute(fase);
    }

    /**
     * Classe DeleteFaseAsyncTask che esegue l'operazione di eliminazione di una fase in un AsyncTask.
     */
    private static class DeleteFaseAsyncTask extends AsyncTask<Fase, Void, Void> {
        private FaseDao faseDao;

        private DeleteFaseAsyncTask(FaseDao faseDao) {
            this.faseDao = faseDao;
        }

        /**
         * Metodo che esegue in background l'eliminazione della fase passata in input.
         *
         * @param fasi La fase da eliminare.
         * @return  null.
         */
        @Override
        protected Void doInBackground(Fase... fasi) {
            faseDao.delete(fasi[0]);
            return null;
        }

    }

    /**
     * Metodo che inserisce la fase all'interno del database.
     *
     * @param fase  La fase da inserire all'interno del database.
     */
    @Override
    public void insert(Fase fase) {
        new InsertFaseAsyncTask(mFaseDao).execute(fase);
    }

    /**
     * Classe InsertFaseAsyncTask  che esegue l'inserimento della fase nel database in un AsyncTask.
     */
    private static class InsertFaseAsyncTask extends AsyncTask<Fase, Void, Void> {
        private FaseDao mFaseDao;

        private InsertFaseAsyncTask(FaseDao faseDao) {
            this. mFaseDao = faseDao;
        }

        /**
         * Metodo che esegue l'inserimento della fase in background.
         * @param fasi  La fase da inserire all'interno del database.
         * @return null.
         */
        @Override
        protected Void doInBackground(Fase... fasi) {
            mFaseDao.insert(fasi[0]);
            return null;
        }
    }

    /**
     * Metodo updateLocalDB che esegue l'aggiornamento del Room database per allinearlo a quello di Firebase.
     * Prende tutte le fasi presenti su Firebase ed esegue un controllo se queste non sono presenti nel Room database e se queste sono state modificate rispetto alle istanze in locale.
     * Se una fase è già presente in locale allora questa non viene inserita.
     * Se una fase è stata modificata su Firebase rispetto al database locale allora viene aggiornata l'istanza locale con quella presente su Firebase.
     * Se una fase non è presente in locale allora questa viene inserita.
     */
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
                                            String.valueOf(tempMap.get(Constants.FASE_IMMAGINE)),
                                            Integer.parseInt(String.valueOf(tempMap.get(Constants.FASE_FREQUENZA_INNAFFIAMENTO))));
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
                                            String.valueOf(tempMap.get(Constants.FASE_IMMAGINE)),
                                            Integer.parseInt(String.valueOf(tempMap.get(Constants.FASE_FREQUENZA_INNAFFIAMENTO))));
                                    insert(newFase);


                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    /**
     * Metodo che ottiene tutte le fasi presenti nel database che hanno gli ID uguali a quelli passati in input.
     * @param ids Gli Id delle fasi che dovranno coincidere con quelli delle fasi restituite in output.
     * @return La lista di tutte le fasi presenti nel database che hanno gli ID uguali a quelli passati in input.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Override
    public ArrayList<Fase> getFasiID(List<String> ids) throws ExecutionException, InterruptedException {
        AsyncTask asyncTask = new GetFasiAsyncTask(mFaseDao).execute(ids);
        ArrayList<Fase> fasi = (ArrayList<Fase>) asyncTask.get();

        // Crea una mappa che associa ciascun id alla sua posizione nella lista ids
        Map<String, Integer> idToIndexMap = new HashMap<>();
        for (int i = 0; i < ids.size(); i++) {
            idToIndexMap.put(ids.get(i), i);
        }
        // Ordina la lista fasi utilizzando la mappa
        Collections.sort(fasi, new Comparator<Fase>() {
            @Override
            public int compare(Fase f1, Fase f2) {
                return Integer.compare(idToIndexMap.get(f1.getId()), idToIndexMap.get(f2.getId()));
            }
        });
        return fasi;
    }

    /**
     * Classe GetFasiAsyncTask che esegue il recupero di una lista di fasi sulla base degli Id in input in un Async Tassk.
     */
    private static class GetFasiAsyncTask extends AsyncTask<List<String>, Void, List<Fase>> {
        private FaseDao mFaseDao;

        private GetFasiAsyncTask(FaseDao faseDao) {
            this.mFaseDao = faseDao;
        }


        /**
         * Metodo che esegue la ricerca delle fasi per Id in background.
         *
         * @param lists Gli Id delle fasi che dovranno coincidere con quelli delle fasi restituite in output.
         * @return  La lista delle fasi i cui Id coincidono con quelli passati in input.
         */
        @Override
        protected List<Fase> doInBackground(List<String>... lists) {
            return mFaseDao.getFasiID(lists[0]);
        }

    }
}

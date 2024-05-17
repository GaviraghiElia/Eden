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

/**
 * Classe PiantaRepository che rappresenta il repository per la gestione dell'entità Pianta.
 * Fornisce le operazioni di accesso e di sincronizzazione dei dati con il Firestore Database di Firebase.
 *
 * @author Alice Hoa Galli
 */
public class PiantaRepository implements IPiantaRepository {
    private static final String TAG = "PiantaRepository";

    private final PiantaDao mPiantaDao;

    List<String> piante = new ArrayList<String>();

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Pianta> allPiante;

    /**
     * Costruttore della classe che genera un'istanza di PiantaRepository.
     *
     * @param application   Il contesto dell'applicazione
     */
    public PiantaRepository(Application application) {
        PiantaRoomDatabase piantaRoomDatabase = ServiceLocator.getInstance().getPiantaDao(application);
        this.mPiantaDao = piantaRoomDatabase.piantaDao();
        allPiante = mPiantaDao.getAll();
    }

    /**
     * Ottiene tutte le piante.
     *
     * @return  Una lista di tutte le piante.
     */
    @Override
    public List<Pianta> getAllPiante() {
        return allPiante;
    }

    /**
     * Elimina l'istanza di Pianta passata in input.
     *
     * @param pianta  La pianta da eliminare all'interno del database.
     */
    @Override
    public void deletePianta(Pianta pianta) {
        new DeletePiantaAsyncTask(mPiantaDao).execute(pianta);
    }

    /**
     * Classe DeletePiantaAsyncTask che esegue l'operazione di eliminazione di una pianta in un AsyncTask.
     */
    private static class DeletePiantaAsyncTask extends AsyncTask<Pianta, Void, Void> {
        private PiantaDao piantaDao;

        private DeletePiantaAsyncTask(PiantaDao piantaDao) {
            this.piantaDao = piantaDao;
        }

        /**
         * Metodo che esegue in background l'eliminazione della pianta passata in input.
         *
         * @param pianta La pianta da eliminare.
         * @return  null.
         */
        @Override
        protected Void doInBackground(Pianta... pianta) {
            piantaDao.delete(pianta[0]);
            return null;
        }
    }

    /**
     * Metodo che inserisce la pianta all'interno del database.
     *
     * @param pianta  La pianta da inserire all'interno del database.
     */
    @Override
    public void insert(Pianta pianta) {

        new InsertPiantaAsyncTask(mPiantaDao).execute(pianta);
    }

    /**
     * Metodo per ottenere una pianta dall'id dall'interno del database.
     *
     * @param piantaId  L'id della pianta da ottenere dal database.
     */
    @Override
    public Pianta getPiantaById(String piantaId) {
        return mPiantaDao.getById(piantaId);
    }

    /**
     * Classe InsertPiantaAsyncTask  che esegue l'inserimento della pianta nel database in un AsyncTask.
     */
    private static class InsertPiantaAsyncTask extends AsyncTask<Pianta, Void, Void> {
        private PiantaDao mPiantaDao;

        private InsertPiantaAsyncTask(PiantaDao piantaDao) {
            this.mPiantaDao = piantaDao;
        }

        /**
         * Metodo che esegue l'inserimento della pianta in background.
         * @param piante  La pianta da inserire all'interno del database.
         * @return null.
         */
        @Override
        protected Void doInBackground(Pianta... piante) {
            mPiantaDao.insert(piante[0]);
            return null;
        }
    }

    /**
     * Metodo updateLocalDB che esegue l'aggiornamento del Room database per allinearlo a quello di Firebase.
     *
     * Prende tutte le piante presenti su Firebase ed esegue un controllo se queste non sono presenti nel Room database e se queste sono state modificate rispetto alle istanze in locale.
     * Se una pianta è già presente in locale allora questa non viene inserita.
     * Se una pianta è stata modificata su Firebase rispetto al database locale allora viene aggiornata l'istanza locale con quella presente su Firebase.
     * Se una pianta non è presente in locale allora questa viene inserita.
     */
    public void updateLocalDB() {

        db.collection(Constants.FIRESTORE_COLLECTION_PIANTE)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Log.d(TAG, document.getId() + " => " + document.getData());
                                List<Pianta> tempPiante = allPiante;
                                boolean isPiantaPresent = false;
                                boolean isPiantaChanged = false;
                                Pianta oldPianta = null;
                                Pianta newPianta = null;
                                assert tempPiante != null;
                                for (Pianta p : tempPiante) {
                                    if (p.getId().equals(document.getId())) {
                                        isPiantaPresent = true;
                                    }
                                    //Log.d(TAG, "onComplete: ARRAYLIST " + (p.getFasi() != (ArrayList) document.getData().get("fasi")));
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
                                    /*
                                    Log.d(TAG, "onComplete: TEMP_MAP" + tempMap.toString());
                                    Log.d(TAG, "onComplete: FASI " + tmpListFasi );
                                    Log.d(TAG, "onComplete: INIZIO_SEMINA: " + tempMap.get("inizio_semina"));

                                     */
                                    newPianta = new Pianta(
                                            document.getId(),
                                            String.valueOf(tempMap.get("nome")),
                                            String.valueOf(tempMap.get("descrizione")),
                                            String.valueOf(tempMap.get("famiglia_botanica")),
                                            Integer.parseInt(String.valueOf(tempMap.get("inizio_semina"))),
                                            Integer.parseInt(tempMap.get("fine_semina").toString()),
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

    /**
     * Metodo che esegue la ricerca delle piante in base al nome passato in input.
     *
     * @param query Il nome della pianta da restituire in output.
     * @return  Una lista di piante il cui nome contiene come sottostringa la stringa passata in input.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Pianta> SearchPiante(String query) throws ExecutionException, InterruptedException {
        AsyncTask asyncTask = new SearchPianteAsyncTask(mPiantaDao).execute(query);

        return (List<Pianta>) asyncTask.get();
    }

    /**
     * Classe SearchPianteAsyncTask che si occupa di cercare in un Async Task le piante nel cui nome è presente come sottostringa la stringa passata in input.
     */
    private static class SearchPianteAsyncTask extends AsyncTask<String, Void, List<Pianta>> {
        private PiantaDao piantaDao;

        private SearchPianteAsyncTask(PiantaDao piantaDao) {
            this.piantaDao = piantaDao;
        }

        /**
         * Metodo che esegue la ricerca in background delle piante nel cui nome è presente come sottostringa la stringa passata in input.
         * @param strings   Il nome della pianta da restituire in output.
         * @return  Una lista di piante il cui nome contiene come sottostringa la stringa passata in input.
         */
        @Override
        protected List<Pianta> doInBackground(String... strings) {
            return piantaDao.searchPiante(strings[0]);
        }
    }

    /**
     * Metodo che esegue la ricerca delle piante in base al nome passato in input e ai filtri di ricerca impostati.
     *
     * @param query Il nome della pianta da restituire in output.
     * @param filtriMap I filtri di ricerca da applicare.
     * @return  Una lista di piante il cui nome contiene come sottostringa la stringa passata in input e che soddisfano i filtri di ricerca.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<Pianta> SearchPiante(String query, Map<String, String> filtriMap) throws ExecutionException, InterruptedException {
        String frequenzaInnaffiamento = "";
        String esposizioneSole = "";
        String inizioSemina = "";
        String fineSemina = "";

        if(filtriMap.get("frequenzaInnaffiamento") != null) {
            frequenzaInnaffiamento = filtriMap.get("frequenzaInnaffiamento");
        } else {
            frequenzaInnaffiamento = "0";
        }
        if(filtriMap.get("esposizioneSole") != null) {
            esposizioneSole = filtriMap.get("esposizioneSole");
        } else {
            esposizioneSole = "";
        }
        if(filtriMap.get("inizioSemina") != null) {
            inizioSemina = filtriMap.get("inizioSemina");
        } else {
            inizioSemina = "1";
        }
        if(filtriMap.get("fineSemina") != null) {
            fineSemina = filtriMap.get("fineSemina");
        } else {
            fineSemina = "12";
        }

        AsyncTask asyncTask = new SearchPianteFiltriAsyncTask(mPiantaDao)
                .execute(
                        query,
                        frequenzaInnaffiamento,
                        esposizioneSole,
                        inizioSemina,
                        fineSemina);

        return (List<Pianta>) asyncTask.get();
    }

    /**
     * Classe SearchPianteFiltriAsyncTask che si occupa di cercare in un Async Task le piante nel cui nome è presente come sottostringa la stringa passata in input e che soddisfano i fitri di ricerca impostati.
     */
    private static class SearchPianteFiltriAsyncTask extends AsyncTask<String, Void, List<Pianta>> {
        private PiantaDao piantaDao;
        private Map<String, String> filtriMap;

        private SearchPianteFiltriAsyncTask(PiantaDao piantaDao) {
            this.piantaDao = piantaDao;
        }

        /**
         * Metodo che esegue la ricerca in background delle piante nel cui nome è presente come sottostringa la stringa passata in input e che soddisfa i filtri impostati.
         *
         * @param strings   Il nome della pianta da restituire in output e i filtri di ricerca da applicare.
         * @return Una lista di piante nel cui nome è presente come sottostringa la stringa passata in input e che soddisfa i filtri impostati.
         */
        @Override
        protected List<Pianta> doInBackground(String... strings) {
            if (strings[2].equals("")) {
                return piantaDao.searchPianteFiltri(strings[0], Integer.parseInt(strings[3]), Integer.parseInt(strings[4]));
            } else {
                return piantaDao.searchPianteFiltriAll(strings[0], strings[2], Integer.parseInt(strings[3]), Integer.parseInt(strings[4]));
            }

        }
    }

}

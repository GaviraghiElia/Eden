package com.unimib.eden.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unimib.eden.database.PhaseDao;
import com.unimib.eden.database.PhaseRoomDatabase;
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
 * PhaseRepository class representing the repository for managing the Phase entity.
 * It provides operations for accessing and synchronizing data with the Firebase Firestore Database.
 */
public class PhaseRepository implements IPhaseRepository {

    private static final String TAG = "PhaseRepository";

    private final PhaseDao mPhaseDao;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final List<Fase> allPhases;

    /**
     * Constructor of the class that generates an instance of PhaseRepository.
     *
     * @param application The application context
     */
    public PhaseRepository(Application application) {
        PhaseRoomDatabase phaseRoomDatabase = ServiceLocator.getInstance().getPhaseDao(application);
        this.mPhaseDao = phaseRoomDatabase.phaseDao();
        allPhases = mPhaseDao.getAll();
    }

    /**
     * Retrieves all phases.
     *
     * @return A list of all phases.
     */
    @Override
    public List<Fase> getAllPhases() {
        return allPhases;
    }

    /**
     * Deletes the passed Phase instance.
     *
     * @param phase The phase to delete within the database.
     */
    @Override
    public void deletePhase(Fase phase) {
        new DeletePhaseAsyncTask(mPhaseDao).execute(phase);
    }

    /**
     * DeletePhaseAsyncTask class that performs the operation of deleting a phase in an AsyncTask.
     */
    private static class DeletePhaseAsyncTask extends AsyncTask<Fase, Void, Void> {
        private final PhaseDao phaseDao;

        private DeletePhaseAsyncTask(PhaseDao phaseDao) {
            this.phaseDao = phaseDao;
        }

        /**
         * Method that performs the background deletion of the passed phase.
         *
         * @param phases The phase to delete.
         * @return null.
         */
        @Override
        protected Void doInBackground(Fase... phases) {
            phaseDao.delete(phases[0]);
            return null;
        }

    }

    /**
     * Method that inserts the phase into the database.
     *
     * @param phase The phase to insert into the database.
     */
    @Override
    public void insert(Fase phase) {
        new InsertPhaseAsyncTask(mPhaseDao).execute(phase);
    }

    /**
     * InsertPhaseAsyncTask class that performs the insertion of the phase into the database in an AsyncTask.
     */
    private static class InsertPhaseAsyncTask extends AsyncTask<Fase, Void, Void> {
        private final PhaseDao mPhaseDao;

        private InsertPhaseAsyncTask(PhaseDao phaseDao) {
            this.mPhaseDao = phaseDao;
        }

        /**
         * Method that performs the insertion of the phase in the background.
         * @param phases The phase to insert into the database.
         * @return null.
         */
        @Override
        protected Void doInBackground(Fase... phases) {
            mPhaseDao.insert(phases[0]);
            return null;
        }
    }

    /**
     * Gets a Crop entity from the repository based on the specified ID.
     *
     * @param phaseId The ID of the Phase entity to look for in the repository.
     * @return The Phase entity corresponding to the specified ID, if present in the repository, otherwise null.
     */
    @Override
    public Fase getPhaseById(String phaseId) {
        return mPhaseDao.getById(phaseId);
    }

    /**
     * Method updateLocalDB that performs the update of the Room database to align it with Firebase's.
     * Retrieves all phases from Firebase and checks if they are not present in the Room database and if they have been modified compared to the local instances.
     * If a phase is already present locally, it is not inserted.
     * If a phase has been modified on Firebase compared to the local database, the local instance is updated with the one on Firebase.
     * If a phase is not present locally, it is inserted.
     */
    public void updateLocalDB() {

        db.collection(Constants.FIRESTORE_COLLECTION_PHASES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List<Fase> tempPhases = allPhases;
                                boolean isPhasePresent = false;
                                boolean isPhaseChanged = false;
                                Fase oldPhase = null;
                                Fase newPhase = null;
                                assert tempPhases != null;
                                for (Fase f : tempPhases) {
                                    if (f.getId().equals(document.getId())) {
                                        isPhasePresent = true;
                                    }
                                    if (f.getId().equals(document.getId())) {
                                        oldPhase = f;
                                        isPhaseChanged = true;
                                    }
                                    boolean phaseFireBaseDBNotPresent = false;
                                    for (QueryDocumentSnapshot d : task.getResult()) {
                                        if (f.getId().equals(d.getId())) {
                                            phaseFireBaseDBNotPresent = true;
                                        }
                                    }
                                    if (!phaseFireBaseDBNotPresent) {
                                        deletePhase(f);
                                    }


                                }
                                if (!isPhasePresent) {
                                    Map<String, Object> tempMap = document.getData();

                                    newPhase = new Fase(
                                            document.getId(),
                                            String.valueOf(tempMap.get(Constants.PHASE_NAME)),
                                            Integer.parseInt(String.valueOf(tempMap.get(Constants.PHASE_START))),
                                            Integer.parseInt(String.valueOf(tempMap.get(Constants.PHASE_DURATION))),
                                            String.valueOf(tempMap.get(Constants.PHASE_DESCRIPTION)),
                                            String.valueOf(tempMap.get(Constants.PHASE_IMAGE)),
                                            Integer.parseInt(String.valueOf(tempMap.get(Constants.PHASE_WATERING_FREQUENCY))));
                                    insert(newPhase);
                                }
                                if (isPhaseChanged) {
                                    deletePhase(oldPhase);
                                    Map<String, Object> tempMap = document.getData();
                                    ArrayList<String> tmpListPhases = (ArrayList) document.getData().get("fasi");

                                    newPhase = new Fase(
                                            document.getId(),
                                            String.valueOf(tempMap.get(Constants.PHASE_NAME)),
                                            Integer.parseInt(String.valueOf(tempMap.get(Constants.PHASE_START))),
                                            Integer.parseInt(String.valueOf(tempMap.get(Constants.PHASE_DURATION))),
                                            String.valueOf(tempMap.get(Constants.PHASE_DESCRIPTION)),
                                            String.valueOf(tempMap.get(Constants.PHASE_IMAGE)),
                                            Integer.parseInt(String.valueOf(tempMap.get(Constants.PHASE_WATERING_FREQUENCY))));
                                    insert(newPhase);


                                }
                            }
                        }
                    }
                });
    }

    /**
     * Method that retrieves all phases present in the database that have IDs matching those passed as input.
     * @param ids The IDs of the phases that should match those of the returned phases.
     * @return The list of all phases present in the database that have IDs matching those passed as input.
     */
    @Override
    public ArrayList<Fase> getPhasesFromIds(List<String> ids) throws ExecutionException, InterruptedException {
        AsyncTask asyncTask = new GetPhasesAsyncTask(mPhaseDao).execute(ids);
        ArrayList<Fase> phases = (ArrayList<Fase>) asyncTask.get();

        // Creates a map associating each ID with its position in the ids list
        Map<String, Integer> idToIndexMap = new HashMap<>();
        for (int i = 0; i < ids.size(); i++) {
            idToIndexMap.put(ids.get(i), i);
        }

        Collections.sort(phases, new Comparator<>() {
            @Override
            public int compare(Fase f1, Fase f2) {
                return Integer.compare(idToIndexMap.get(f1.getId()), idToIndexMap.get(f2.getId()));
            }
        });
        return phases;
    }

    /**
     * Class GetPhasesAsyncTask that performs the retrieval of a list of phases based on the input IDs in an AsyncTask.
     */
    private static class GetPhasesAsyncTask extends AsyncTask<List<String>, Void, List<Fase>> {
        private final PhaseDao mPhaseDao;

        private GetPhasesAsyncTask(PhaseDao phaseDao) {
            this.mPhaseDao = phaseDao;
        }


        /**
         * Method that performs the search for phases by ID in the background.
         *
         * @param list The IDs of the phases that should match those of the returned phases.
         * @return The list of phases whose IDs match those passed as input.
         */
        @Override
        protected List<Fase> doInBackground(List<String>... list) {
            return mPhaseDao.getPhasesIds(list[0]);
        }

    }

}

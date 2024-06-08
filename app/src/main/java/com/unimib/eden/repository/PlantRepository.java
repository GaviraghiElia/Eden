package com.unimib.eden.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unimib.eden.database.PlantDao;
import com.unimib.eden.database.PlantRoomDatabase;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.ServiceLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * PlantRepository class representing the repository for managing the Plant entity.
 * Provides operations for accessing and synchronizing data with the Firebase Firestore Database.
 */
public class PlantRepository implements IPlantRepository {
    private static final String TAG = "PlantRepository";

    private final PlantDao mPlantDao;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final List<Pianta> allPlants;

    /**
     * Constructor of the class that generates an instance of PlantRepository.
     *
     * @param application   The application context.
     */
    public PlantRepository(Application application) {
        PlantRoomDatabase plantRoomDatabase = ServiceLocator.getInstance().getPlantDao(application);
        this.mPlantDao = plantRoomDatabase.plantDao();
        allPlants = mPlantDao.getAll();
    }

    /**
     * Retrieves all plants.
     *
     * @return  A list of all plants.
     */
    @Override
    public List<Pianta> getAllPlants() {
        return allPlants;
    }

    /**
     * Deletes the input Plant instance.
     *
     * @param plant  The plant to delete from the database.
     */
    @Override
    public void deletePlant(Pianta plant) {
        new DeletePiantaAsyncTask(mPlantDao).execute(plant);
    }

    /**
     * DeletePlantAsyncTask class that performs the deletion operation of a plant in an AsyncTask.
     */
    private static class DeletePiantaAsyncTask extends AsyncTask<Pianta, Void, Void> {
        private final PlantDao plantDao;

        private DeletePiantaAsyncTask(PlantDao plantDao) {
            this.plantDao = plantDao;
        }

        /**
         * Method that executes the deletion of the input plant in the background.
         *
         * @param plants The plant to be deleted.
         * @return  null.
         */
        @Override
        protected Void doInBackground(Pianta... plants) {
            plantDao.delete(plants[0]);
            return null;
        }
    }

    /**
     * Method that inserts the plant into the database.
     *
     * @param plant  The plant to be inserted into the database.
     */
    @Override
    public void insert(Pianta plant) {

        new insertPlantAsyncTask(mPlantDao).execute(plant);
    }

    /**
     * Method to get a plant by its id from the database.
     *
     * @param plantId  The id of the plant to get from the database.
     */
    @Override
    public Pianta getPlantById(String plantId) {
        return mPlantDao.getById(plantId);
    }

    /**
     * InsertPlantAsyncTask class that performs the insertion of the plant into the database in an AsyncTask.
     */
    private static class insertPlantAsyncTask extends AsyncTask<Pianta, Void, Void> {
        private final PlantDao mPlantDao;

        private insertPlantAsyncTask(PlantDao plantDao) {
            this.mPlantDao = plantDao;
        }

        /**
         * Method that executes the insertion of the plant in the background.
         * @param plants  The plant to be inserted into the database.
         * @return null.
         */
        @Override
        protected Void doInBackground(Pianta... plants) {
            mPlantDao.insert(plants[0]);
            return null;
        }
    }

    /**
     * updateLocalDB method that performs the update of the Room database to align it with Firebase's.
     * <p>
     * Retrieves all plants from Firebase and checks if they are not present in the Room database and if they have been modified compared to the local instances.
     * If a plant is already present locally, it is not inserted.
     * If a plant has been modified on Firebase compared to the local database, the local instance is updated with the one on Firebase.
     * If a plant is not present locally, it is inserted.
     */
    public void updateLocalDB() {

        db.collection(Constants.FIRESTORE_COLLECTION_PLANTS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List<Pianta> tempPlants = allPlants;
                                boolean isPlantPresent = false;
                                boolean isPlantChanged = false;
                                Pianta oldPlant = null;
                                Pianta newPlant = null;
                                assert tempPlants != null;
                                for (Pianta p : tempPlants) {
                                    if (p.getId().equals(document.getId())) {
                                        isPlantPresent = true;
                                    }
                                    if (p.getId().equals(document.getId()) && p.getFasi() != (ArrayList) document.getData().get("fasi")) {
                                        oldPlant = p;
                                        isPlantChanged = true;
                                    }
                                    boolean plantFireBaseDBNotPresent = false;
                                    for (QueryDocumentSnapshot d : task.getResult()) {
                                        if (p.getId().equals(d.getId())) {
                                            plantFireBaseDBNotPresent = true;
                                        }
                                    }
                                    if (!plantFireBaseDBNotPresent) {
                                        deletePlant(p);
                                    }


                                }
                                if (!isPlantPresent) {
                                    Map<String, Object> tempMap = document.getData();
                                    ArrayList<String> tmpListPhases = (ArrayList) document.getData().get("fasi");
                                    newPlant = new Pianta(
                                            document.getId(),
                                            String.valueOf(tempMap.get("nome")),
                                            String.valueOf(tempMap.get("descrizione")),
                                            String.valueOf(tempMap.get("famiglia_botanica")),
                                            Integer.parseInt(String.valueOf(tempMap.get("inizio_semina"))),
                                            Integer.parseInt(tempMap.get("fine_semina").toString()),
                                            tmpListPhases,
                                            Double.parseDouble(String.valueOf(tempMap.get("spazio_necessario"))),
                                            String.valueOf(tempMap.get("esposizione_sole")),
                                            String.valueOf(tempMap.get("tipo_terreno")),
                                            Integer.parseInt(tempMap.get("min_temperatura").toString()),
                                            Integer.parseInt(tempMap.get("max_temperatura").toString()),
                                            Double.parseDouble(String.valueOf(tempMap.get("altezza_max_prevista"))));
                                    insert(newPlant);
                                }
                                if (isPlantChanged) {
                                    deletePlant(oldPlant);
                                    Map<String, Object> tempMap = document.getData();
                                    ArrayList<String> tmpListPhases = (ArrayList) document.getData().get("fasi");
                                    newPlant = new Pianta(
                                            document.getId(),
                                            String.valueOf(tempMap.get("nome")),
                                            String.valueOf(tempMap.get("descrizione")),
                                            String.valueOf(tempMap.get("famiglia_botanica")),
                                            Integer.parseInt(String.valueOf(tempMap.get("inizio_semina"))),
                                            Integer.parseInt(tempMap.get("fine_semina").toString()),
                                            tmpListPhases,
                                            Double.parseDouble(String.valueOf(tempMap.get("spazio_necessario"))),
                                            String.valueOf(tempMap.get("esposizione_sole")),
                                            String.valueOf(tempMap.get("tipo_terreno")),
                                            Integer.parseInt(tempMap.get("min_temperatura").toString()),
                                            Integer.parseInt(tempMap.get("max_temperatura").toString()),
                                            Double.parseDouble(String.valueOf(tempMap.get("altezza_max_prevista"))));
                                    insert(newPlant);


                                }
                            }
                        }
                    }
                });
    }

    /**
     * Method that performs the search for plants based on the input name.
     *
     * @param query The name of the plant to be returned as output.
     * @return  A list of plants whose name contains the input string as a substring.
     */
    public List<Pianta> searchPlants(String query) throws ExecutionException, InterruptedException {
        AsyncTask asyncTask = new SearchPlantsAsyncTask(mPlantDao).execute(query);

        return (List<Pianta>) asyncTask.get();
    }

    /**
     * SearchPlantsAsyncTask class responsible for searching plants in an AsyncTask where the plant name contains the input string as a substring.
     */
    private static class SearchPlantsAsyncTask extends AsyncTask<String, Void, List<Pianta>> {
        private final PlantDao plantsDao;

        private SearchPlantsAsyncTask(PlantDao plantDao) {
            this.plantsDao = plantDao;
        }

        /**
         * Method that performs the background search for plants whose name contains the input string as a substring.
         * @param strings   The name of the plant to be returned as output.
         * @return  A list of plants whose name contains the input string as a substring.
         */
        @Override
        protected List<Pianta> doInBackground(String... strings) {
            return plantsDao.searchPlants(strings[0]);
        }
    }

    /**
     * Method that performs the search for plants based on the input name and the set search filters.
     *
     * @param query The name of the plant to be returned as output.
     * @param filtersMap The search filters to apply.
     * @return  A list of plants whose name contains the input string as a substring and that satisfy the search filters.
     */
    public List<Pianta> searchPlants(String query, Map<String, String> filtersMap) throws ExecutionException, InterruptedException {
        String wateringFrequency = "";
        String sunExposure = "";
        String sowingStart = "";
        String sowingEnd = "";

        if(filtersMap.get("frequenzaInnaffiamento") != null) {
            wateringFrequency = filtersMap.get("frequenzaInnaffiamento");
        } else {
            wateringFrequency = "0";
        }
        if(filtersMap.get("esposizioneSole") != null) {
            sunExposure = filtersMap.get("esposizioneSole");
        } else {
            sunExposure = "";
        }
        if(filtersMap.get("inizioSemina") != null) {
            sowingStart = filtersMap.get("inizioSemina");
        } else {
            sowingStart = "1";
        }
        if(filtersMap.get("fineSemina") != null) {
            sowingEnd = filtersMap.get("fineSemina");
        } else {
            sowingEnd = "12";
        }

        AsyncTask asyncTask = new SearchPlantsFiltersAsyncTask(mPlantDao)
                .execute(
                        query,
                        wateringFrequency,
                        sunExposure,
                        sowingStart,
                        sowingEnd);

        return (List<Pianta>) asyncTask.get();
    }

    /**
     * SearchPlantsFiltersAsyncTask class responsible for searching plants in an AsyncTask where the plant name contains the input string as a substring and satisfies the set search filters.
     */
    private static class SearchPlantsFiltersAsyncTask extends AsyncTask<String, Void, List<Pianta>> {
        private final PlantDao plantDao;

        private SearchPlantsFiltersAsyncTask(PlantDao plantDao) {
            this.plantDao = plantDao;
        }

        /**
         * Method that performs the background search for plants whose name contains the input string as a substring and satisfies the set filters.
         *
         * @param strings   The name of the plant to be returned as output and the search filters to apply.
         * @return A list of plants whose name contains the input string as a substring and satisfies the set filters.
         */
        @Override
        protected List<Pianta> doInBackground(String... strings) {
            if (strings[2].equals("")) {
                return plantDao.searchPlantsFilters(strings[0], Integer.parseInt(strings[3]), Integer.parseInt(strings[4]));
            } else {
                return plantDao.searchPlantsAllFilters(strings[0], strings[2], Integer.parseInt(strings[3]), Integer.parseInt(strings[4]));
            }

        }
    }

}

package com.unimib.eden.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unimib.eden.database.CropDao;
import com.unimib.eden.database.CropRoomDatabase;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.ServiceLocator;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Repository class for managing Crops entities, providing data access operations and synchronization with Firestore.
 */
public class CropRepository implements ICropRepository {
    private static final String TAG = "CropRepository";
    private final CropDao mCropDao;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final LiveData<List<Coltura>> allCrops;

    /**
     * Constructs an instance of CropRepository.
     *
     * @param application The application context.
     */
    public CropRepository(Application application) {
        CropRoomDatabase cropRoomDatabase = ServiceLocator.getInstance().getCropDao(application);
        this.mCropDao = cropRoomDatabase.cropDao();
        allCrops = mCropDao.getAll();
    }

    /**
     * Retrieves all Crop entities.
     *
     * @return A list of all Crop entities.
     */
    @Override
    public LiveData<List<Coltura>> getAllCrops() {
        return allCrops;
    }

    /**
     * Gets all crops for irrigation on the specified date.
     *
     * @param date The date to filter crops for irrigation.
     * @return A list of all crops for irrigation on the specified date.
     */
    @Override
    public LiveData<List<Coltura>> getAllCropsToWater(long date) {
        return mCropDao.getAllToWater(date);

    }

    /**
     * Deletes a Crop entity.
     *
     * @param crop The Crop entity to delete.
     */
    @Override
    public void deleteCrop(Coltura crop) {
        new DeleteCropAsyncTask(mCropDao).execute(crop);
    }

    /**
     * Inserts a new Crop entity.
     *
     * @param crop The new Crop entity to insert.
     */
    @Override
    public void insert(Coltura crop) {
        new InsertCropAsyncTask(mCropDao).execute(crop);
    }

    /**
     * Retrieves a Crop entity from the repository based on the specified ID.
     *
     * @param cropId The ID of the Crop entity to search for in the repository.
     * @return The Crop entity corresponding to the specified ID if present in the repository, otherwise null.
     */
    @Override
    public Coltura getCropById(String cropId) {
        return mCropDao.getById(cropId);
    }

    /**
     * Updates the last watering date to the current date for the specified crop.
     *
     * @param crop The crop to update the last watering date to the current date.
     */
    @Override
    public void updateCropWateringDate(Coltura crop) {
        db.collection(Constants.FIRESTORE_COLLECTION_CROPS)
                .document(crop.getId())
                .update("ultimo_innaffiamento", new Date());
        deleteCrop(crop);
        crop.setUltimoInnaffiamento(new Date());
        insert(crop);
    }

    /**
     * Updates the last watering date for the specified crop to the date provided as a parameter.
     *
     * @param crop     The crop to update the last watering date.
     * @param newDate  The date to update the last watering date of the specified crop.
     */

    @Override
    public void updateCropWateringDate(Coltura crop, Date newDate) {
        deleteCrop(crop);
        if(newDate != null) {
            db.collection(Constants.FIRESTORE_COLLECTION_CROPS)
                    .document(crop.getId())
                    .update("ultimo_innaffiamento", newDate);
            crop.setUltimoInnaffiamento(newDate);
        }
        crop.setUltimoAggiornamento(new Date());
        insert(crop);
    }

    /**
     * Updates the last watering date to the specified date for the crops in the provided map.
     *
     * @param updates The map containing crop-date pairs.
     */
    public void updateCropsWateringDate(Map<Coltura, Date> updates) {
        for (Map.Entry<Coltura, Date> current : updates.entrySet()) {
            updateCropWateringDate(current.getKey(), current.getValue());
        }
    }

    /**
     * Updates the last watering date to the current date for all crops in the garden.
     *
     * @param crops The list of crops to update.
     */
    public void updateCropsWateringDate(List<Coltura> crops) {
        for (Coltura crop : crops) {
            db.collection(Constants.FIRESTORE_COLLECTION_CROPS)
                    .document(crop.getId())
                    .update("ultimo_innaffiamento", new Date());
            deleteCrop(crop);
            crop.setUltimoInnaffiamento(new Date());
            crop.setUltimoAggiornamento(new Date());
            insert(crop);
        }
    }

    /**
     * Adds a new crop to Firestore and the local database.
     *
     * @param cropsMap The map containing the crop data to be added.
     */
    public void addCrops(Map<String, Object> cropsMap) {
        db.collection(Constants.FIRESTORE_COLLECTION_CROPS)
                .add(cropsMap)
                .addOnSuccessListener(documentReference -> {
                    String cropId = documentReference.getId();
                    // Add the ID to the cropMap
                    cropsMap.put(Constants.PRODUCT_ID, cropId);
                    Coltura crop = new Coltura(cropsMap);
                    insert(crop);
                });
    }

    /**
     * Updates the local database with crop entities from Firestore.
     * If the local database is empty, it downloads crop entities from Firestore.
     *
     * @param currentUserId The ID of the current user.
     */
    public void updateLocalDB(String currentUserId) {
        db.collection(Constants.FIRESTORE_COLLECTION_CROPS)
                .whereEqualTo(Constants.CROPS_OWNER, currentUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List<Coltura> tempCrops = allCrops.getValue();
                                boolean isCropPresent = false;
                                boolean isCropChanged = false;
                                Coltura oldCrop = null;
                                Coltura newCrop = null;
                                if (tempCrops != null) {
                                    for (Coltura c : tempCrops) {
                                        if (c.getId().equals(document.getId())) {
                                            isCropPresent = true;
                                        }
                                        if (c.getId().equals(document.getId()) && c.getFaseAttuale() != Math.toIntExact(Long.valueOf(String.valueOf(document.getData().get("fase_attuale"))))) {
                                            oldCrop = c;
                                            isCropChanged = true;
                                        }
                                        boolean cropFireBaseDBNotPresent = false;
                                        for (QueryDocumentSnapshot d : task.getResult()) {
                                            if (c.getId().equals(d.getId())) {
                                                cropFireBaseDBNotPresent = true;
                                            }
                                        }
                                        if (!cropFireBaseDBNotPresent) {
                                            deleteCrop(c);
                                        }
                                    }
                                    if (!isCropPresent) {
                                        newCrop = new Coltura(document);
                                        insert(newCrop);
                                    }
                                    if (isCropChanged) {
                                        deleteCrop(oldCrop);
                                        newCrop = new Coltura(document);
                                        insert(newCrop);
                                    }
                                } else { // Local DB is empty
                                    newCrop = new Coltura(document);
                                    insert(newCrop);
                                }
                            }
                        }
                    }
                });
    }

    // Intern AsyncTask classes

    private static class DeleteCropAsyncTask extends AsyncTask<Coltura, Void, Void> {
        private final CropDao cropDao;

        private DeleteCropAsyncTask(CropDao cropDao) {
            this.cropDao = cropDao;
        }

        @Override
        protected Void doInBackground(Coltura... crops) {
            cropDao.delete(crops[0]);
            return null;
        }
    }

    private static class InsertCropAsyncTask extends AsyncTask<Coltura, Void, Void> {
        private final CropDao mCropDao;

        private InsertCropAsyncTask(CropDao cropDao) {
            this.mCropDao = cropDao;
        }

        @Override
        protected Void doInBackground(Coltura... crops) {
            mCropDao.insert(crops[0]);
            return null;
        }
    }
}

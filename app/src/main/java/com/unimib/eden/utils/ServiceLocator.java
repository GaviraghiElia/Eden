package com.unimib.eden.utils;

import android.app.Application;

import com.unimib.eden.database.CropRoomDatabase;
import com.unimib.eden.database.PhaseRoomDatabase;
import com.unimib.eden.database.PlantRoomDatabase;
import com.unimib.eden.database.ProductRoomDatabase;

/**
 * Class ServiceLocator that provides instances of Room databases for the Plant and Phase entities.
 */
public class ServiceLocator {

    private static ServiceLocator instance = null;

    private ServiceLocator() {
    }

    /**
     * Method getInstance that returns an instance of the ServiceLocator class.
     *
     * @return An instance of the ServiceLocator class.
     */
    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized (ServiceLocator.class) {
                instance = new ServiceLocator();
            }
        }
        return instance;
    }

    /**
     * Method getPlantDao that returns an instance of the plant Room database.
     *
     * @param application   The application context
     * @return An instance of the plant Room database.
     */
    public PlantRoomDatabase getPlantDao(Application application) {
        return PlantRoomDatabase.getDatabase(application);
    }

    /**
     * Method getCropDao that returns an instance of the crop Room database.
     *
     * @param application   The application context
     * @return An instance of the crop Room database.
     */
    public CropRoomDatabase getCropDao(Application application) {
        return CropRoomDatabase.getDatabase(application);
    }
    /**
     * Method getPhaseDao that returns an instance of the phase Room database.
     *
     * @param application   The application context
     * @return An instance of the phase Room database.
     */
    public PhaseRoomDatabase getPhaseDao(Application application) {
        return PhaseRoomDatabase.getDatabase(application);
    }


    public ProductRoomDatabase getProductDao(Application application) {
        return ProductRoomDatabase.getDatabase(application);
    }
}

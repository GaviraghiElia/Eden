package com.unimib.eden.utils;

import android.app.Application;

import com.unimib.eden.database.ColturaRoomDatabase;
import com.unimib.eden.database.FaseRoomDatabase;
import com.unimib.eden.database.PiantaRoomDatabase;
import com.unimib.eden.database.ProdottoRoomDatabase;

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
    public PiantaRoomDatabase getPlantDao(Application application) {
        return PiantaRoomDatabase.getDatabase(application);
    }

    /**
     * Method getCropDao that returns an instance of the crop Room database.
     *
     * @param application   The application context
     * @return An instance of the crop Room database.
     */
    public ColturaRoomDatabase getCropDao(Application application) {
        return ColturaRoomDatabase.getDatabase(application);
    }
    /**
     * Method getPhaseDao that returns an instance of the phase Room database.
     *
     * @param application   The application context
     * @return An instance of the phase Room database.
     */
    public FaseRoomDatabase getPhaseDao(Application application) {
        return FaseRoomDatabase.getDatabase(application);
    }


    public ProdottoRoomDatabase getProductDao(Application application) {
        return ProdottoRoomDatabase.getDatabase(application);
    }
}

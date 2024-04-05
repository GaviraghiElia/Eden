package com.unimib.eden.utils;

import android.app.Application;

import com.unimib.eden.database.ColturaRoomDatabase;
import com.unimib.eden.database.FaseRoomDatabase;
import com.unimib.eden.database.PiantaRoomDatabase;

import java.io.File;
import java.io.IOException;

/**
 * Class ServiceLocator che restituisce istanze dei Room database per le entità Pianta e Fase.
 *
 * @author Alice Hoa Galli
 */
public class ServiceLocator {

    private static ServiceLocator instance = null;

    private ServiceLocator() {
    }

    /**
     * Metodo getInstance che restituisce un'istanza della classe ServiceLocator.
     *
     * @return Un'istanza della classe ServiceLocator.
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
     * Metodo getPiantaDao che restituisce un'istanza del Room database di pianta.
     *
     * @param application   Contesto dell'applicazione
     * @return Un'istanza del Room database di pianta.
     */
    public PiantaRoomDatabase getPiantaDao(Application application) {
        return PiantaRoomDatabase.getDatabase(application);
    }

    /**
     * Metodo getColturaDao che restituisce un'istanza del Room database di coltura.
     *
     * @param application   Contesto dell'applicazione
     * @return Un'istanza del Room database di coltura.
     */
    public ColturaRoomDatabase getColturaDao(Application application) {
        return ColturaRoomDatabase.getDatabase(application);
    }
    /**
     * Metodo getFaseDao che restituisce un'istanza del Room database di fase.
     *
     * @param application   Contesto dell'applicazione
     * @return Un'istanza del Room database di fase.
     */
    public FaseRoomDatabase getFaseDao(Application application) {
        return FaseRoomDatabase.getDatabase(application);
    }



}

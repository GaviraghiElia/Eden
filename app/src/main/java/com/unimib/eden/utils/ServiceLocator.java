package com.unimib.eden.utils;

import android.app.Application;

import com.unimib.eden.database.ColturaRoomDatabase;
import com.unimib.eden.database.PiantaRoomDatabase;

import java.io.File;
import java.io.IOException;

public class ServiceLocator {

    private static ServiceLocator instance = null;

    private ServiceLocator() {
    }

    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized (ServiceLocator.class) {
                instance = new ServiceLocator();
            }
        }
        return instance;
    }

    public PiantaRoomDatabase getPiantaDao(Application application) {
        return PiantaRoomDatabase.getDatabase(application);
    }

    public ColturaRoomDatabase getColturaDao(Application application) {
        return ColturaRoomDatabase.getDatabase(application);
    }

}

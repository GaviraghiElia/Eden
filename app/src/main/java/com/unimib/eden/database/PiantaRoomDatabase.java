package com.unimib.eden.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.unimib.eden.model.Pianta;
import com.unimib.eden.utils.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Pianta.class}, version = Constants.VERSIONE_DATABASE, exportSchema = false)
public abstract class PiantaRoomDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile PiantaRoomDatabase INSTANCE;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //  new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static PiantaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PiantaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PiantaRoomDatabase.class, Constants.NOME_DATABASE_ORTO)
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract PiantaDao piantaDao();
}

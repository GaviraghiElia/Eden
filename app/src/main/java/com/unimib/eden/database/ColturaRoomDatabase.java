package com.unimib.eden.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.unimib.eden.model.Coltura;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Coltura.class}, version = Constants.VERSIONE_DATABASE, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ColturaRoomDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile ColturaRoomDatabase INSTANCE;
    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //  new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static ColturaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ColturaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ColturaRoomDatabase.class, Constants.NOME_DATABASE_COLTURA)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ColturaDao colturaDao();
}

package com.unimib.eden.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.unimib.eden.model.Fase;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Classe FaseRoomDatabase per il database di Fase.
 * Questa classe definisce il database Room che contiene la tabella Fase.
 *
 * @author Alice Hoa Galli
 */
@Database(entities = {Fase.class}, version = Constants.VERSIONE_DATABASE_FASE, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class FaseRoomDatabase extends RoomDatabase {
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile FaseRoomDatabase INSTANCE;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //  new PopulateDbAsyncTask(instance).execute();
        }
    };

    /**
     * Metodo getDatabase che ottiene un'istanza del database.
     *
     * @param context   Il contesto dell'applicazione
     * @return Un'istanza del database.
     */
    public static FaseRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FaseRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    FaseRoomDatabase.class, Constants.NOME_DATABASE_ORTO)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Metodo faseDao che ottiene il Dao associato al database.
     *
     * @return Il Dao associato al database.
     */
    public abstract FaseDao faseDao();
}

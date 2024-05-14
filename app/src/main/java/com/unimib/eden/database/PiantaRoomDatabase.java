package com.unimib.eden.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.unimib.eden.model.Pianta;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Classe PiantaRoomDatabase per il database di Pianta.
 * Questa classe definisce il database Room che contiene la tabella Pianta.
 *
 * @author Alice Hoa Galli
 */
@Database(entities = {Pianta.class}, version = Constants.VERSIONE_DATABASE_PIANTA, exportSchema = false)
@TypeConverters({Converters.class})
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

    /**
     * Metodo getDatabase che ottiene un'istanza del database.
     *
     * @param context   Il contesto dell'applicazione
     * @return Un'istanza del database.
     */
    public static PiantaRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PiantaRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PiantaRoomDatabase.class, Constants.NOME_DATABASE_ORTO)
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
     * Metodo piantaDao che ottiene il Dao associato al database.
     *
     * @return Il Dao associato al database.
     */
    public abstract PiantaDao piantaDao();
}

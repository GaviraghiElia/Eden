package com.unimib.eden.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.unimib.eden.model.Phase;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * PhaseRoomDatabase class for the Phase database.
 * This class defines the Room database that contains the Phase table.
 */
@Database(entities = {Phase.class}, version = Constants.DATABASE_VERSION_PHASE, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class PhaseRoomDatabase extends RoomDatabase {
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile PhaseRoomDatabase INSTANCE;
    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //  new PopulateDbAsyncTask(instance).execute();
        }
    };

    /**
     * Method getDatabase that gets an instance of the database.
     *
     * @param context The application context.
     * @return An instance of the database.
     */
    public static PhaseRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PhaseRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PhaseRoomDatabase.class, Constants.GARDEN_DATABASE_NAME)
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
     * Method phaseDao that gets the Dao associated with the database.
     *
     * @return The Dao associated with the database.
     */
    public abstract PhaseDao phaseDao();
}

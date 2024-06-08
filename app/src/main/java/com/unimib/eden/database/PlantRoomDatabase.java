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
 * PlantRoomDatabase class for the Plant database.
 * This class defines the Room database that contains the Plant table.
 */
@Database(entities = {Pianta.class}, version = Constants.DATABASE_VERSION_PLANT, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class PlantRoomDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile PlantRoomDatabase INSTANCE;
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
     * @param context   The application context.
     * @return An instance of the database.
     */
    public static PlantRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PlantRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PlantRoomDatabase.class, Constants.GARDEN_DATABASE_NAME)
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
     * Method plantDao that gets the Dao associated with the database.
     *
     * @return The Dao associated with the database.
     */
    public abstract PlantDao plantDao();
}

package com.unimib.eden.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.unimib.eden.model.Crop;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Database class for Crop.
 * This class defines the Room database that contains the Crop table.
 */
@Database(entities = {Crop.class}, version = Constants.DATABASE_VERSION, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class CropRoomDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile CropRoomDatabase INSTANCE;
    private static final Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //  new PopulateDbAsyncTask(instance).execute();
        }
    };

    /**
     * Gets an instance of the database.
     *
     * @param context The application context.
     * @return An instance of the database.
     */
    public static CropRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CropRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CropRoomDatabase.class, Constants.GARDEN_DATABASE_NAME)
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
     * Gets the DAO associated with the database.
     *
     * @return The DAO associated with the database.
     */
    public abstract CropDao cropDao();
}

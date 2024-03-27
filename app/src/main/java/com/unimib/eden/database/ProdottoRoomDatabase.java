package com.unimib.eden.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.unimib.eden.model.Prodotto;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Prodotto.class}, version = Constants.VERSIONE_DATABASE, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ProdottoRoomDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    private static final String DATABASE_NAME = "prodotti";

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile ProdottoRoomDatabase INSTANCE;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //  new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static ProdottoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProdottoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ProdottoRoomDatabase.class, Constants.NOME_DATABASE_PRODOTTO)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract ProdottoDao prodottoDao();
}

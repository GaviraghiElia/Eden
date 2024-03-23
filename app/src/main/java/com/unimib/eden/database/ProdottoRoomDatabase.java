package com.unimib.eden.database;

import android.content.Context;
import androidx.room.Room;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.unimib.eden.model.Prodotto;
//si collega effettivamente con il db
@Database(entities = {Prodotto.class}, version = 1, exportSchema = false)
public abstract class ProdottoRoomDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "prodotti";
    private static ProdottoRoomDatabase instance;

    public static synchronized ProdottoRoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ProdottoRoomDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract ProdottoDao prodottoDao();
}

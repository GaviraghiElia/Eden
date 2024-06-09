package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.unimib.eden.database.CropDao;
import com.unimib.eden.database.CropRoomDatabase;
import com.unimib.eden.model.Crop;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ColturaDaoTest {
    private static final String TAG = "ColturaDaoTest";
    private CropDao colturaDao;
    private CropRoomDatabase colturaRoomDatabase;

    private Crop coltura1 = new Crop(
            "QuV8dadcUbZ5gKNpVhET",
            "beVITqkLHWCerI1XLRxj",
            //TODO: change to id
            "YPYhzBvIsnbKTsz7BhumJc3oP142",
            20,
            "Coltivazione in vaso sul balcone",
            new Date(124 - 1900, 2, 29, 19, 13, 11),
            2,
            new Date(124 - 1900, 2, 22, 16, 43, 8),
            "Pomodori",
            new ArrayList<>(Arrays.asList(5, 4, 4, 3, 3, 4, 7)),
            4
    );
    private Crop coltura2 = new Crop(
            "RJWeOugwpBBo4bbZE95C",
            "vxpwZvOefZM28L9GnOmX",
            //TODO: change to id
            "YPYhzBvIsnbKTsz7BhumJc3oP142",
            5,
            "",
            new Date(124 - 1900, 2, 29, 1, 0, 0),
            0,
            new Date(124 - 1900, 2, 29, 19, 13, 11),
            "Zucchine",
            new ArrayList<>(Arrays.asList(5, 4, 4, 3, 3, 4, 7)),
            5
    );

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        colturaRoomDatabase = Room.inMemoryDatabaseBuilder(context, CropRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        colturaDao = colturaRoomDatabase.cropDao();
    }

    @After
    public void closeDb() {
        colturaRoomDatabase.close();
    }

    private void addOneColturaToDb() {
        colturaDao.insert(coltura1);
    }

    private void addTwoColtureToDb() {
        colturaDao.insert(coltura1);
        colturaDao.insert(coltura2);
    }

    @Test
    public void daoInsert_insertsColtureIntoDb() {
        addOneColturaToDb();
        List<Crop> allColture = colturaDao.getAllTest();
        assertEquals(allColture.get(0), coltura1);
    }

    @Test
    public void daoGetAllColture_returnAllColtureFromDb() {
        addTwoColtureToDb();
        List<Crop> allColture = colturaDao.getAllTest();
        assertEquals(allColture.get(0), coltura1);
        assertEquals(allColture.get(1), coltura2);
    }

    @Test
    public void daoDeleteColture_deleteAllColtureFromDb() {
        addTwoColtureToDb();
        colturaDao.delete(coltura1);
        colturaDao.delete(coltura2);
        List<Crop> allColture = colturaDao.getAllTest();
        assertTrue(allColture.isEmpty());
    }

    @Test
    public void daoGetColturaById_returnsColturaFromDb() {
        addTwoColtureToDb();
        Crop coltura = colturaDao.getById(coltura2.getId());
        assertEquals(coltura, coltura2);
    }

    @Test
    public void daoGetAllDaIrrigare_returnsAllColtureDaIrrigareFromDb() {
        addTwoColtureToDb();
        List<Crop> allColtureDaIrrigare = new ArrayList<>();
        allColtureDaIrrigare = colturaDao.getAllToWaterTest((new Date()).getTime()/ (1000 * 60 * 60 * 24));

        assertTrue(allColtureDaIrrigare.contains(coltura1));
        assertTrue(allColtureDaIrrigare.contains(coltura2));
    }

    @Test
    public void daoGetColtureByIds_returnsAllColtureFromDb() {
        addTwoColtureToDb();
        List<String> idsList = new ArrayList<>();
        idsList.add(coltura1.getId());
        idsList.add(coltura2.getId());
        List<Crop> colture = colturaDao.getByIds(idsList);
        assertTrue(colture.contains(coltura1));
        assertTrue(colture.contains(coltura2));
    }

}

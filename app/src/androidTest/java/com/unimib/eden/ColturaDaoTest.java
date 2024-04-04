package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.unimib.eden.database.ColturaDao;
import com.unimib.eden.database.ColturaRoomDatabase;
import com.unimib.eden.model.Coltura;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ColturaDaoTest {
    private static final String TAG = "ColturaDaoTest";
    private ColturaDao colturaDao;
    private ColturaRoomDatabase colturaRoomDatabase;

    private Coltura coltura1 = new Coltura(
            "QuV8dadcUbZ5gKNpVhET",
            "beVITqkLHWCerI1XLRxj",
            //TODO: change to id
            "g.colombo147@campus.unimib.it",
            20,
            "Coltivazione in vaso sul balcone",
            Date.from(java.time.LocalDateTime.of(2024, 3, 20, 9, 14, 18).plusHours(1).atZone(java.time.ZoneId.systemDefault()).toInstant()),
            2,
            Date.from(LocalDateTime.of(2024, 3, 22, 16, 43, 8).atZone(java.time.ZoneId.systemDefault()).toInstant())
    );
    private Coltura coltura2 = new Coltura(
            "RJWeOugwpBBo4bbZE95C",
            "vxpwZvOefZM28L9GnOmX",
            //TODO: change to id
            "g.colombo147@campus.unimib.it",
            5,
            "",
            Date.from(java.time.LocalDateTime.of(2024, 3, 29, 0, 0, 0).plusHours(1).atZone(java.time.ZoneId.systemDefault()).toInstant()),
            0,
            Date.from(java.time.LocalDateTime.of(2024, 3, 29, 18, 13, 11).plusHours(1).atZone(java.time.ZoneId.systemDefault()).toInstant())
    );

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        colturaRoomDatabase = Room.inMemoryDatabaseBuilder(context, ColturaRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        colturaDao = colturaRoomDatabase.colturaDao();
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
        List<Coltura> allColture = colturaDao.getAll();
        assertEquals(allColture.get(0), coltura1);
    }

    @Test
    public void daoGetAllColture_returnAllColtureFromDb() {
        addTwoColtureToDb();
        List<Coltura> allColture = colturaDao.getAll();
        assertEquals(allColture.get(0), coltura1);
        assertEquals(allColture.get(1), coltura2);
    }

    @Test
    public void daoDeleteColture_deleteAllColtureFromDb() {
        addTwoColtureToDb();
        colturaDao.delete(coltura1);
        colturaDao.delete(coltura2);
        List<Coltura> allColture = colturaDao.getAll();
        assertTrue(allColture.isEmpty());
    }

    @Test
    public void daoGetColturaById_returnsColturaFromDb() {
        addTwoColtureToDb();
        Coltura coltura = colturaDao.getById(coltura2.getId());
        assertEquals(coltura, coltura2);
    }

}

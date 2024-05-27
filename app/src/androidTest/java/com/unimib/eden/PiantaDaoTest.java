package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.unimib.eden.database.PiantaDao;
import com.unimib.eden.database.PiantaRoomDatabase;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Pianta;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kotlin.jvm.Throws;

@RunWith(AndroidJUnit4.class)
public class PiantaDaoTest {
    private static final String TAG = "PiantaDaoTest";
    private PiantaDao piantaDao;
    private PiantaRoomDatabase piantaRoomDatabase;
    private ArrayList<String> fasi = new ArrayList<String>();

    private Pianta pianta1 = new Pianta(
            "beVITqkLHWCerI1XLRxj",
            "pomodoro",
            "Pianta di pomodoro (Solanum lycopersicum)",
            "Solanaceae",
            3,
            4,
            fasi,
            0.0,
            "pieno sole",
            "ben drenato, ricco di sostanze nutritive",
            18,
            24,
            2.0
    );
    private Pianta pianta2 = new Pianta(
            "GIuCsu9ircjfN4RXWgxe",
            "Spinaci",
            "Pianta di spinaci (Spinacia oleracea)",
            "Amaranthaceae",
            3,
            5,
            fasi,
            0.0,
            "mezz'ombra",
            "ben drenato, ricco di humus",
            10,
            20,
            0.3
    );

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        piantaRoomDatabase = Room.inMemoryDatabaseBuilder(context, PiantaRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        piantaDao = piantaRoomDatabase.piantaDao();
    }

    @After
    public void closeDb() {
        piantaRoomDatabase.close();
    }

    private void addOnePiantaToDb() {
        fasi.add("IfjXQLn98rnUDu1rax8h");
        pianta1.setFasi(fasi);
        piantaDao.insert(pianta1);
    }

    private void addTwoPianteToDb() {
        fasi.add("IfjXQLn98rnUDu1rax8h");
        pianta1.setFasi(fasi);
        pianta2.setFasi(fasi);
        piantaDao.insert(pianta1);
        piantaDao.insert(pianta2);
    }

    @Test
    public void daoInsert_insertsPiantaIntoDb() {
        addOnePiantaToDb();
        List<Pianta> allPiante = piantaDao.getAll();
        assertEquals(allPiante.get(0), pianta1);
    }

   @Test
   public void daoGetAllPiante_returnAllPianteFromDb() {
        addTwoPianteToDb();
        List<Pianta> allPiante = piantaDao.getAll();
       assertEquals(allPiante.get(0), pianta1);
       assertEquals(allPiante.get(1), pianta2);
   }

   @Test
    public void daoDeletePiante_deleteAllPianteFromDb() {
        addTwoPianteToDb();
        piantaDao.delete(pianta1);
        piantaDao.delete(pianta2);
        List<Pianta> allPiante = piantaDao.getAll();
        assertTrue(allPiante.isEmpty());
   }

   @Test
    public void daoSearchPiante_returnsPiantaFromDb() {
        addTwoPianteToDb();
        List<Pianta> allPiante = piantaDao.searchPiante("Spinaci");
        assertEquals(allPiante.get(0), pianta2);
   }

   @Test
    public void daoSearchPianteFiltri_returnsPiantaFilteredFromDb() {
        addTwoPianteToDb();
        List<Pianta> allPiante = piantaDao.searchPianteFiltri("pom", 3, 5);
        assertEquals(allPiante.get(0), pianta1);
   }

   @Test
    public void daoSearchPianteFiltriAll_returnsPiantaFilteredFromDb() {
       addTwoPianteToDb();
       List<Pianta> allPiante = piantaDao.searchPianteFiltriAll("Spi",  "mezz'ombra", 3, 5);
       assertEquals(allPiante.get(0), pianta2);
   }

    @Test
    public void daoGetPiantaById_returnsPiantaFromDb() {
        addTwoPianteToDb();
        Pianta pianta = piantaDao.getById(pianta2.getId());
        assertEquals(pianta, pianta2);
    }

}

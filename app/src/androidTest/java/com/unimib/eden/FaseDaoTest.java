package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.unimib.eden.database.PhaseDao;
import com.unimib.eden.database.PhaseRoomDatabase;
import com.unimib.eden.model.Fase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class FaseDaoTest {

    private static final String TAG = "FaseDaoTest";
    private PhaseDao faseDao;
    private PhaseRoomDatabase faseRoomDatabase;


    private Fase fase1 = new Fase(
            "IfjXQLn98rnUDu1rax8h",
            "Formazione Frutti",
            4,
            30,
            "I fiori sono impollinati e si formano i primi frutti.",
            "URL_immagine_formazione_frutti",
            3
    );
    private Fase fase2 = new Fase(
            "mtqfP931yhNDQzFRN8RU",
            "Fioritura",
            4,
            15,
            "La pianta produce fiori.",
            "URL_immagine_fioritura",
            3
    );

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        faseRoomDatabase = Room.inMemoryDatabaseBuilder(context, PhaseRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        faseDao = faseRoomDatabase.phaseDao();
    }

    @After
    public void closeDb() {
        faseRoomDatabase.close();
    }

    private void addOneFaseToDb() {
        faseDao.insert(fase1);
    }

    private void addTwoFasiToDb() {
        faseDao.insert(fase1);
        faseDao.insert(fase2);
    }

    @Test
    public void daoGetFaseById_returnsFaseFromDb() {
        addTwoFasiToDb();
        Fase fase = faseDao.getById(fase2.getId());
        assertEquals(fase, fase2);
    }



    @Test
    public void daoInsert_insertsFaseIntoDb() {
        addOneFaseToDb();
        List<Fase> allFasi = faseDao.getAll();
        assertEquals(allFasi.get(0), fase1);
    }

    @Test
    public void daoGetAllFasi_returnAllFasiFromDb() {
        addTwoFasiToDb();
        List<Fase> allFasi = faseDao.getAll();
        assertEquals(allFasi.get(0), fase1);
        assertEquals(allFasi.get(1), fase2);
    }

    @Test
    public void daoDeleteFasi_deleteAllFasiFromDb() {
        addTwoFasiToDb();
        faseDao.delete(fase1);
        faseDao.delete(fase2);
        List<Fase> allFasi = faseDao.getAll();
        assertTrue(allFasi.isEmpty());
    }

    @Test
    public void daoGetFasiId_returnsFasiByIdFromDb() {
        addTwoFasiToDb();
        List<String> fasiId = new ArrayList<>();
        fasiId.add("mtqfP931yhNDQzFRN8RU");
        List<Fase> allFasi = faseDao.getPhasesIds(fasiId);
        assertEquals(allFasi.get(0), fase2);
    }



}

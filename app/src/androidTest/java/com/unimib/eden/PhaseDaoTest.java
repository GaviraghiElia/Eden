package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.unimib.eden.database.PhaseDao;
import com.unimib.eden.database.PhaseRoomDatabase;
import com.unimib.eden.model.Phase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class PhaseDaoTest {

    private static final String TAG = "PhaseDaoTest";
    private PhaseDao phaseDao;
    private PhaseRoomDatabase phaseRoomDatabase;


    private final Phase phase1 = new Phase(
            "IfjXQLn98rnUDu1rax8h",
            "Fruit Formation",
            4,
            30,
            "Flowers are pollinated and the first fruits begin to form.",
            "URL_image_fruit_formation",
            3
    );
    private final Phase phase2 = new Phase(
            "mtqfP931yhNDQzFRN8RU",
            "Flowering",
            4,
            15,
            "The plant produces flowers.",
            "URL_image_flowering",
            3
    );

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        phaseRoomDatabase = Room.inMemoryDatabaseBuilder(context, PhaseRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        phaseDao = phaseRoomDatabase.phaseDao();
    }

    @After
    public void closeDb() {
        phaseRoomDatabase.close();
    }

    private void addOnePhaseToDb() {
        phaseDao.insert(phase1);
    }

    private void addTwoPhasesToDb() {
        phaseDao.insert(phase1);
        phaseDao.insert(phase2);
    }

    @Test
    public void daoGetPhaseById_returnsPhaseFromDb() {
        addTwoPhasesToDb();
        Phase phase = phaseDao.getById(phase2.getId());
        assertEquals(phase, phase2);
    }



    @Test
    public void daoInsert_insertsPhaseIntoDb() {
        addOnePhaseToDb();
        List<Phase> allPhases = phaseDao.getAll();
        assertEquals(allPhases.get(0), phase1);
    }

    @Test
    public void daoGetAllPhases_returnAllPhasesFromDb() {
        addTwoPhasesToDb();
        List<Phase> allPhases = phaseDao.getAll();
        assertEquals(allPhases.get(0), phase1);
        assertEquals(allPhases.get(1), phase2);
    }

    @Test
    public void daoDeletePhases_deleteAllPhasesFromDb() {
        addTwoPhasesToDb();
        phaseDao.delete(phase1);
        phaseDao.delete(phase2);
        List<Phase> allPhases = phaseDao.getAll();
        assertTrue(allPhases.isEmpty());
    }

    @Test
    public void daoGetPhasesId_returnsPhasesByIdFromDb() {
        addTwoPhasesToDb();
        List<String> phasesId = new ArrayList<>();
        phasesId.add("mtqfP931yhNDQzFRN8RU");
        List<Phase> allPhases = phaseDao.getPhasesIds(phasesId);
        assertEquals(allPhases.get(0), phase2);
    }



}

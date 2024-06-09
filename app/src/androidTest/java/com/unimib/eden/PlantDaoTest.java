package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.unimib.eden.database.PlantDao;
import com.unimib.eden.database.PlantRoomDatabase;
import com.unimib.eden.model.Plant;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

//instrumented test
@RunWith(AndroidJUnit4.class)
public class PlantDaoTest {
    private static final String TAG = "PlantDaoTest";
    private PlantDao plantDao;
    private PlantRoomDatabase plantRoomDatabase;
    private final ArrayList<String> phases = new ArrayList<>();

    private final Plant plant1 = new Plant(
            "beVITqkLHWCerI1XLRxj",
            "Tomato",
            "Tomato plant (Solanum lycopersicum)",
            "Solanaceae",
            3,
            4,
            phases,
            0.0,
            "full sun",
            "well-drained, nutrient-rich soil",
            18,
            24,
            2.0
    );
    private final Plant plant2 = new Plant(
            "GIuCsu9ircjfN4RXWgxe",
            "Spinach",
            "Spinach plant (Spinacia oleracea)",
            "Amaranthaceae",
            3,
            5,
            phases,
            0.0,
            "partial shade",
            "well-drained, humus-rich soil",
            10,
            20,
            0.3
    );

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        plantRoomDatabase = Room.inMemoryDatabaseBuilder(context, PlantRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        plantDao = plantRoomDatabase.plantDao();
    }

    @After
    public void closeDb() {
        plantRoomDatabase.close();
    }

    private void addOnePlantToDb() {
        phases.add("IfjXQLn98rnUDu1rax8h");
        plant1.setPhases(phases);
        plantDao.insert(plant1);
    }

    private void addTwoPlantsToDb() {
        phases.add("IfjXQLn98rnUDu1rax8h");
        plant1.setPhases(phases);
        plant2.setPhases(phases);
        plantDao.insert(plant1);
        plantDao.insert(plant2);
    }

    @Test
    public void daoInsert_insertsPlantIntoDb() {
        addOnePlantToDb();
        List<Plant> allPlants = plantDao.getAll();
        assertEquals(allPlants.get(0), plant1);
    }

    @Test
    public void daoGetAllPlants_returnAllPlantsFromDb() {
        addTwoPlantsToDb();
        List<Plant> allPlants = plantDao.getAll();
        assertEquals(allPlants.get(0), plant1);
        assertEquals(allPlants.get(1), plant2);
    }

    @Test
    public void daoDeletePlants_deleteAllPlantsFromDb() {
        addTwoPlantsToDb();
        plantDao.delete(plant1);
        plantDao.delete(plant2);
        List<Plant> allPlants = plantDao.getAll();
        assertTrue(allPlants.isEmpty());
    }

    @Test
    public void daoSearchPlants_returnsPlantFromDb() {
        addTwoPlantsToDb();
        List<Plant> allPlants = plantDao.searchPlants("Spinach");
        assertEquals(allPlants.get(0), plant2);
    }

    @Test
    public void daoSearchPlantsFilters_returnsPlantFilteredFromDb() {
        addTwoPlantsToDb();
        List<Plant> allPlants = plantDao.searchPlantsFilters("tom", 3, 5);
        assertEquals(allPlants.get(0), plant1);
    }

    @Test
    public void daoSearchPlantsFiltersAll_returnsPlantFilteredFromDb() {
        addTwoPlantsToDb();
        List<Plant> allPlants = plantDao.searchPlantsAllFilters("Spi",  "partial shade", 3, 5);
        assertEquals(allPlants.get(0), plant2);
    }

    @Test
    public void daoGetPlantById_returnsPlantFromDb() {
        addTwoPlantsToDb();
        Plant plant = plantDao.getById(plant2.getId());
        assertEquals(plant, plant2);
    }
}

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
public class CropDaoTest {
    private static final String TAG = "CropDaoTest";
    private CropDao cropDao;
    private CropRoomDatabase cropRoomDatabase;

    private final Crop crop1 = new Crop(
            "QuV8dadcUbZ5gKNpVhET",
            "beVITqkLHWCerI1XLRxj",
            "YPYhzBvIsnbKTsz7BhumJc3oP142",
            20,
            "Cultivation in a pot on the balcony",
            new Date(124 - 1900, 2, 29, 19, 13, 11),
            2,
            new Date(124 - 1900, 2, 22, 16, 43, 8),
            "Tomatoes",
            new ArrayList<>(Arrays.asList(5, 4, 4, 3, 3, 4, 7)),
            4
    );
    private final Crop crop2 = new Crop(
            "RJWeOugwpBBo4bbZE95C",
            "vxpwZvOefZM28L9GnOmX",
            "YPYhzBvIsnbKTsz7BhumJc3oP142",
            5,
            "",
            new Date(124 - 1900, 2, 29, 1, 0, 0),
            0,
            new Date(124 - 1900, 2, 29, 19, 13, 11),
            "Zucchinis",
            new ArrayList<>(Arrays.asList(5, 4, 4, 3, 3, 4, 7)),
            5
    );

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        cropRoomDatabase = Room.inMemoryDatabaseBuilder(context, CropRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        cropDao = cropRoomDatabase.cropDao();
    }

    @After
    public void closeDb() {
        cropRoomDatabase.close();
    }

    private void addOneCropToDb() {
        cropDao.insert(crop1);
    }

    private void addTwoCropsToDb() {
        cropDao.insert(crop1);
        cropDao.insert(crop2);
    }

    @Test
    public void daoInsert_insertsCropsIntoDb() {
        addOneCropToDb();
        List<Crop> allCrops = cropDao.getAllTest();
        assertEquals(allCrops.get(0), crop1);
    }

    @Test
    public void daoGetAllCrops_returnAllCropsFromDb() {
        addTwoCropsToDb();
        List<Crop> allCrops = cropDao.getAllTest();
        assertEquals(allCrops.get(0), crop1);
        assertEquals(allCrops.get(1), crop2);
    }

    @Test
    public void daoDeleteCrops_deleteAllCropsFromDb() {
        addTwoCropsToDb();
        cropDao.delete(crop1);
        cropDao.delete(crop2);
        List<Crop> allCrops = cropDao.getAllTest();
        assertTrue(allCrops.isEmpty());
    }

    @Test
    public void daoGetCropById_returnsCropFromDb() {
        addTwoCropsToDb();
        Crop crop = cropDao.getById(crop2.getId());
        assertEquals(crop, crop2);
    }

    @Test
    public void daoGetAllToWater_returnsAllCropsToWaterFromDb() {
        addTwoCropsToDb();
        List<Crop> allCropsToWater = new ArrayList<>();
        allCropsToWater = cropDao.getAllToWaterTest((new Date()).getTime()/ (1000 * 60 * 60 * 24));

        assertTrue(allCropsToWater.contains(crop1));
        assertTrue(allCropsToWater.contains(crop2));
    }

    @Test
    public void daoGetCropsByIds_returnsAllCropsFromDb() {
        addTwoCropsToDb();
        List<String> idsList = new ArrayList<>();
        idsList.add(crop1.getId());
        idsList.add(crop2.getId());
        List<Crop> crops = cropDao.getByIds(idsList);
        assertTrue(crops.contains(crop1));
        assertTrue(crops.contains(crop2));
    }

}

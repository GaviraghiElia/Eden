package com.unimib.eden;

import static com.unimib.eden.utils.Constants.CROPS_ID;
import static com.unimib.eden.utils.Constants.CROPS_PLANT;
import static com.unimib.eden.utils.Constants.CROPS_OWNER;
import static com.unimib.eden.utils.Constants.CROPS_QUANTITY;
import static com.unimib.eden.utils.Constants.CROPS_NOTES;
import static com.unimib.eden.utils.Constants.CROPS_INSERTION_DATE;
import static com.unimib.eden.utils.Constants.CROPS_CURRENT_PHASE;
import static com.unimib.eden.utils.Constants.CROPS_LAST_WATERING;
import static com.unimib.eden.utils.Constants.CROPS_WATERING_FREQUENCY;
import static com.unimib.eden.utils.Constants.CROPS_CURRENT_WATERING_FREQUENCY;
import static com.unimib.eden.utils.Constants.PLANT_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.firebase.Timestamp;
import com.unimib.eden.model.Crop;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CropUnitTest {
    private Crop crop;
    private final String id = "crop1";
    private final String plantId = "plant1";
    private final String owner = "owner1";
    private final int quantity = 10;
    private final String notes = "notes";
    private final Date insertionDate = new Date();
    private final int currentPhase = 1;
    private final Date lastWatering = new Date();
    private final Date lastUpdate = new Date((new Date()).getTime() - 1 * 24 * 60 * 60 * 1000);
    private final String plantName = "plantName";
    private final ArrayList<Integer> wateringFrequency = new ArrayList<>();
    private final int currentWateringFrequency = 2;

    @Before
    public void setUp() {
        wateringFrequency.add(1);
        wateringFrequency.add(2);
        wateringFrequency.add(3);

        crop = new Crop(id, plantId, owner, quantity, notes, insertionDate, currentPhase, lastWatering, plantName, wateringFrequency, currentWateringFrequency);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(id, crop.getId());
        assertEquals(plantId, crop.getPlantId());
        assertEquals(owner, crop.getOwner());
        assertEquals(quantity, crop.getQuantity());
        assertEquals(notes, crop.getNote());
        assertEquals(insertionDate, crop.getInsertionDate());
        assertEquals(currentPhase, crop.getCurrentPhase());
        assertEquals(lastWatering, crop.getLastWatering());
        assertEquals(plantName, crop.getPlantName());
        assertEquals(wateringFrequency, crop.getWateringFrequency());
        assertEquals(currentWateringFrequency, crop.getCurrentWateringFrequency());
        checkDate(lastUpdate, crop.getLastUpdate());
    }

    private void checkDate(Date date1, Date date2){
        assertEquals(date1.getDay(), date2.getDay());
        assertEquals(date1.getHours(), date2.getHours());
        assertEquals(date1.getMinutes(), date2.getMinutes());
        assertEquals(date1.getSeconds(), date2.getSeconds());
        assertEquals(date1.getYear(), date2.getYear());
    }

    @Test
    public void testSetters() {
        crop.setId("newId");
        crop.setPlantId("newPlantId");
        crop.setOwner("newOwner");
        crop.setQuantity(20);
        crop.setNote("newNotes");
        crop.setInsertionDate(new Date(0));
        crop.setCurrentPhase(2);
        crop.setLastWatering(new Date(0));
        crop.setPlantName("newPlantName");
        ArrayList<Integer> newWateringFrequency = new ArrayList<>();
        newWateringFrequency.add(4);
        newWateringFrequency.add(5);
        crop.setWateringFrequency(newWateringFrequency);
        crop.setCurrentWateringFrequency(3);
        crop.setLastUpdate(new Date((new Date()).getTime() - 1 * 24 * 60 * 60 * 1000));

        assertEquals("newId", crop.getId());
        assertEquals("newPlantId", crop.getPlantId());
        assertEquals("newOwner", crop.getOwner());
        assertEquals(20, crop.getQuantity());
        assertEquals("newNotes", crop.getNote());
        assertEquals(new Date(0), crop.getInsertionDate());
        assertEquals(2, crop.getCurrentPhase());
        assertEquals(new Date(0), crop.getLastWatering());
        assertEquals("newPlantName", crop.getPlantName());
        assertEquals(newWateringFrequency, crop.getWateringFrequency());
        assertEquals(3, crop.getCurrentWateringFrequency());
        checkDate(new Date((new Date()).getTime() - 1 * 24 * 60 * 60 * 1000), crop.getLastUpdate());
    }

    @Test
    public void testEqualsAndHashCode() {
        Crop sameCrop = new Crop(id, plantId, owner, quantity, notes, insertionDate, currentPhase, lastWatering, plantName, wateringFrequency, currentWateringFrequency);
        Crop differentCrop = new Crop("differentId", plantId, owner, quantity, notes, insertionDate, currentPhase, lastWatering, plantName, wateringFrequency, currentWateringFrequency);

        Crop newCrop = null;
        assertTrue(crop.equals(sameCrop));
        assertFalse(crop.equals(differentCrop));
        assertEquals(crop.hashCode(), sameCrop.hashCode());
        assertNotEquals(crop.hashCode(), differentCrop.hashCode());
        assertFalse(crop.equals(newCrop));
        assertTrue(crop.equals(crop));
    }

    @Test
    public void testInitFromMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(CROPS_ID, id);
        dataMap.put(CROPS_PLANT, plantId);
        dataMap.put(CROPS_OWNER, owner);
        dataMap.put(CROPS_QUANTITY, quantity);
        dataMap.put(CROPS_NOTES, notes);
        dataMap.put(CROPS_INSERTION_DATE, new Timestamp(new Date()));
        dataMap.put(CROPS_CURRENT_PHASE, currentPhase);
        dataMap.put(CROPS_LAST_WATERING, new Timestamp(new Date()));
        dataMap.put(PLANT_NAME, plantName);
        dataMap.put(CROPS_WATERING_FREQUENCY, wateringFrequency);
        dataMap.put(CROPS_CURRENT_WATERING_FREQUENCY, currentWateringFrequency);

        // Initialize Crop from the data map
        crop.initFromMap(dataMap);

        // Check if Crop fields are correctly initialized
        assertEquals(id, crop.getId());
        assertEquals(plantId, crop.getPlantId());
        assertEquals(owner, crop.getOwner());
        assertEquals(quantity, crop.getQuantity());
        assertEquals(notes, crop.getNote());
        assertEquals(currentPhase, crop.getCurrentPhase());
        assertEquals(plantName, crop.getPlantName());
        assertEquals(wateringFrequency, crop.getWateringFrequency());
        assertEquals(currentWateringFrequency, crop.getCurrentWateringFrequency());
        // Check if Date fields are not null
        assertNotNull(crop.getInsertionDate());
        assertNotNull(crop.getLastWatering());
        assertNotNull(crop.getLastUpdate());
    }

    @Test
    public void testConstructorFromMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(CROPS_ID, id);
        dataMap.put(CROPS_PLANT, plantId);
        dataMap.put(CROPS_OWNER, owner);
        dataMap.put(CROPS_QUANTITY, quantity);
        dataMap.put(CROPS_NOTES, notes);
        dataMap.put(CROPS_INSERTION_DATE, new Timestamp(new Date()));
        dataMap.put(CROPS_CURRENT_PHASE, currentPhase);
        dataMap.put(CROPS_LAST_WATERING, new Timestamp(new Date()));
        dataMap.put(PLANT_NAME, plantName);
        dataMap.put(CROPS_WATERING_FREQUENCY, wateringFrequency);
        dataMap.put(CROPS_CURRENT_WATERING_FREQUENCY, currentWateringFrequency);

        // Initialize Coltura from the data map
        Crop newColtura =  new Crop(dataMap);

        // Check if Coltura fields are correctly initialized
        assertEquals(id, newColtura.getId());
        assertEquals(plantId, newColtura.getPlantId());
        assertEquals(owner, newColtura.getOwner());
        assertEquals(quantity, newColtura.getQuantity());
        assertEquals(notes, newColtura.getNote());
        assertEquals(currentPhase, newColtura.getCurrentPhase());
        assertEquals(plantName, newColtura.getPlantName());
        assertEquals(wateringFrequency, newColtura.getWateringFrequency());
        assertEquals(currentWateringFrequency, newColtura.getCurrentWateringFrequency());
        // Check if Date fields are not null
        assertNotNull(newColtura.getInsertionDate());
        assertNotNull(newColtura.getLastWatering());
        assertNotNull(newColtura.getLastUpdate());
    }


}

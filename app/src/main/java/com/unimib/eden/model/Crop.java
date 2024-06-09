package com.unimib.eden.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unimib.eden.utils.Converters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static com.unimib.eden.utils.Constants.CROPS_INSERTION_DATE;
import static com.unimib.eden.utils.Constants.CROPS_CURRENT_PHASE;
import static com.unimib.eden.utils.Constants.CROPS_CURRENT_WATERING_FREQUENCY;
import static com.unimib.eden.utils.Constants.CROPS_ID;
import static com.unimib.eden.utils.Constants.CROPS_NOTES;
import static com.unimib.eden.utils.Constants.CROPS_PLANT;
import static com.unimib.eden.utils.Constants.CROPS_OWNER;
import static com.unimib.eden.utils.Constants.CROPS_QUANTITY;
import static com.unimib.eden.utils.Constants.CROPS_LAST_WATERING;
import static com.unimib.eden.utils.Constants.CROP_DATABASE_NAME;
import static com.unimib.eden.utils.Constants.CROPS_WATERING_FREQUENCY;
import static com.unimib.eden.utils.Constants.PLANT_NAME;

/**
 * Model class representing a crop.
 */
@Entity(tableName = CROP_DATABASE_NAME)
public class Crop implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = CROPS_PLANT)
    private String plantId;

    @ColumnInfo(name = CROPS_OWNER)
    private String owner;

    @ColumnInfo(name = CROPS_QUANTITY)
    private int quantity;

    @ColumnInfo(name = CROPS_NOTES)
    private String note;

    @ColumnInfo(name = CROPS_INSERTION_DATE)
    private Date insertionDate;

    @ColumnInfo(name = CROPS_CURRENT_PHASE)
    private int currentPhase;

    @ColumnInfo(name = CROPS_LAST_WATERING)
    private Date lastWatering;
    @ColumnInfo(name = PLANT_NAME)
    private String plantName;
    @TypeConverters(Converters.class)
    @ColumnInfo(name = CROPS_WATERING_FREQUENCY)
    private ArrayList<Integer> wateringFrequency;

    @ColumnInfo(name = CROPS_CURRENT_WATERING_FREQUENCY)
    private int currentWateringFrequency;

    // Local internal parameter signaling when the crop was last updated regarding the weather
    private Date lastUpdate;

    /**
     * Constructor for the Crop class.
     *
     * @param id                     The crop's ID.
     * @param plantId                The ID of the plant associated with the crop.
     * @param owner                  The owner of the crop.
     * @param quantity               The quantity of the crop.
     * @param note                   Notes on the crop.
     * @param insertionDate          The insertion date of the crop.
     * @param currentPhase           The current phase of the crop.
     * @param lastWatering           Date of the last watering.
     * @param plantName              The name of the plant associated with the crop.
     * @param wateringFrequency      Watering frequencies of the plant based on the phase.
     * @param currentWateringFrequency The watering frequency of the current phase of the plant.
     */
    public Crop(@NonNull String id, String plantId, String owner, int quantity, String note, Date insertionDate, int currentPhase, Date lastWatering, String plantName, ArrayList<Integer> wateringFrequency, int currentWateringFrequency) {
        this.id = id;
        this.plantId = plantId;
        this.owner = owner;
        this.quantity = quantity;
        this.note = note;
        this.insertionDate = insertionDate;
        this.currentPhase = currentPhase;
        this.lastWatering = lastWatering;
        this.plantName = plantName;
        this.wateringFrequency = wateringFrequency;
        this.currentWateringFrequency = currentWateringFrequency;
        // The last update date is initialized to the previous day, allowing updating based on weather even on the first insertion
        this.lastUpdate = new Date((new Date()).getTime() - 1 * 24 * 60 * 60 * 1000);
    }

    /**
     * Constructor for the Crop class from a Firestore document.
     *
     * @param document The Firestore document representing the crop.
     */
    public Crop(QueryDocumentSnapshot document) {
        this.id = document.getId();
        Map<String, Object> tempMap = document.getData();
        this.plantId = String.valueOf(tempMap.get(CROPS_PLANT));
        this.owner = String.valueOf(tempMap.get(CROPS_OWNER));
        this.quantity = Integer.parseInt(tempMap.get(CROPS_QUANTITY).toString());
        this.note = String.valueOf(tempMap.get(CROPS_NOTES));
        Timestamp insertionDate = (Timestamp) tempMap.get(CROPS_INSERTION_DATE);
        this.insertionDate = insertionDate.toDate();
        this.currentPhase = Integer.parseInt(tempMap.get(CROPS_CURRENT_PHASE).toString());
        Timestamp lastWatering = (Timestamp) tempMap.get(CROPS_LAST_WATERING);
        this.lastWatering = lastWatering.toDate();
        this.plantName = String.valueOf(tempMap.get(PLANT_NAME));
        this.wateringFrequency = (ArrayList) document.getData().get(CROPS_WATERING_FREQUENCY);
        this.currentWateringFrequency = Integer.parseInt(tempMap.get(CROPS_CURRENT_WATERING_FREQUENCY).toString());
        this.lastUpdate = new Date((new Date()).getTime() - 1 * 24 * 60 * 60 * 1000);
    }

    /**
     * Constructor for the Crop class from a data map.
     *
     * @param dataMap The data map representing the crop.
     */
    public Crop(Map<String, Object> dataMap) {
        this.id = String.valueOf(dataMap.get(CROPS_ID));
        initFromMap(dataMap);
    }

    public void initFromMap(Map<String, Object> tempMap) {
        this.plantId = String.valueOf(tempMap.get(CROPS_PLANT));
        this.owner = String.valueOf(tempMap.get(CROPS_OWNER));
        this.quantity = Integer.parseInt(tempMap.get(CROPS_QUANTITY).toString());
        this.note = String.valueOf(tempMap.get(CROPS_NOTES));
        Timestamp insertionDate = (Timestamp) tempMap.get(CROPS_INSERTION_DATE);
        this.insertionDate = insertionDate.toDate();
        this.currentPhase = Integer.parseInt(tempMap.get(CROPS_CURRENT_PHASE).toString());
        Timestamp lastWatering = (Timestamp) tempMap.get(CROPS_LAST_WATERING);
        this.lastWatering = lastWatering.toDate();
        this.plantName = String.valueOf(tempMap.get(PLANT_NAME));
        this.wateringFrequency = (ArrayList<Integer>) (ArrayList) tempMap.get(CROPS_WATERING_FREQUENCY);
        this.currentWateringFrequency = Integer.parseInt(tempMap.get(CROPS_CURRENT_WATERING_FREQUENCY).toString());
        this.lastUpdate = new Date((new Date()).getTime() - 1 * 24 * 60 * 60 * 1000);
    }

    // Getter and setter methods

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(Date insertionDate) {
        this.insertionDate = insertionDate;
    }

    public int getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(int currentPhase) {
        this.currentPhase = currentPhase;
    }

    public Date getLastWatering() {
        return lastWatering;
    }

    public void setLastWatering(Date lastWatering) {
        this.lastWatering = lastWatering;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public ArrayList<Integer> getWateringFrequency() {
        return wateringFrequency;
    }

    public void setWateringFrequency(ArrayList<Integer> wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    public int getCurrentWateringFrequency() {
        return currentWateringFrequency;
    }

    public void setCurrentWateringFrequency(int currentWateringFrequency) {
        this.currentWateringFrequency = currentWateringFrequency;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crop crop = (Crop) o;
        return quantity == crop.quantity && currentPhase == crop.currentPhase && currentWateringFrequency == crop.currentWateringFrequency && Objects.equals(id, crop.id) && Objects.equals(plantId, crop.plantId) && Objects.equals(owner, crop.owner) && Objects.equals(note, crop.note) && Objects.equals(insertionDate, crop.insertionDate) && Objects.equals(lastWatering, crop.lastWatering) && Objects.equals(plantName, crop.plantName) && Objects.equals(wateringFrequency, crop.wateringFrequency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plantId, owner, quantity, note, insertionDate, currentPhase, lastWatering, plantName, wateringFrequency, currentWateringFrequency);
    }

}

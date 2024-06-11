package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.PLANT_MAX_EXPECTED_HEIGHT;
import static com.unimib.eden.utils.Constants.PLANT_SOWING_END;
import static com.unimib.eden.utils.Constants.PLANT_SOWING_START;
import static com.unimib.eden.utils.Constants.PLANT_MAX_TEMPERATURE;
import static com.unimib.eden.utils.Constants.PLANT_MIN_TEMPERATURE;
import static com.unimib.eden.utils.Constants.PLANT_NAME;
import static com.unimib.eden.utils.Constants.PLANT_DESCRIPTION;
import static com.unimib.eden.utils.Constants.PLANT_BOTANICAL_FAMILY;
import static com.unimib.eden.utils.Constants.PLANT_PHASE;
import static com.unimib.eden.utils.Constants.PLANT_REQUIRED_SPACE;
import static com.unimib.eden.utils.Constants.PLANT_SUN_EXPOSURE;
import static com.unimib.eden.utils.Constants.PLANT_SOIL_TYPE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import com.unimib.eden.utils.Converters;

/**
 * The Plant class represents a plant.
 */
@Entity(tableName = "pianta")
public class Plant implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;
    @ColumnInfo(name = PLANT_NAME)
    private String name;
    @ColumnInfo(name = PLANT_DESCRIPTION)
    private String description;
    @ColumnInfo(name = PLANT_BOTANICAL_FAMILY)
    private String botanicalFamily;
    @ColumnInfo(name = PLANT_SOWING_START)
    private int sowingStart;
    @ColumnInfo(name = PLANT_SOWING_END)
    private int sowingEnd;
    @TypeConverters(Converters.class)
    @ColumnInfo(name = PLANT_PHASE)
    private ArrayList<String> phases;
    @ColumnInfo(name = PLANT_REQUIRED_SPACE)
    private Double requiredSpace;
    @ColumnInfo(name = PLANT_SUN_EXPOSURE)
    private String sunExposure;
    @ColumnInfo(name = PLANT_SOIL_TYPE)
    private String soilType;
    @ColumnInfo(name = PLANT_MIN_TEMPERATURE)
    private int minTemperature;
    @ColumnInfo(name = PLANT_MAX_TEMPERATURE)
    private int maxTemperature;
    @ColumnInfo(name = PLANT_MAX_EXPECTED_HEIGHT)
    private Double maxExpectedHeight;

    /**
     * Constructor for the Plant class.
     *
     * @param id                The ID of the plant.
     * @param name              The name of the plant.
     * @param description       The description of the plant.
     * @param botanicalFamily   The botanical family of the plant.
     * @param sowingStart       The sowing start month.
     * @param sowingEnd         The sowing end month.
     * @param phases            The list of phase IDs for the plant.
     * @param requiredSpace     The required space for the plant to grow.
     * @param sunExposure       The sun exposure required by the plant.
     * @param soilType          The type of soil required by the plant.
     * @param minTemperature    The minimum temperature the plant can tolerate.
     * @param maxTemperature    The maximum temperature the plant can tolerate.
     * @param maxExpectedHeight The maximum expected height of the plant.
     */
    public Plant(@NonNull String id, String name, String description, String botanicalFamily, int sowingStart, int sowingEnd, ArrayList<String> phases, Double requiredSpace, String sunExposure, String soilType, int minTemperature, int maxTemperature, Double maxExpectedHeight) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.botanicalFamily = botanicalFamily;
        this.sowingStart = sowingStart;
        this.sowingEnd = sowingEnd;
        this.phases = phases;
        this.requiredSpace = requiredSpace;
        this.sunExposure = sunExposure;
        this.soilType = soilType;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.maxExpectedHeight = maxExpectedHeight;
    }

    // Getters and setters

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBotanicalFamily() {
        return botanicalFamily;
    }

    public void setBotanicalFamily(String botanicalFamily) {
        this.botanicalFamily = botanicalFamily;
    }

    public int getSowingStart() {
        return sowingStart;
    }

    public void setSowingStart(int sowingStart) {
        this.sowingStart = sowingStart;
    }

    public int getSowingEnd() {
        return sowingEnd;
    }

    public void setSowingEnd(int sowingEnd) {
        this.sowingEnd = sowingEnd;
    }

    public ArrayList<String> getPhases() {
        return phases;
    }

    public void setPhases(ArrayList<String> phases) {
        this.phases = phases;
    }

    public Double getRequiredSpace() {
        return requiredSpace;
    }

    public void setRequiredSpace(Double requiredSpace) {
        this.requiredSpace = requiredSpace;
    }

    public String getSunExposure() {
        return sunExposure;
    }

    public void setSunExposure(String sunExposure) {
        this.sunExposure = sunExposure;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(int minTemperature) {
        this.minTemperature = minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Double getMaxExpectedHeight() {
        return maxExpectedHeight;
    }

    public void setMaxExpectedHeight(Double maxExpectedHeight) {
        this.maxExpectedHeight = maxExpectedHeight;
    }

    @NonNull
    @Override
    public String toString() {
        return "Plant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", botanicalFamily='" + botanicalFamily + '\'' +
                ", sowingStart=" + sowingStart +
                ", sowingEnd=" + sowingEnd +
                ", phases=" + phases +
                ", requiredSpace=" + requiredSpace +
                ", sunExposure='" + sunExposure + '\'' +
                ", soilType='" + soilType + '\'' +
                ", minTemperature=" + minTemperature +
                ", maxTemperature=" + maxTemperature +
                ", maxExpectedHeight=" + maxExpectedHeight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plant plant = (Plant) o;
        return sowingStart == plant.sowingStart &&
                sowingEnd == plant.sowingEnd &&
                minTemperature == plant.minTemperature &&
                maxTemperature == plant.maxTemperature &&
                Objects.equals(id, plant.id) &&
                Objects.equals(name, plant.name) &&
                Objects.equals(description, plant.description) &&
                Objects.equals(botanicalFamily, plant.botanicalFamily) &&
                Objects.equals(phases, plant.phases) &&
                Objects.equals(requiredSpace, plant.requiredSpace) &&
                Objects.equals(sunExposure, plant.sunExposure) &&
                Objects.equals(soilType, plant.soilType) &&
                Objects.equals(maxExpectedHeight, plant.maxExpectedHeight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, botanicalFamily, sowingStart, sowingEnd, phases, requiredSpace, sunExposure, soilType, minTemperature, maxTemperature, maxExpectedHeight);
    }
}

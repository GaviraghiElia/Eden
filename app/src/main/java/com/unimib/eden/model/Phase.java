package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.PHASE_DESCRIPTION;
import static com.unimib.eden.utils.Constants.PHASE_DURATION;
import static com.unimib.eden.utils.Constants.PHASE_WATERING_FREQUENCY;
import static com.unimib.eden.utils.Constants.PHASE_IMAGE;
import static com.unimib.eden.utils.Constants.PHASE_START;
import static com.unimib.eden.utils.Constants.PHASE_NAME;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Phase class is the model representing a phase.
 */
@Entity(tableName = "fase")
public class Phase implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = PHASE_NAME)
    private String phaseName;
    @ColumnInfo(name = PHASE_START)
    private int phaseStart;
    @ColumnInfo(name = PHASE_DURATION)
    private int phaseDuration;
    @ColumnInfo(name = PHASE_DESCRIPTION)
    private String description;
    @ColumnInfo(name = PHASE_IMAGE)
    private String image;
    @ColumnInfo(name = PHASE_WATERING_FREQUENCY)
    private int wateringFrequency;

    /**
     * Constructor for the Phase class.
     *
     * @param id                The phase ID.
     * @param phaseName         The phase name.
     * @param phaseStart        The starting month of the phase.
     * @param phaseDuration     The number of days representing the duration of the phase.
     * @param description       The description of the phase.
     * @param image             The string representing the URL of the phase image.
     * @param wateringFrequency The number of days between each watering.
     */
    public Phase(@NonNull String id, String phaseName, int phaseStart, int phaseDuration, String description, String image, int wateringFrequency) {
        this.id = id;
        this.phaseName = phaseName;
        this.phaseStart = phaseStart;
        this.phaseDuration = phaseDuration;
        this.description = description;
        this.image = image;
        this.wateringFrequency = wateringFrequency;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public int getPhaseStart() {
        return phaseStart;
    }

    public void setPhaseStart(int phaseStart) {
        this.phaseStart = phaseStart;
    }

    public int getPhaseDuration() {
        return phaseDuration;
    }

    public void setPhaseDuration(int phaseDuration) {
        this.phaseDuration = phaseDuration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getWateringFrequency() {
        return wateringFrequency;
    }

    public void setWateringFrequency(int wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phase phase = (Phase) o;
        return getPhaseStart() == phase.getPhaseStart() && getPhaseDuration() == phase.getPhaseDuration() && getWateringFrequency() == phase.getWateringFrequency() && Objects.equals(getId(), phase.getId()) && Objects.equals(getPhaseName(), phase.getPhaseName()) && Objects.equals(getDescription(), phase.getDescription()) && Objects.equals(getImage(), phase.getImage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPhaseName(), getPhaseStart(), getPhaseDuration(), getDescription(), getImage(), getWateringFrequency());
    }
}

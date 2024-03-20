package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.PLANT_PHASE_DESCRIPTION;
import static com.unimib.eden.utils.Constants.PLANT_PHASE_DURATION;
import static com.unimib.eden.utils.Constants.PLANT_PHASE_IMAGE;
import static com.unimib.eden.utils.Constants.PLANT_PHASE_NAME;
import static com.unimib.eden.utils.Constants.PLANT_PHASE_START_DATE;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Phase implements Serializable {

    @SerializedName(PLANT_PHASE_NAME)
    private String phaseName;
    @SerializedName(PLANT_PHASE_START_DATE)
    private String startDatePhase;
    @SerializedName(PLANT_PHASE_DURATION)
    private int phaseDuration;
    @SerializedName(PLANT_PHASE_DESCRIPTION)
    private String description;
    @SerializedName(PLANT_PHASE_IMAGE)
    private String image;

}

package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.PLANT_ID;
import static com.unimib.eden.utils.Constants.PLANT_NAME;
import static com.unimib.eden.utils.Constants.PLANT_DESCRIPTION;
import static com.unimib.eden.utils.Constants.PLANT_BOTANICAL_FAMILY;
import static com.unimib.eden.utils.Constants.PLANT_PHASES;
import static com.unimib.eden.utils.Constants.PLANT_WATERING_FREQUENCY;
import static com.unimib.eden.utils.Constants.PLANT_SOWING_PERIOD;
import static com.unimib.eden.utils.Constants.PLANT_SPACE_REQUIRED;
import static com.unimib.eden.utils.Constants.PLANT_MAX_HEIGHT;
import static com.unimib.eden.utils.Constants.PLANT_SUN_EXPOSURE ;
import static com.unimib.eden.utils.Constants.PLANT_SOIL_TYPE;
import static com.unimib.eden.utils.Constants.PLANT_IDEAL_TEMPERATURE;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Plant implements Serializable {

    @SerializedName(PLANT_ID)
    private String id;
    @SerializedName(PLANT_NAME)
    private String name;
    @SerializedName(PLANT_DESCRIPTION)
    private String description;
    @SerializedName(PLANT_BOTANICAL_FAMILY)
    private String botanicalFamily;
    @SerializedName(PLANT_PHASES)
    private List<Phase> phases;
    @SerializedName(PLANT_WATERING_FREQUENCY)
    private int wateringFrequency;
    @SerializedName(PLANT_SOWING_PERIOD)
    private List<Date> sowingPeriod;
    @SerializedName(PLANT_SPACE_REQUIRED)
    private Double spaceRequired;
    @SerializedName(PLANT_MAX_HEIGHT)
    private String maxHeight;
    @SerializedName(PLANT_SUN_EXPOSURE)
    private String sunExposure;
    @SerializedName(PLANT_SOIL_TYPE)
    private String soilType;
    @SerializedName(PLANT_IDEAL_TEMPERATURE)
    private String idealTemperature;
}



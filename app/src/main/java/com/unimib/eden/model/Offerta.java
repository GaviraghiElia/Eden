package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.ACQUIRENTE_OFFERTA;
import static com.unimib.eden.utils.Constants.PREZZO_OFFERTA;
import static com.unimib.eden.utils.Constants.STATO_OFFERTA;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
public class Offerta implements Serializable {
    @SerializedName(ACQUIRENTE_OFFERTA)
    private String acquirente;
    @SerializedName(PREZZO_OFFERTA)
    private int offerta;
    @SerializedName(STATO_OFFERTA)
    private int stato;
}

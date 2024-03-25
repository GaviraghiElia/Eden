package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.FASE_DESCRIZIONE;
import static com.unimib.eden.utils.Constants.FASE_DURATA_FASE;
import static com.unimib.eden.utils.Constants.FASE_IMMAGINE;
import static com.unimib.eden.utils.Constants.FASE_INIZIO_FASE;
import static com.unimib.eden.utils.Constants.FASE_NOME_FASE;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
public class Fase implements Serializable {

    @PrimaryKey
    private String id;
    @SerializedName(FASE_NOME_FASE)
    private String nomeFase;
    @SerializedName(FASE_INIZIO_FASE)
    private String inizioFase;
    @SerializedName(FASE_DURATA_FASE)
    private int durataFase;
    @SerializedName(FASE_DESCRIZIONE)
    private String descrizione;
    @SerializedName(FASE_IMMAGINE)
    private String immagine;

}

package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.PIANTA_FASE_DESCRIZIONE;
import static com.unimib.eden.utils.Constants.PIANTA_FASE_DURATA;
import static com.unimib.eden.utils.Constants.PIANTA_FASE_IMMAGINE;
import static com.unimib.eden.utils.Constants.PIANTA_FASE_NOME;
import static com.unimib.eden.utils.Constants.PIANTA_FASE_DATA_INIZIO;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Fase implements Serializable {

    @SerializedName(PIANTA_FASE_NOME)
    private String nomeFase;
    @SerializedName(PIANTA_FASE_DATA_INIZIO)
    private String dataInizioFase;
    @SerializedName(PIANTA_FASE_DURATA)
    private int durataFase;
    @SerializedName(PIANTA_FASE_DESCRIZIONE)
    private String descrizione;
    @SerializedName(PIANTA_FASE_IMMAGINE)
    private String immagine;

}

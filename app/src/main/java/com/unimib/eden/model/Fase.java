package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.FASE_DESCRIZIONE;
import static com.unimib.eden.utils.Constants.FASE_DURATA_FASE;
import static com.unimib.eden.utils.Constants.FASE_IMMAGINE;
import static com.unimib.eden.utils.Constants.FASE_INIZIO_FASE;
import static com.unimib.eden.utils.Constants.FASE_NOME_FASE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "fase")
public class Fase implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = FASE_NOME_FASE)
    private String nomeFase;
    @ColumnInfo(name = FASE_INIZIO_FASE)
    private String inizioFase;
    @ColumnInfo(name = FASE_DURATA_FASE)
    private int durataFase;
    @ColumnInfo(name = FASE_DESCRIZIONE)
    private String descrizione;
    @ColumnInfo(name = FASE_IMMAGINE)
    private String immagine;

    public Fase(@NonNull String id, String nomeFase, String inizioFase, int durataFase, String descrizione, String immagine) {
        this.id = id;
        this.nomeFase = nomeFase;
        this.inizioFase = inizioFase;
        this.durataFase = durataFase;
        this.descrizione = descrizione;
        this.immagine = immagine;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getNomeFase() {
        return nomeFase;
    }

    public void setNomeFase(String nomeFase) {
        this.nomeFase = nomeFase;
    }

    public String getInizioFase() {
        return inizioFase;
    }

    public void setInizioFase(String inizioFase) {
        this.inizioFase = inizioFase;
    }

    public int getDurataFase() {
        return durataFase;
    }

    public void setDurataFase(int durataFase) {
        this.durataFase = durataFase;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
}

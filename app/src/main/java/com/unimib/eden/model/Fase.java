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
import java.util.Objects;

/**
 * Classe Fase è il modello che rappresenta una fase.
 *
 * @author Alice Hoa Galli
 */
@Entity(tableName = "fase")
public class Fase implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = FASE_NOME_FASE)
    private String nomeFase;
    @ColumnInfo(name = FASE_INIZIO_FASE)
    private int inizioFase;
    @ColumnInfo(name = FASE_DURATA_FASE)
    private int durataFase;
    @ColumnInfo(name = FASE_DESCRIZIONE)
    private String descrizione;
    @ColumnInfo(name = FASE_IMMAGINE)
    private String immagine;

    /**
     * Costruttore per la classe Fase.
     *
     * @param id    L'Id della fase.
     * @param nomeFase  Il nome della fase.
     * @param inizioFase    Il mese dell'inizio della fase.
     * @param durataFase    Il numero di giorni rappresentante la durata della fase.
     * @param descrizione   La descrizione della fase.
     * @param immagine  La stringa rappresentante l'URL dell'immagine della fase.
     */
    public Fase(@NonNull String id, String nomeFase, int inizioFase, int durataFase, String descrizione, String immagine) {
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

    public int getInizioFase() {
        return inizioFase;
    }

    public void setInizioFase(int inizioFase) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fase fase = (Fase) o;
        return inizioFase == fase.inizioFase && durataFase == fase.durataFase && Objects.equals(id, fase.id) && Objects.equals(nomeFase, fase.nomeFase) && Objects.equals(descrizione, fase.descrizione) && Objects.equals(immagine, fase.immagine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeFase, inizioFase, durataFase, descrizione, immagine);
    }
}

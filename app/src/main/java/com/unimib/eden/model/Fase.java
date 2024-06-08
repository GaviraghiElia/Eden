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
 * Classe Fase è il modello che rappresenta una fase.
 */
@Entity(tableName = "fase")
public class Fase implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = PHASE_NAME)
    private String nomeFase;
    @ColumnInfo(name = PHASE_START)
    private int inizioFase;
    @ColumnInfo(name = PHASE_DURATION)
    private int durataFase;
    @ColumnInfo(name = PHASE_DESCRIPTION)
    private String descrizione;
    @ColumnInfo(name = PHASE_IMAGE)
    private String immagine;

    @ColumnInfo(name = PHASE_WATERING_FREQUENCY)
    private int frequenzaInnaffiamento;

    /**
     * Costruttore per la classe Fase.
     *
     * @param id    L'Id della fase.
     * @param nomeFase  Il nome della fase.
     * @param inizioFase    Il mese dell'inizio della fase.
     * @param durataFase    Il numero di giorni rappresentante la durata della fase.
     * @param descrizione   La descrizione della fase.
     * @param immagine  La stringa rappresentante l'URL dell'immagine della fase.
     * @param frequenzaInnaffiamento  Il numero di giorni che intercorrono tra un innaffiamento e l'altro.
     */
    public Fase(@NonNull String id, String nomeFase, int inizioFase, int durataFase, String descrizione, String immagine, int frequenzaInnaffiamento) {
        this.id = id;
        this.nomeFase = nomeFase;
        this.inizioFase = inizioFase;
        this.durataFase = durataFase;
        this.descrizione = descrizione;
        this.immagine = immagine;
        this.frequenzaInnaffiamento = frequenzaInnaffiamento;
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
    public int getFrequenzaInnaffiamento() {
        return frequenzaInnaffiamento;
    }

    public void setFrequenzaInnaffiamento(int frequenzaInnaffiamento) {
        this.frequenzaInnaffiamento = frequenzaInnaffiamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fase fase = (Fase) o;
        return getInizioFase() == fase.getInizioFase() && getDurataFase() == fase.getDurataFase() && getFrequenzaInnaffiamento() == fase.getFrequenzaInnaffiamento() && Objects.equals(getId(), fase.getId()) && Objects.equals(getNomeFase(), fase.getNomeFase()) && Objects.equals(getDescrizione(), fase.getDescrizione()) && Objects.equals(getImmagine(), fase.getImmagine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNomeFase(), getInizioFase(), getDurataFase(), getDescrizione(), getImmagine(), getFrequenzaInnaffiamento());
    }
}

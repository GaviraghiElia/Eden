package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.PIANTA_ALTEZZA_MAX_PREVISTA;
import static com.unimib.eden.utils.Constants.PIANTA_FINE_SEMINA;
import static com.unimib.eden.utils.Constants.PIANTA_INIZIO_SEMINA;
import static com.unimib.eden.utils.Constants.PIANTA_MAX_TEMPERATURA;
import static com.unimib.eden.utils.Constants.PIANTA_MIN_TEMPERATURA;
import static com.unimib.eden.utils.Constants.PIANTA_NOME;
import static com.unimib.eden.utils.Constants.PIANTA_DESCRIZIONE;
import static com.unimib.eden.utils.Constants.PIANTA_FAMIGLIA_BOTANICA;
import static com.unimib.eden.utils.Constants.PIANTA_FASE;
import static com.unimib.eden.utils.Constants.PIANTA_FREQUENZA_INNAFFIAMENTO;
import static com.unimib.eden.utils.Constants.PIANTA_SPAZIO_NECESSARIO;
import static com.unimib.eden.utils.Constants.PIANTA_ESPOSIZIONE_SOLE;
import static com.unimib.eden.utils.Constants.PIANTA_TIPO_TERRENO;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.unimib.eden.utils.Converters;

@Entity(tableName = "pianta")
public class Pianta implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;
    @ColumnInfo(name = PIANTA_NOME)
    private String nome;
    @ColumnInfo(name = PIANTA_DESCRIZIONE)
    private String descrizione;
    @ColumnInfo(name = PIANTA_FAMIGLIA_BOTANICA)
    private String famigliaBotanica;

    @ColumnInfo(name = PIANTA_INIZIO_SEMINA)
    private int inizioSemina;
    @ColumnInfo(name = PIANTA_FINE_SEMINA)
    private int fineSemina;
    @ColumnInfo(name = PIANTA_FREQUENZA_INNAFFIAMENTO)
    private int frequenzaInnaffiamento;
    @TypeConverters(Converters.class)
    @ColumnInfo(name = PIANTA_FASE)
    private ArrayList<String> fasi;
    @ColumnInfo(name = PIANTA_SPAZIO_NECESSARIO)
    private Double spazioNecessario;
    @ColumnInfo(name = PIANTA_ESPOSIZIONE_SOLE)
    private String esposizioneSole;
    @ColumnInfo(name = PIANTA_TIPO_TERRENO)
    private String tipoTerreno;
    @ColumnInfo(name = PIANTA_MIN_TEMPERATURA)
    private int minTemperatura;
    @ColumnInfo(name = PIANTA_MAX_TEMPERATURA)
    private int maxTemperatura;
    @ColumnInfo(name = PIANTA_ALTEZZA_MAX_PREVISTA)
    private double altezzaMaxPrevista;

    public Pianta(@NonNull String id, String nome, String descrizione, String famigliaBotanica, int inizioSemina, int fineSemina, int frequenzaInnaffiamento, ArrayList<String> fasi, Double spazioNecessario, String esposizioneSole, String tipoTerreno, int minTemperatura, int maxTemperatura, double altezzaMaxPrevista) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.famigliaBotanica = famigliaBotanica;
        this.inizioSemina = inizioSemina;
        this.fineSemina = fineSemina;
        this.frequenzaInnaffiamento = frequenzaInnaffiamento;
        this.fasi = fasi;
        this.spazioNecessario = spazioNecessario;
        this.esposizioneSole = esposizioneSole;
        this.tipoTerreno = tipoTerreno;
        this.minTemperatura = minTemperatura;
        this.maxTemperatura = maxTemperatura;
        this.altezzaMaxPrevista = altezzaMaxPrevista;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getFamigliaBotanica() {
        return famigliaBotanica;
    }

    public void setFamigliaBotanica(String famigliaBotanica) {
        this.famigliaBotanica = famigliaBotanica;
    }

    public int getInizioSemina() {
        return inizioSemina;
    }

    public void setInizioSemina(int inizioSemina) {
        this.inizioSemina = inizioSemina;
    }

    public int getFineSemina() {
        return fineSemina;
    }

    public void setFineSemina(int fineSemina) {
        this.fineSemina = fineSemina;
    }

    public int getFrequenzaInnaffiamento() {
        return frequenzaInnaffiamento;
    }

    public void setFrequenzaInnaffiamento(int frequenzaInnaffiamento) {
        this.frequenzaInnaffiamento = frequenzaInnaffiamento;
    }

    public ArrayList<String> getFasi() {
        return fasi;
    }

    public void setFasi(ArrayList<String> fasi) {
        this.fasi = fasi;
    }

    public Double getSpazioNecessario() {
        return spazioNecessario;
    }

    public void setSpazioNecessario(Double spazioNecessario) {
        this.spazioNecessario = spazioNecessario;
    }

    public String getEsposizioneSole() {
        return esposizioneSole;
    }

    public void setEsposizioneSole(String esposizioneSole) {
        this.esposizioneSole = esposizioneSole;
    }

    public String getTipoTerreno() {
        return tipoTerreno;
    }

    public void setTipoTerreno(String tipoTerreno) {
        this.tipoTerreno = tipoTerreno;
    }

    public int getMinTemperatura() {
        return minTemperatura;
    }

    public void setMinTemperatura(int minTemperatura) {
        this.minTemperatura = minTemperatura;
    }

    public int getMaxTemperatura() {
        return maxTemperatura;
    }

    public void setMaxTemperatura(int maxTemperatura) {
        this.maxTemperatura = maxTemperatura;
    }

    public double getAltezzaMaxPrevista() {
        return altezzaMaxPrevista;
    }

    public void setAltezzaMaxPrevista(double altezzaMaxPrevista) {
        this.altezzaMaxPrevista = altezzaMaxPrevista;
    }
}



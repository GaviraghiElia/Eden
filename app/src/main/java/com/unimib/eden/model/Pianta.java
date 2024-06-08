package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.PLANT_MAX_EXPECTED_HEIGHT;
import static com.unimib.eden.utils.Constants.PLANT_SOWING_END;
import static com.unimib.eden.utils.Constants.PLANT_SOWING_START;
import static com.unimib.eden.utils.Constants.PLANT_MAX_TEMPERATURE;
import static com.unimib.eden.utils.Constants.PLANT_MIN_TEMPERATURE;
import static com.unimib.eden.utils.Constants.PLANT_NAME;
import static com.unimib.eden.utils.Constants.PLANT_DESCRIPTION;
import static com.unimib.eden.utils.Constants.PLANT_BOTANICAL_FAMILY;
import static com.unimib.eden.utils.Constants.PLANT_PHASE;
import static com.unimib.eden.utils.Constants.PLANT_REQUIRED_SPACE;
import static com.unimib.eden.utils.Constants.PLANT_SUN_EXPOSURE;
import static com.unimib.eden.utils.Constants.PLANT_SOIL_TYPE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import com.unimib.eden.utils.Converters;

/**
 * Classe Pianta è il modello che rappresenta una pianta.
 */
@Entity(tableName = "pianta")
public class Pianta implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;
    @ColumnInfo(name = PLANT_NAME)
    private String nome;
    @ColumnInfo(name = PLANT_DESCRIPTION)
    private String descrizione;
    @ColumnInfo(name = PLANT_BOTANICAL_FAMILY)
    private String famigliaBotanica;
    @ColumnInfo(name = PLANT_SOWING_START)
    private int inizioSemina;
    @ColumnInfo(name = PLANT_SOWING_END)
    private int fineSemina;
    @TypeConverters(Converters.class)
    @ColumnInfo(name = PLANT_PHASE)
    private ArrayList<String> fasi;
    @ColumnInfo(name = PLANT_REQUIRED_SPACE)
    private Double spazioNecessario;
    @ColumnInfo(name = PLANT_SUN_EXPOSURE)
    private String esposizioneSole;
    @ColumnInfo(name = PLANT_SOIL_TYPE)
    private String tipoTerreno;
    @ColumnInfo(name = PLANT_MIN_TEMPERATURE)
    private int minTemperatura;
    @ColumnInfo(name = PLANT_MAX_TEMPERATURE)
    private int maxTemperatura;
    @ColumnInfo(name = PLANT_MAX_EXPECTED_HEIGHT)
    private Double altezzaMaxPrevista;

    /**
     * Il costruttore per la classe Pianta.
     *
     * @param id    L'Id della pianta.
     * @param nome  Il nome della pianta.
     * @param descrizione   La descrizione della pianta.
     * @param famigliaBotanica  La famiglia botanica alla quale appartiene la pianta.
     * @param inizioSemina  Il mese di inizio semina della pianta.
     * @param fineSemina    Il mese di fine semina della pianta.
     * @param fasi  La lista con gli Id delle fasi della pianta.
     * @param spazioNecessario  Lo spazio necessario alla pianta per poter crescere.
     * @param esposizioneSole   L'esposizione al sole richiesta dalla piata.
     * @param tipoTerreno   Il tipo di terreno necessario per la pianta.
     * @param minTemperatura    La temperatura minima alla quale può essere esposta la pianta.
     * @param maxTemperatura    La temperatura massima alla quale può essere esposta la pianta.
     * @param altezzaMaxPrevista    L'altezza massima che la pianta può raggiungere
     */
    public Pianta(@NonNull String id, String nome, String descrizione, String famigliaBotanica, int inizioSemina, int fineSemina, ArrayList<String> fasi, Double spazioNecessario, String esposizioneSole, String tipoTerreno, int minTemperatura, int maxTemperatura, Double altezzaMaxPrevista) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.famigliaBotanica = famigliaBotanica;
        this.inizioSemina = inizioSemina;
        this.fineSemina = fineSemina;
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

    public Double getAltezzaMaxPrevista() {
        return altezzaMaxPrevista;
    }

    public void setAltezzaMaxPrevista(Double altezzaMaxPrevista) {
        this.altezzaMaxPrevista = altezzaMaxPrevista;
    }

    @Override
    public String toString() {
        return "Pianta{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", famigliaBotanica='" + famigliaBotanica + '\'' +
                ", inizioSemina=" + inizioSemina +
                ", fineSemina=" + fineSemina +
                ", fasi=" + fasi +
                ", spazioNecessario=" + spazioNecessario +
                ", esposizioneSole='" + esposizioneSole + '\'' +
                ", tipoTerreno='" + tipoTerreno + '\'' +
                ", minTemperatura=" + minTemperatura +
                ", maxTemperatura=" + maxTemperatura +
                ", altezzaMaxPrevista=" + altezzaMaxPrevista +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pianta pianta = (Pianta) o;
        return getInizioSemina() == pianta.getInizioSemina() && getFineSemina() == pianta.getFineSemina() && getMinTemperatura() == pianta.getMinTemperatura() && getMaxTemperatura() == pianta.getMaxTemperatura() && Objects.equals(getId(), pianta.getId()) && Objects.equals(getNome(), pianta.getNome()) && Objects.equals(getDescrizione(), pianta.getDescrizione()) && Objects.equals(getFamigliaBotanica(), pianta.getFamigliaBotanica()) && Objects.equals(getFasi(), pianta.getFasi()) && Objects.equals(getSpazioNecessario(), pianta.getSpazioNecessario()) && Objects.equals(getEsposizioneSole(), pianta.getEsposizioneSole()) && Objects.equals(getTipoTerreno(), pianta.getTipoTerreno()) && Objects.equals(getAltezzaMaxPrevista(), pianta.getAltezzaMaxPrevista());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getDescrizione(), getFamigliaBotanica(), getInizioSemina(), getFineSemina(), getFasi(), getSpazioNecessario(), getEsposizioneSole(), getTipoTerreno(), getMinTemperatura(), getMaxTemperatura(), getAltezzaMaxPrevista());
    }
}



package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.ALTRE_INFORMAZIONI_PRODOTTO;
import static com.unimib.eden.utils.Constants.ID_PIANTA_PRODOTTO;
import static com.unimib.eden.utils.Constants.SCAMBIO_PRODOTTO;
import static com.unimib.eden.utils.Constants.PESO_PRODOTTO;
import static com.unimib.eden.utils.Constants.PREZZO_PRODOTTO;
import static com.unimib.eden.utils.Constants.STORIA_OFFERTE_PRODOTTO;
import static com.unimib.eden.utils.Constants.TIPO_PRODOTTO;
import static com.unimib.eden.utils.Constants.VENDITORE_PRODOTTO;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.util.List;

@Entity(tableName = "prodotti")
public class Prodotto implements Serializable {
    public Prodotto() {}

    @PrimaryKey @NonNull
    private String id;
    @ColumnInfo(name = TIPO_PRODOTTO)
    private String tipo;
    @ColumnInfo(name = VENDITORE_PRODOTTO)
    private String venditore;
    @ColumnInfo(name = PREZZO_PRODOTTO)
    private int prezzo;
    @ColumnInfo(name = ALTRE_INFORMAZIONI_PRODOTTO)
    private String altreInformazioni;
    @ColumnInfo(name = SCAMBIO_PRODOTTO)
    private boolean scambio;
    //@ColumnInfo(name = STORIA_OFFERTE_PRODOTTO)
    //private List<Offerta> storiaOfferte;
    @ColumnInfo(name = PESO_PRODOTTO)
    private int peso;
    @ColumnInfo(name = ID_PIANTA_PRODOTTO)
    private String idPianta;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setVenditore(String venditore) {
        this.venditore = venditore;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public void setAltreInformazioni(String altreInformazioni) {
        this.altreInformazioni = altreInformazioni;
    }

    public void setScambio(boolean scambio) {
        this.scambio = scambio;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public void setIdPianta(String idPianta) {
        this.idPianta = idPianta;
    }

    public String getTipo() {
        return tipo;
    }

    public String getVenditore() {
        return venditore;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public String getAltreInformazioni() {
        return altreInformazioni;
    }

    public boolean isScambio() {
        return scambio;
    }

    public int getPeso() {
        return peso;
    }

    public String getIdPianta() {
        return idPianta;
    }
}

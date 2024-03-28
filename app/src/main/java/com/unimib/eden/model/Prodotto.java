package com.unimib.eden.model;

import static android.content.ContentValues.TAG;
import static com.unimib.eden.utils.Constants.PRODOTTO_ALTRE_INFORMAZIONI;
import static com.unimib.eden.utils.Constants.PRODOTTO_FASE_ATTUALE;
import static com.unimib.eden.utils.Constants.PRODOTTO_PIANTA;
import static com.unimib.eden.utils.Constants.PRODOTTO_PREZZO;
import static com.unimib.eden.utils.Constants.PRODOTTO_QUANTITA;
import static com.unimib.eden.utils.Constants.PRODOTTO_STORIA_OFFERTE;
import static com.unimib.eden.utils.Constants.PRODOTTO_TIPO;
import static com.unimib.eden.utils.Constants.PRODOTTO_VENDITORE;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unimib.eden.utils.Converters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity(tableName = "prodotto")
public class Prodotto implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = PRODOTTO_TIPO)
    private String tipo;
    @ColumnInfo(name = PRODOTTO_VENDITORE)
    private String venditore;
    @ColumnInfo(name = PRODOTTO_PREZZO)
    private double prezzo;
    @ColumnInfo(name = PRODOTTO_PIANTA)
    private String pianta;
    @ColumnInfo(name = PRODOTTO_STORIA_OFFERTE)
    private ArrayList<String> offerte;
    @ColumnInfo(name = PRODOTTO_QUANTITA)
    private int quantita;
    @ColumnInfo(name = PRODOTTO_FASE_ATTUALE)
    private int faseAttuale;
    @ColumnInfo(name = PRODOTTO_ALTRE_INFORMAZIONI)
    private String altreInformazioni;

    public Prodotto(@NonNull String id, String tipo, String venditore, double prezzo, String pianta, ArrayList<String> offerte, int quantita, int faseAttuale, String altreInformazioni) {
        this.id = id;
        this.tipo = tipo;
        this.venditore = venditore;
        this.prezzo = prezzo;
        this.pianta = pianta;
        this.offerte = offerte;
        this.quantita = quantita;
        this.faseAttuale = faseAttuale;
        this.altreInformazioni = altreInformazioni;
    }

    public Prodotto(QueryDocumentSnapshot document) {
        this.id = document.getId();
        Map<String, Object> tempMap = document.getData();
        this.tipo = String.valueOf(tempMap.get(PRODOTTO_TIPO));
        this.venditore = String.valueOf(tempMap.get(PRODOTTO_VENDITORE));
        this.prezzo = Double.parseDouble(tempMap.get(PRODOTTO_PREZZO).toString());
        this.pianta = String.valueOf(tempMap.get(PRODOTTO_PIANTA));
        this.quantita = Integer.parseInt(tempMap.get(PRODOTTO_QUANTITA).toString());
        this.faseAttuale = Integer.parseInt(tempMap.get(PRODOTTO_FASE_ATTUALE).toString());
        this.altreInformazioni = String.valueOf(tempMap.get(PRODOTTO_ALTRE_INFORMAZIONI));
        this.offerte = (ArrayList<String>) (ArrayList) document.getData().get(PRODOTTO_STORIA_OFFERTE);
        //TODO: aggiungere Adapter tipo meeple e tipo gaia per fare la lista recycler che scorre
        //booleano per "scambio si/no". booleano per "coltura/eccedenza"
        //riempire offerte. altezza max prevista in Pianta è double e non int
        //è necessario copiare tutto il lavoro fatto anche per Offerta? SI
        //per riempire il db: mandare una stampa a chatgpt e farsi generare il codice per inserirne altre
        //parte grafica di prodotti, guardare se Gaia ha pushato le card
        //nella pipeline implementare anche la parte di artefatti, ovvero generare un .apk testato e funzionante
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getVenditore() {
        return venditore;
    }

    public void setVenditore(String venditore) {
        this.venditore = venditore;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public String getPianta() {
        return pianta;
    }

    public void setPianta(String pianta) {
        this.pianta = pianta;
    }

    public ArrayList<String> getOfferte() {
        return offerte;
    }

    public void setOfferte(ArrayList<String> offerte) {
        this.offerte = offerte;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public int getFaseAttuale() {
        return faseAttuale;
    }

    public void setFaseAttuale(int faseAttuale) {
        this.faseAttuale = faseAttuale;
    }

    public String getAltreInformazioni() {
        return altreInformazioni;
    }

    public void setAltreInformazioni(String altreInformazioni) {
        this.altreInformazioni = altreInformazioni;
    }

    @Override
    public String toString() {
        return "Prodotto{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                ", venditore='" + venditore + '\'' +
                ", prezzo=" + prezzo +
                ", pianta='" + pianta + '\'' +
                ", offerte=" + offerte +
                ", quantita=" + quantita +
                ", faseAttuale=" + faseAttuale +
                ", altreInformazioni='" + altreInformazioni + '\'' +
                '}';
    }
}

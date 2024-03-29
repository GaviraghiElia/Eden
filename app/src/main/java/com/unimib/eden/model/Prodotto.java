package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.PRODOTTO_ALTRE_INFORMAZIONI;
import static com.unimib.eden.utils.Constants.PRODOTTO_FASE_ATTUALE;
import static com.unimib.eden.utils.Constants.PRODOTTO_ID;
import static com.unimib.eden.utils.Constants.PRODOTTO_OFFERTE;
import static com.unimib.eden.utils.Constants.PRODOTTO_PIANTA;
import static com.unimib.eden.utils.Constants.PRODOTTO_PREZZO;
import static com.unimib.eden.utils.Constants.PRODOTTO_QUANTITA;
import static com.unimib.eden.utils.Constants.PRODOTTO_SCAMBIO_DISPONIBILE;
import static com.unimib.eden.utils.Constants.PRODOTTO_TIPO;
import static com.unimib.eden.utils.Constants.PRODOTTO_VENDITORE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
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
    @ColumnInfo(name = PRODOTTO_OFFERTE)
    private ArrayList<String> offerte;
    @ColumnInfo(name = PRODOTTO_QUANTITA)
    private int quantita;
    @ColumnInfo(name = PRODOTTO_FASE_ATTUALE)
    private int faseAttuale;
    @ColumnInfo(name = PRODOTTO_ALTRE_INFORMAZIONI)
    private String altreInformazioni;
    @ColumnInfo(name = PRODOTTO_SCAMBIO_DISPONIBILE)
    private Boolean scambioDisponibile;

    //costruttore partendo dai parametri
    public Prodotto(@NonNull String id, String tipo, String venditore, double prezzo, String pianta, ArrayList<String> offerte, int quantita, int faseAttuale, String altreInformazioni, Boolean scambioDisponibile) {
        this.id = id;
        this.tipo = tipo;
        this.venditore = venditore;
        this.prezzo = prezzo;
        this.pianta = pianta;
        this.offerte = offerte;
        this.quantita = quantita;
        this.faseAttuale = faseAttuale;
        this.altreInformazioni = altreInformazioni;
        this.scambioDisponibile = scambioDisponibile;
    }

    //costruttore partendo dal document
    public Prodotto(QueryDocumentSnapshot document) {
        this.id = document.getId();
        Map<String, Object> tempMap = document.getData();
        initFromMap(tempMap);
    }

    //costruttore partendo da una map
    public Prodotto(Map<String, Object> dataMap) {
        this.id = String.valueOf(dataMap.get(PRODOTTO_ID));
        initFromMap(dataMap);
    }

    private void initFromMap(Map<String, Object> dataMap) {
        this.tipo = String.valueOf(dataMap.get(PRODOTTO_TIPO));
        this.venditore = String.valueOf(dataMap.get(PRODOTTO_VENDITORE));
        this.prezzo = Double.parseDouble(dataMap.get(PRODOTTO_PREZZO).toString());
        this.pianta = String.valueOf(dataMap.get(PRODOTTO_PIANTA));
        this.quantita = Integer.parseInt(String.valueOf(dataMap.get(PRODOTTO_QUANTITA)));
        this.faseAttuale = Integer.parseInt(dataMap.get(PRODOTTO_FASE_ATTUALE).toString());
        this.altreInformazioni = String.valueOf(dataMap.get(PRODOTTO_ALTRE_INFORMAZIONI));
        this.scambioDisponibile = Boolean.parseBoolean(String.valueOf(dataMap.get(PRODOTTO_SCAMBIO_DISPONIBILE)));
        this.offerte = (ArrayList<String>) (ArrayList) dataMap.get(PRODOTTO_OFFERTE);
    }
        //TODO: unita di misura deve cambiare in base alla fase scelta (4->kg, 1...3->piante)
        //il bottone Invia è clickabile solo dopo aver inserito tutto.
        //dopo aver inserito un prodotto la schermata torna alla bancarella
        //quantita potrebbe essere intero o double a seconda di eccedenza o pianta. da decidere
        //tagliare le cifre dopo la virgola nei numeri interi in fase di inserimento
        //prendere la ricerca delle piante da Alice. controllo sulle fasi in base alla pianta
        //parte grafica di prodotti, guardare se Gaia ha pushato le card

        //nella pipeline implementare anche la parte di artefatti, ovvero generare un .apk testato e funzionante
        
        //**CARD BANCARELLA** aggiungere Adapter tipo meeple e tipo gaia per fare la lista recycler che scorre
        // è necessario copiare tutto il lavoro fatto anche per Offerta? SI
        //per riempire il db: mandare una stampa a chatgpt e farsi generare il codice per inserirne altre
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

    public Boolean getScambioDisponibile() {
        return scambioDisponibile;
    }

    public void setScambioDisponibile(Boolean scambioDisponibile) {
        this.scambioDisponibile = scambioDisponibile;
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
                ", scambioDisponibile=" + scambioDisponibile +
                '}';
    }
}

package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.PRODUCT_OTHER_INFORMATION;
import static com.unimib.eden.utils.Constants.PRODUCT_CURRENT_PHASE;
import static com.unimib.eden.utils.Constants.PRODUCT_ID;
import static com.unimib.eden.utils.Constants.PRODUCT_OFFERS;
import static com.unimib.eden.utils.Constants.PRODUCT_PLANT;
import static com.unimib.eden.utils.Constants.PRODUCT_PRICE;
import static com.unimib.eden.utils.Constants.PRODUCT_QUANTITY;
import static com.unimib.eden.utils.Constants.PRODUCT_EXCHANGE_AVAILABLE;
import static com.unimib.eden.utils.Constants.PRODUCT_TYPE;
import static com.unimib.eden.utils.Constants.PRODUCT_SELLER;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


/**
 * Classe modello che rappresenta un prodotto.
 */
@Entity(tableName = "prodotto")
public class Prodotto implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = PRODUCT_TYPE)
    private String tipo;
    @ColumnInfo(name = PRODUCT_SELLER)
    private String venditore;
    @ColumnInfo(name = PRODUCT_PRICE)
    private double prezzo;
    @ColumnInfo(name = PRODUCT_PLANT)
    private String pianta;
    @ColumnInfo(name = PRODUCT_OFFERS)
    private ArrayList<String> offerte;
    @ColumnInfo(name = PRODUCT_QUANTITY)
    private int quantita;
    @ColumnInfo(name = PRODUCT_CURRENT_PHASE)
    private String faseAttuale;
    @ColumnInfo(name = PRODUCT_OTHER_INFORMATION)
    private String altreInformazioni;
    @ColumnInfo(name = PRODUCT_EXCHANGE_AVAILABLE)
    private Boolean scambioDisponibile;

    /**
     * Costruttore per la classe Prodotto.
     *
     * @param id                ID del prodotto.
     * @param tipo              Tipo del prodotto.
     * @param venditore         Venditore del prodotto.
     * @param prezzo            Prezzo del prodotto.
     * @param pianta            Pianta associata al prodotto.
     * @param offerte           Lista delle offerte associate al prodotto.
     * @param quantita          Quantità del prodotto.
     * @param faseAttuale       Fase attuale del prodotto.
     * @param altreInformazioni Altre informazioni sul prodotto.
     * @param scambioDisponibile Indica se il prodotto è disponibile per lo scambio.
     */
    public Prodotto(@NonNull String id, String tipo, String venditore, double prezzo, String pianta, ArrayList<String> offerte, int quantita, String faseAttuale, String altreInformazioni, Boolean scambioDisponibile) {
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

    /**
     * Costruttore per la classe Prodotto partendo da un documento Firestore.
     *
     * @param document Il documento Firestore che rappresenta il prodotto.
     */
    public Prodotto(QueryDocumentSnapshot document) {
        this.id = document.getId();
        Map<String, Object> tempMap = document.getData();
        initFromMap(tempMap);
    }

    /**
     * Costruttore per la classe Prodotto partendo da una mappa di dati.
     *
     * @param dataMap La mappa di dati che rappresenta il prodotto.
     */
    public Prodotto(Map<String, Object> dataMap) {
        this.id = String.valueOf(dataMap.get(PRODUCT_ID));
        initFromMap(dataMap);
    }

    public void initFromMap(Map<String, Object> dataMap) {
        this.tipo = String.valueOf(dataMap.get(PRODUCT_TYPE));
        this.venditore = String.valueOf(dataMap.get(PRODUCT_SELLER));
        this.prezzo = Double.parseDouble(dataMap.get(PRODUCT_PRICE).toString());
        this.pianta = String.valueOf(dataMap.get(PRODUCT_PLANT));
        this.quantita = Integer.parseInt(String.valueOf(dataMap.get(PRODUCT_QUANTITY)));
        this.faseAttuale = String.valueOf(dataMap.get(PRODUCT_CURRENT_PHASE));
        this.altreInformazioni = String.valueOf(dataMap.get(PRODUCT_OTHER_INFORMATION));
        this.scambioDisponibile = Boolean.parseBoolean(String.valueOf(dataMap.get(PRODUCT_EXCHANGE_AVAILABLE)));
        this.offerte = (ArrayList<String>) (ArrayList) dataMap.get(PRODUCT_OFFERS);
    }

    @NonNull
    //metodi getter e setter

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

    public String getFaseAttuale() {
        return faseAttuale;
    }

    public void setFaseAttuale(String faseAttuale) {
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

    // Metodi equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prodotto prodotto = (Prodotto) o;
        return Double.compare(prezzo, prodotto.prezzo) == 0 && quantita == prodotto.quantita && Objects.equals(id, prodotto.id) && Objects.equals(tipo, prodotto.tipo) && Objects.equals(venditore, prodotto.venditore) && Objects.equals(pianta, prodotto.pianta) && Objects.equals(offerte, prodotto.offerte) && Objects.equals(faseAttuale, prodotto.faseAttuale) && Objects.equals(altreInformazioni, prodotto.altreInformazioni) && Objects.equals(scambioDisponibile, prodotto.scambioDisponibile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipo, venditore, prezzo, pianta, offerte, quantita, faseAttuale, altreInformazioni, scambioDisponibile);
    }
}

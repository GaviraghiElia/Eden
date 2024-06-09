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
public class Product implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = PRODUCT_TYPE)
    private String type;
    @ColumnInfo(name = PRODUCT_SELLER)
    private String seller;
    @ColumnInfo(name = PRODUCT_PRICE)
    private double price;
    @ColumnInfo(name = PRODUCT_PLANT)
    private String plant;
    @ColumnInfo(name = PRODUCT_OFFERS)
    private ArrayList<String> offers;
    @ColumnInfo(name = PRODUCT_QUANTITY)
    private int quantity;
    @ColumnInfo(name = PRODUCT_CURRENT_PHASE)
    private String currentPhase;
    @ColumnInfo(name = PRODUCT_OTHER_INFORMATION)
    private String otherInformation;
    @ColumnInfo(name = PRODUCT_EXCHANGE_AVAILABLE)
    private Boolean exchangeAvailable;

    /**
     * Costruttore per la classe Prodotto.
     *
     * @param id                ID del prodotto.
     * @param type              Tipo del prodotto.
     * @param seller            Venditore del prodotto.
     * @param price             Prezzo del prodotto.
     * @param plant             Pianta associata al prodotto.
     * @param offers            Lista delle offerte associate al prodotto.
     * @param quantity          Quantità del prodotto.
     * @param currentPhase      Fase attuale del prodotto.
     * @param otherInformation  Altre informazioni sul prodotto.
     * @param exchangeAvailable Indica se il prodotto è disponibile per lo scambio.
     */
    public Product(@NonNull String id, String type, String seller, double price, String plant, ArrayList<String> offers, int quantity, String currentPhase, String otherInformation, Boolean exchangeAvailable) {
        this.id = id;
        this.type = type;
        this.seller = seller;
        this.price = price;
        this.plant = plant;
        this.offers = offers;
        this.quantity = quantity;
        this.currentPhase = currentPhase;
        this.otherInformation = otherInformation;
        this.exchangeAvailable = exchangeAvailable;
    }

    /**
     * Costruttore per la classe Prodotto partendo da un documento Firestore.
     *
     * @param document Il documento Firestore che rappresenta il prodotto.
     */
    public Product(QueryDocumentSnapshot document) {
        this.id = document.getId();
        Map<String, Object> tempMap = document.getData();
        initFromMap(tempMap);
    }

    /**
     * Costruttore per la classe Prodotto partendo da una mappa di dati.
     *
     * @param dataMap La mappa di dati che rappresenta il prodotto.
     */
    public Product(Map<String, Object> dataMap) {
        this.id = String.valueOf(dataMap.get(PRODUCT_ID));
        initFromMap(dataMap);
    }

    public void initFromMap(Map<String, Object> dataMap) {
        this.type = String.valueOf(dataMap.get(PRODUCT_TYPE));
        this.seller = String.valueOf(dataMap.get(PRODUCT_SELLER));
        this.price = Double.parseDouble(dataMap.get(PRODUCT_PRICE).toString());
        this.plant = String.valueOf(dataMap.get(PRODUCT_PLANT));
        this.quantity = Integer.parseInt(String.valueOf(dataMap.get(PRODUCT_QUANTITY)));
        this.currentPhase = String.valueOf(dataMap.get(PRODUCT_CURRENT_PHASE));
        this.otherInformation = String.valueOf(dataMap.get(PRODUCT_OTHER_INFORMATION));
        this.exchangeAvailable = Boolean.parseBoolean(String.valueOf(dataMap.get(PRODUCT_EXCHANGE_AVAILABLE)));
        this.offers = (ArrayList<String>) dataMap.get(PRODUCT_OFFERS);
    }

    // Getter and Setter methods

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public ArrayList<String> getOffers() {
        return offers;
    }

    public void setOffers(ArrayList<String> offers) {
        this.offers = offers;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(String currentPhase) {
        this.currentPhase = currentPhase;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }

    public Boolean getExchangeAvailable() {
        return exchangeAvailable;
    }

    public void setExchangeAvailable(Boolean exchangeAvailable) {
        this.exchangeAvailable = exchangeAvailable;
    }

    @NonNull
    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", seller='" + seller + '\'' +
                ", price=" + price +
                ", plant='" + plant + '\'' +
                ", offers=" + offers +
                ", quantity=" + quantity +
                ", currentPhase='" + currentPhase + '\'' +
                ", otherInformation='" + otherInformation + '\'' +
                ", exchangeAvailable=" + exchangeAvailable +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                quantity == product.quantity &&
                id.equals(product.id) &&
                Objects.equals(type, product.type) &&
                Objects.equals(seller, product.seller) &&
                Objects.equals(plant, product.plant) &&
                Objects.equals(offers, product.offers) &&
                Objects.equals(currentPhase, product.currentPhase) &&
                Objects.equals(otherInformation, product.otherInformation) &&
                Objects.equals(exchangeAvailable, product.exchangeAvailable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, seller, price, plant, offers, quantity, currentPhase, otherInformation, exchangeAvailable);
    }
}

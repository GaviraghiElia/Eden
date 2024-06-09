package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.OFFER_BUYER;
import static com.unimib.eden.utils.Constants.OFFER_PRICE;
import static com.unimib.eden.utils.Constants.OFFER_STATUS;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unimib.eden.utils.Enum;

import java.util.Map;
import java.util.Objects;

/**
 * The Offer class represents an offer.
 */
public class Offer {
    @PrimaryKey
    private String id;
    @ColumnInfo(name = OFFER_BUYER)
    private String buyer;
    @ColumnInfo(name = OFFER_PRICE)
    private double price;
    @ColumnInfo(name = OFFER_STATUS)
    private Enum.OfferStatus offerStatus;

    /**
     * Constructor for creating an Offer object from a QueryDocumentSnapshot.
     *
     * @param document The QueryDocumentSnapshot representing the offer.
     */
    public Offer(QueryDocumentSnapshot document) {
        this.id = document.getId();
        Map<String, Object> tempMap = document.getData();
        this.buyer = String.valueOf(tempMap.get(OFFER_BUYER));
        this.price = Double.parseDouble(tempMap.get(OFFER_PRICE).toString());
        String statoPropostaString = String.valueOf(tempMap.get(OFFER_STATUS));
        this.offerStatus = Enum.OfferStatus.valueOf(statoPropostaString);
    }

    /**
     * Constructor for creating an Offer object.
     *
     * @param id          The ID of the offer.
     * @param buyer       The buyer associated with the offer.
     * @param price       The price of the offer.
     * @param offerStatus The status of the offer.
     */
    public Offer(String id, String buyer, double price, Enum.OfferStatus offerStatus) {
        this.id = id;
        this.buyer = buyer;
        this.price = price;
        this.offerStatus = offerStatus;
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Enum.OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(Enum.OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Double.compare(offer.price, price) == 0 && Objects.equals(id, offer.id) && Objects.equals(buyer, offer.buyer) && offerStatus == offer.offerStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, buyer, price, offerStatus);
    }

    @NonNull
    @Override
    public String toString() {
        return "Offer{" +
                "id='" + id + '\'' +
                ", buyer='" + buyer + '\'' +
                ", price=" + price +
                ", offerStatus=" + offerStatus +
                '}';
    }
}

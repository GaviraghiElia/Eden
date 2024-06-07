package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.OFFER_BUYER;
import static com.unimib.eden.utils.Constants.OFFER_PRICE;
import static com.unimib.eden.utils.Constants.OFFER_STATUS;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unimib.eden.utils.Enum;

import java.util.Map;
import java.util.Objects;

public class Offerta {
    @PrimaryKey
    private String id;
    @ColumnInfo(name = OFFER_BUYER)
    private String acquirente;
    @ColumnInfo(name = OFFER_PRICE)
    private double prezzo;
    @ColumnInfo(name = OFFER_STATUS)
    private Enum.StatoProposta statoPropostaEnum;

    public Offerta(QueryDocumentSnapshot document) {
        this.id = document.getId();
        Map<String, Object> tempMap = document.getData();
        this.acquirente = String.valueOf(tempMap.get(OFFER_BUYER));
        this.prezzo = Double.parseDouble(tempMap.get(OFFER_PRICE).toString());
        //da controllare
        String statoPropostaString = String.valueOf(tempMap.get(OFFER_STATUS));
        this.statoPropostaEnum = Enum.StatoProposta.valueOf(statoPropostaString);
    }

    public Offerta(String id, String acquirente, double prezzo, Enum.StatoProposta statoPropostaEnum) {
        this.id = id;
        this.acquirente = acquirente;
        this.prezzo = prezzo;
        this.statoPropostaEnum = statoPropostaEnum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAcquirente() {
        return acquirente;
    }

    public void setAcquirente(String acquirente) {
        this.acquirente = acquirente;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public Enum.StatoProposta getStatoPropostaEnum() {
        return statoPropostaEnum;
    }

    public void setStatoPropostaEnum(Enum.StatoProposta statoPropostaEnum) {
        this.statoPropostaEnum = statoPropostaEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offerta offerta = (Offerta) o;
        return Double.compare(prezzo, offerta.prezzo) == 0 && Objects.equals(id, offerta.id) && Objects.equals(acquirente, offerta.acquirente) && statoPropostaEnum == offerta.statoPropostaEnum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, acquirente, prezzo, statoPropostaEnum);
    }

    @Override
    public String toString() {
        return "Offerta{" +
                "id='" + id + '\'' +
                ", acquirente='" + acquirente + '\'' +
                ", prezzo=" + prezzo +
                ", statoPropostaEnum=" + statoPropostaEnum +
                '}';
    }
}
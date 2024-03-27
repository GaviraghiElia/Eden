package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.OFFERTA_ACQUIRENTE;
import static com.unimib.eden.utils.Constants.OFFERTA_PREZZO;
import static com.unimib.eden.utils.Constants.OFFERTA_STATO;
import static com.unimib.eden.utils.Constants.PRODOTTO_ALTRE_INFORMAZIONI;
import static com.unimib.eden.utils.Constants.PRODOTTO_FASE_ATTUALE;
import static com.unimib.eden.utils.Constants.PRODOTTO_PREZZO;
import static com.unimib.eden.utils.Constants.PRODOTTO_QUANTITA;
import static com.unimib.eden.utils.Constants.PRODOTTO_STORIA_OFFERTE;
import static com.unimib.eden.utils.Constants.PRODOTTO_TIPO;
import static com.unimib.eden.utils.Constants.PRODOTTO_VENDITORE;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unimib.eden.utils.Enum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Offerta {
    @PrimaryKey
    private String id;
    @ColumnInfo(name = OFFERTA_ACQUIRENTE)
    private String acquirente;
    @ColumnInfo(name = OFFERTA_PREZZO)
    private double prezzo;
    @ColumnInfo(name = OFFERTA_STATO)
    private Enum.StatoProposta statoPropostaEnum;

    public Offerta(QueryDocumentSnapshot document) {
        this.id = document.getId();
        Map<String, Object> tempMap = document.getData();
        this.acquirente = String.valueOf(tempMap.get(OFFERTA_ACQUIRENTE));
        this.prezzo = Double.parseDouble(tempMap.get(OFFERTA_PREZZO).toString());
        //da controllare
        String statoPropostaString = String.valueOf(tempMap.get(OFFERTA_STATO));
        this.statoPropostaEnum = Enum.StatoProposta.valueOf(statoPropostaString);
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
}
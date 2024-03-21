package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.ALTRE_INFORMAZIONI_PRODOTTO;
import static com.unimib.eden.utils.Constants.ID_PIANTA_PRODOTTO;
import static com.unimib.eden.utils.Constants.SCAMBIO_PRODOTTO;
import static com.unimib.eden.utils.Constants.PESO_PRODOTTO;
import static com.unimib.eden.utils.Constants.PREZZO_PRODOTTO;
import static com.unimib.eden.utils.Constants.STORIA_OFFERTE_PRODOTTO;
import static com.unimib.eden.utils.Constants.TIPO_PRODOTTO;
import static com.unimib.eden.utils.Constants.VENDITORE_PRODOTTO;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "prodotti")
public class Prodotto implements Serializable {
    @PrimaryKey
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
    @ColumnInfo(name = STORIA_OFFERTE_PRODOTTO)
    private List<Offerta> storiaOfferte;
    @ColumnInfo(name = PESO_PRODOTTO)
    private int peso;
    @ColumnInfo(name = ID_PIANTA_PRODOTTO)
    private String idPianta;
}

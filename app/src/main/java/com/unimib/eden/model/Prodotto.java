package com.unimib.eden.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

public class Prodotto {
    @PrimaryKey
    private String id;
    @ColumnInfo(name = "tipo")
    private String tipo;
    @ColumnInfo(name = "venditore")
    private String venditore;
    @ColumnInfo(name = "pianta")
    private String pianta;
    @ColumnInfo(name = "storia_offerte")
    private List<Offerta> storiaOfferte;
    @ColumnInfo(name = "quantita")
    private int quantita;
    @ColumnInfo(name = "fase_attuale")
    private int faseAttuale;
    @ColumnInfo(name = "altre_informazioni")
    private String altreInformazioni;
}

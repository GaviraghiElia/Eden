package com.unimib.eden.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.unimib.eden.utils.Enum;

public class Offerta {
    @PrimaryKey
    private String id;
    @ColumnInfo(name = "acquirente")
    private String acquirente;
    @ColumnInfo(name = "prezzo")
    private int prezzo;
    @ColumnInfo(name = "stato")
    private Enum.StatoProposta statoPropostaEnum;
}

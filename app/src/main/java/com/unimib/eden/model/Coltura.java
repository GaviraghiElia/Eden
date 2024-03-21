package com.unimib.eden.model;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "colture")
public class Coltura implements Serializable {

    @PrimaryKey
    private String id;
    @ColumnInfo(name = "pianta")
    private String idPianta;
    @ColumnInfo(name = "quantita")
    private int quantita;
    @ColumnInfo(name = "note")
    private String note;
    @ColumnInfo(name = "data_inserimento")
    private Date dataInserimento;
    @ColumnInfo(name = "fase_attuale")
    private int faseAttuale;
    @ColumnInfo(name = "data_ultimo_innaffiamento")
    private Date dataUltimoInnaffiamento;

}

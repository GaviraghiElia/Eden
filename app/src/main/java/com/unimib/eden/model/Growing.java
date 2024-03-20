package com.unimib.eden.model;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "colture")
public class Growing implements Serializable {

    @PrimaryKey
    private String id;
    @ColumnInfo(name = "pianta")
    private String plant_id;
    @ColumnInfo(name = "quantita")
    private int quantity;
    @ColumnInfo(name = "note")
    private String notes;
    @ColumnInfo(name = "data_inserimento")
    private Date insertDate;
    @ColumnInfo(name = "fase_attuale")
    private int currentPhase;
    @ColumnInfo(name = "data_ultimo_innaffiamento")
    private Date lastWateringDate;

}

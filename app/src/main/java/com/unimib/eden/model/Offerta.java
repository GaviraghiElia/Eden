package com.unimib.eden.model;

<<<<<<< HEAD
import static com.unimib.eden.utils.Constants.ACQUIRENTE_OFFERTA;
import static com.unimib.eden.utils.Constants.PREZZO_OFFERTA;
import static com.unimib.eden.utils.Constants.STATO_OFFERTA;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
public class Offerta implements Serializable {
    @SerializedName(ACQUIRENTE_OFFERTA)
    private String acquirente;
    @SerializedName(PREZZO_OFFERTA)
    private int offerta;
    @SerializedName(STATO_OFFERTA)
    private int stato;
=======
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
>>>>>>> origin/sprint_1
}

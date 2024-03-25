package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.PIANTA_ALTEZZA_MAX_PREVISTA;
import static com.unimib.eden.utils.Constants.PIANTA_FINE_SEMINA;
import static com.unimib.eden.utils.Constants.PIANTA_INIZIO_SEMINA;
import static com.unimib.eden.utils.Constants.PIANTA_MAX_TEMPERATURA;
import static com.unimib.eden.utils.Constants.PIANTA_MIN_TEMPERATURA;
import static com.unimib.eden.utils.Constants.PIANTA_NOME;
import static com.unimib.eden.utils.Constants.PIANTA_DESCRIZIONE;
import static com.unimib.eden.utils.Constants.PIANTA_FAMIGLIA_BOTANICA;
import static com.unimib.eden.utils.Constants.PIANTA_FASE;
import static com.unimib.eden.utils.Constants.PIANTA_FREQUENZA_INNAFFIAMENTO;
import static com.unimib.eden.utils.Constants.PIANTA_SPAZIO_NECESSARIO;
import static com.unimib.eden.utils.Constants.PIANTA_ESPOSIZIONE_SOLE;
import static com.unimib.eden.utils.Constants.PIANTA_TIPO_TERRENO;

import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Pianta implements Serializable {

    @PrimaryKey
    private String id;
    @SerializedName(PIANTA_NOME)
    private String nome;
    @SerializedName(PIANTA_DESCRIZIONE)
    private String descrizione;
    @SerializedName(PIANTA_FAMIGLIA_BOTANICA)
    private String famigliaBotanica;

    @SerializedName(PIANTA_INIZIO_SEMINA)
    private int inizioSemina;
    @SerializedName(PIANTA_FINE_SEMINA)
    private int fineSemina;
    @SerializedName(PIANTA_FREQUENZA_INNAFFIAMENTO)
    private int frequenzaInnaffiamento;
    @SerializedName(PIANTA_FASE)
    private List<Fase> fasi;
    @SerializedName(PIANTA_SPAZIO_NECESSARIO)
    private Double spazioNecessario;
    @SerializedName(PIANTA_ESPOSIZIONE_SOLE)
    private String esposizioneSole;
    @SerializedName(PIANTA_TIPO_TERRENO)
    private String tipoTerreno;
    @SerializedName(PIANTA_MIN_TEMPERATURA)
    private String minTemperatura;
    @SerializedName(PIANTA_MAX_TEMPERATURA)
    private String maxTemperatura;
    @SerializedName(PIANTA_ALTEZZA_MAX_PREVISTA)
    private String altezzaMaxPrevista;
}



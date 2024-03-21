package com.unimib.eden.model;

import static com.unimib.eden.utils.Constants.PIANTA_ID;
import static com.unimib.eden.utils.Constants.PIANTA_NOME;
import static com.unimib.eden.utils.Constants.PIANTA_DESCRIZIONE;
import static com.unimib.eden.utils.Constants.PIANTA_FAMIGLIA_BOTANICA;
import static com.unimib.eden.utils.Constants.PIANTA_FASE;
import static com.unimib.eden.utils.Constants.PIANTA_FREQUENZA_INNAFFIAMENTO;
import static com.unimib.eden.utils.Constants.PIANTA_PERIODO_SEMINA;
import static com.unimib.eden.utils.Constants.PIANTA_SPAZIO_NECESSARIO;
import static com.unimib.eden.utils.Constants.PIANTA_ALTEZZA_IDEALE;
import static com.unimib.eden.utils.Constants.PIANTA_ESPOSIZIONE_SOLE;
import static com.unimib.eden.utils.Constants.PIANTA_TIPO_TERRENO;
import static com.unimib.eden.utils.Constants.PIANTA_TEMPERATURA_IDEALE;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Pianta implements Serializable {

    @SerializedName(PIANTA_ID)
    private String id;
    @SerializedName(PIANTA_NOME)
    private String nome;
    @SerializedName(PIANTA_DESCRIZIONE)
    private String descrizione;
    @SerializedName(PIANTA_FAMIGLIA_BOTANICA)
    private String famigliaBotanica;
    @SerializedName(PIANTA_FASE)
    private List<Fase> fase;
    @SerializedName(PIANTA_FREQUENZA_INNAFFIAMENTO)
    private int frequenzaInnaffiamento;
    @SerializedName(PIANTA_PERIODO_SEMINA)
    private List<Date> periodoSemina;
    @SerializedName(PIANTA_SPAZIO_NECESSARIO)
    private Double spazioNecessario;
    @SerializedName(PIANTA_ESPOSIZIONE_SOLE)
    private String esposizioneSole;
    @SerializedName(PIANTA_TIPO_TERRENO)
    private String tipoTerreno;
    @SerializedName(PIANTA_TEMPERATURA_IDEALE)
    private String temperaturaIdealeRange;
    private transient String temperaturaIdealeMin = temperaturaIdealeRange != null ? temperaturaIdealeRange.split("-")[0] : null;
    private transient String temperaturaIdealeMax = temperaturaIdealeRange != null ? temperaturaIdealeRange.split("-")[1] : null;
    @SerializedName(PIANTA_ALTEZZA_IDEALE)
    private String altezzaIdealeRange;
    private transient String altezzaIdealeMin = altezzaIdealeRange != null ? altezzaIdealeRange.split("-")[0] : null;
    private transient String altezzaIdealeMax = altezzaIdealeRange != null ? altezzaIdealeRange.split("-")[1] : null;

}



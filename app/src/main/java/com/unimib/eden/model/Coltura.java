package com.unimib.eden.model;


import static com.unimib.eden.utils.Constants.COLTURA_DATA_INSERIMENTO;
import static com.unimib.eden.utils.Constants.COLTURA_FASE_ATTUALE;
import static com.unimib.eden.utils.Constants.COLTURA_NOTE;
import static com.unimib.eden.utils.Constants.COLTURA_PIANTA;
import static com.unimib.eden.utils.Constants.COLTURA_PROPRIETARIO;
import static com.unimib.eden.utils.Constants.COLTURA_QUANTITA;
import static com.unimib.eden.utils.Constants.COLTURA_ULTIMO_INNAFFIAMENTO;
import static com.unimib.eden.utils.Constants.NOME_DATABASE_COLTURA;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Entity(tableName = NOME_DATABASE_COLTURA)
public class Coltura implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;
    @ColumnInfo(name = COLTURA_PIANTA)
    private String idPianta;
    @ColumnInfo(name = COLTURA_PROPRIETARIO)
    private String proprietario;
    @ColumnInfo(name = COLTURA_QUANTITA)
    private int quantita;
    @ColumnInfo(name = COLTURA_NOTE)
    private String note;
    @ColumnInfo(name = COLTURA_DATA_INSERIMENTO)
    private Date dataInserimento;
    @ColumnInfo(name = COLTURA_FASE_ATTUALE)
    private int faseAttuale;
    @ColumnInfo(name = COLTURA_ULTIMO_INNAFFIAMENTO)
    private Date ultimoInnaffiamento;

    public Coltura(@NonNull String id, String idPianta, String proprietario, int quantita, String note, Date dataInserimento, int faseAttuale, Date ultimoInnaffiamento) {
        this.id = id;
        this.idPianta = idPianta;
        this.proprietario = proprietario;
        this.quantita = quantita;
        this.note = note;
        this.dataInserimento = dataInserimento;
        this.faseAttuale = faseAttuale;
        this.ultimoInnaffiamento = ultimoInnaffiamento;
    }

    public Coltura(QueryDocumentSnapshot document) {
        this.id = document.getId();
        Map<String, Object> tempMap = document.getData();
        this.idPianta = String.valueOf(tempMap.get(COLTURA_PIANTA));
        this.proprietario = String.valueOf(tempMap.get(COLTURA_PROPRIETARIO));
        this.quantita = Integer.parseInt(tempMap.get(COLTURA_QUANTITA).toString());
        this.note = String.valueOf(tempMap.get(COLTURA_NOTE));
        Timestamp dataInserimento = (Timestamp) tempMap.get(COLTURA_DATA_INSERIMENTO);
        this.dataInserimento = dataInserimento.toDate();
        this.faseAttuale = Integer.parseInt(tempMap.get(COLTURA_QUANTITA).toString());
        Timestamp ultimoInnaffiamento = (Timestamp) tempMap.get(COLTURA_ULTIMO_INNAFFIAMENTO);
        this.ultimoInnaffiamento = ultimoInnaffiamento.toDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coltura coltura = (Coltura) o;
        return getQuantita() == coltura.getQuantita() && getFaseAttuale() == coltura.getFaseAttuale() && Objects.equals(getId(), coltura.getId()) && Objects.equals(getIdPianta(), coltura.getIdPianta()) && Objects.equals(getProprietario(), coltura.getProprietario()) && Objects.equals(getNote(), coltura.getNote()) && Objects.equals(getDataInserimento(), coltura.getDataInserimento()) && Objects.equals(getUltimoInnaffiamento(), coltura.getUltimoInnaffiamento());
    }

    public boolean equals(QueryDocumentSnapshot document) {
        Coltura o = new Coltura(document);
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coltura coltura = (Coltura) o;
        return getQuantita() == coltura.getQuantita() && getFaseAttuale() == coltura.getFaseAttuale() && Objects.equals(getId(), coltura.getId()) && Objects.equals(getIdPianta(), coltura.getIdPianta()) && Objects.equals(getProprietario(), coltura.getProprietario()) && Objects.equals(getNote(), coltura.getNote()) && Objects.equals(getDataInserimento(), coltura.getDataInserimento()) && Objects.equals(getUltimoInnaffiamento(), coltura.getUltimoInnaffiamento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getIdPianta(), getProprietario(), getQuantita(), getNote(), getDataInserimento(), getFaseAttuale(), getUltimoInnaffiamento());
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getIdPianta() {
        return idPianta;
    }

    public void setIdPianta(String idPianta) {
        this.idPianta = idPianta;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public int getFaseAttuale() {
        return faseAttuale;
    }

    public void setFaseAttuale(int faseAttuale) {
        this.faseAttuale = faseAttuale;
    }

    public Date getUltimoInnaffiamento() {
        return ultimoInnaffiamento;
    }

    public void setUltimoInnaffiamento(Date ultimoInnaffiamento) {
        this.ultimoInnaffiamento = ultimoInnaffiamento;
    }
}

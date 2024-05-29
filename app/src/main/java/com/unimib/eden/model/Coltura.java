package com.unimib.eden.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unimib.eden.utils.Converters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static com.unimib.eden.utils.Constants.COLTURA_DATA_INSERIMENTO;
import static com.unimib.eden.utils.Constants.COLTURA_FASE_ATTUALE;
import static com.unimib.eden.utils.Constants.COLTURA_FREQUENZA_INNAFFIAMENTO_ATTUALE;
import static com.unimib.eden.utils.Constants.COLTURA_ID;
import static com.unimib.eden.utils.Constants.COLTURA_NOTE;
import static com.unimib.eden.utils.Constants.COLTURA_PIANTA;
import static com.unimib.eden.utils.Constants.COLTURA_PROPRIETARIO;
import static com.unimib.eden.utils.Constants.COLTURA_QUANTITA;
import static com.unimib.eden.utils.Constants.COLTURA_ULTIMO_INNAFFIAMENTO;
import static com.unimib.eden.utils.Constants.NOME_DATABASE_COLTURA;
import static com.unimib.eden.utils.Constants.COLTURA_FREQUENZA_INNAFFIAMENTO;
import static com.unimib.eden.utils.Constants.PIANTA_NOME;

/**
 * Classe modello che rappresenta una coltura.
 */
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
    @ColumnInfo(name = PIANTA_NOME)
    private String nomePianta;
    @TypeConverters(Converters.class)
    @ColumnInfo(name = COLTURA_FREQUENZA_INNAFFIAMENTO)
    private ArrayList<Integer> frequenzaInnaffiamento;

    @ColumnInfo(name = COLTURA_FREQUENZA_INNAFFIAMENTO_ATTUALE)
    private int frequenzaInnaffiamentoAttuale;

    /**
     * Costruttore per la classe Coltura.
     *
     * @param id                 ID della coltura.
     * @param idPianta           ID della pianta associata alla coltura.
     * @param proprietario       Proprietario della coltura.
     * @param quantita           Quantità della coltura.
     * @param note               Note sulla coltura.
     * @param dataInserimento    Data di inserimento della coltura.
     * @param faseAttuale        Fase attuale della coltura.
     * @param ultimoInnaffiamento Data dell'ultimo innaffiamento.
     * @param nomePianta         Nome della pianta associata alla coltura.
     * @param frequenzaInnaffiamento Frequenze di innaffiamento della pianta in base alla fase.
     */
    public Coltura(@NonNull String id, String idPianta, String proprietario, int quantita, String note, Date dataInserimento, int faseAttuale, Date ultimoInnaffiamento, String nomePianta, ArrayList<Integer> frequenzaInnaffiamento, int frequenzaInnaffiamentoAttuale) {
        this.id = id;
        this.idPianta = idPianta;
        this.proprietario = proprietario;
        this.quantita = quantita;
        this.note = note;
        this.dataInserimento = dataInserimento;
        this.faseAttuale = faseAttuale;
        this.ultimoInnaffiamento = ultimoInnaffiamento;
        this.nomePianta = nomePianta;
        this.frequenzaInnaffiamento = frequenzaInnaffiamento;
        this.frequenzaInnaffiamentoAttuale = frequenzaInnaffiamentoAttuale;
    }

    /**
     * Costruttore per la classe Coltura da un documento Firestore.
     *
     * @param document Il documento Firestore che rappresenta la coltura.
     */
    public Coltura(QueryDocumentSnapshot document) {
        this.id = document.getId();
        Map<String, Object> tempMap = document.getData();
        this.idPianta = String.valueOf(tempMap.get(COLTURA_PIANTA));
        this.proprietario = String.valueOf(tempMap.get(COLTURA_PROPRIETARIO));
        this.quantita = Integer.parseInt(tempMap.get(COLTURA_QUANTITA).toString());
        this.note = String.valueOf(tempMap.get(COLTURA_NOTE));
        Timestamp dataInserimento = (Timestamp) tempMap.get(COLTURA_DATA_INSERIMENTO);
        this.dataInserimento = dataInserimento.toDate();
        this.faseAttuale = Integer.parseInt(tempMap.get(COLTURA_FASE_ATTUALE).toString());
        Timestamp ultimoInnaffiamento = (Timestamp) tempMap.get(COLTURA_ULTIMO_INNAFFIAMENTO);
        this.ultimoInnaffiamento = ultimoInnaffiamento.toDate();
        this.nomePianta = String.valueOf(tempMap.get(PIANTA_NOME));
        this.frequenzaInnaffiamento = (ArrayList) document.getData().get(COLTURA_FREQUENZA_INNAFFIAMENTO);
        this.frequenzaInnaffiamentoAttuale = Integer.parseInt(tempMap.get(COLTURA_FREQUENZA_INNAFFIAMENTO_ATTUALE).toString());
    }

    /**
     * Costruttore per la classe Coltura partendo da una mappa di dati.
     *
     * @param dataMap La mappa di dati che rappresenta la coltura.
     */
    public Coltura(Map<String, Object> dataMap) {
        this.id = String.valueOf(dataMap.get(COLTURA_ID));
        initFromMap(dataMap);
    }

    public void initFromMap(Map<String, Object> tempMap) {
        this.idPianta = String.valueOf(tempMap.get(COLTURA_PIANTA));
        this.proprietario = String.valueOf(tempMap.get(COLTURA_PROPRIETARIO));
        this.quantita = Integer.parseInt(tempMap.get(COLTURA_QUANTITA).toString());
        this.note = String.valueOf(tempMap.get(COLTURA_NOTE));
        Timestamp dataInserimento = (Timestamp) tempMap.get(COLTURA_DATA_INSERIMENTO);
        this.dataInserimento = dataInserimento.toDate();
        this.faseAttuale = Integer.parseInt(tempMap.get(COLTURA_FASE_ATTUALE).toString());
        Timestamp ultimoInnaffiamento = (Timestamp) tempMap.get(COLTURA_ULTIMO_INNAFFIAMENTO);
        this.ultimoInnaffiamento = ultimoInnaffiamento.toDate();
        this.nomePianta = String.valueOf(tempMap.get(PIANTA_NOME));
        //this.frequenzaInnaffiamento = (ArrayList) document.getData().get(COLTURA_FREQUENZA_INNAFFIAMENTO);
        //TODO: la prossima riga potrebbe generare errori
        this.frequenzaInnaffiamento = (ArrayList<Integer>) (ArrayList) tempMap.get(COLTURA_FREQUENZA_INNAFFIAMENTO);
        this.frequenzaInnaffiamentoAttuale = Integer.parseInt(tempMap.get(COLTURA_FREQUENZA_INNAFFIAMENTO_ATTUALE).toString());
    }

    // Metodi getter e setter

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

    public String getNomePianta() {
        return nomePianta;
    }

    public void setNomePianta(String nomePianta) {
        this.nomePianta = nomePianta;
    }

    public ArrayList<Integer> getFrequenzaInnaffiamento() {
        return frequenzaInnaffiamento;
    }

    public void setFrequenzaInnaffiamento(ArrayList<Integer> frequenzaInnaffiamento) {
        this.frequenzaInnaffiamento = frequenzaInnaffiamento;
    }

    public int getFrequenzaInnaffiamentoAttuale() {
        return frequenzaInnaffiamentoAttuale;
    }

    public void setFrequenzaInnaffiamentoAttuale(int frequenzaInnaffiamentoAttuale) {
        this.frequenzaInnaffiamentoAttuale = frequenzaInnaffiamentoAttuale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coltura coltura = (Coltura) o;
        return quantita == coltura.quantita && faseAttuale == coltura.faseAttuale && frequenzaInnaffiamentoAttuale == coltura.frequenzaInnaffiamentoAttuale && Objects.equals(id, coltura.id) && Objects.equals(idPianta, coltura.idPianta) && Objects.equals(proprietario, coltura.proprietario) && Objects.equals(note, coltura.note) && Objects.equals(dataInserimento, coltura.dataInserimento) && Objects.equals(ultimoInnaffiamento, coltura.ultimoInnaffiamento) && Objects.equals(nomePianta, coltura.nomePianta) && Objects.equals(frequenzaInnaffiamento, coltura.frequenzaInnaffiamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idPianta, proprietario, quantita, note, dataInserimento, faseAttuale, ultimoInnaffiamento, nomePianta, frequenzaInnaffiamento, frequenzaInnaffiamentoAttuale);
    }

}

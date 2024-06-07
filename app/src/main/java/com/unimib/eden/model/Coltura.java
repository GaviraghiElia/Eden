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

import static com.unimib.eden.utils.Constants.CROPS_INSERTION_DATE;
import static com.unimib.eden.utils.Constants.CROPS_CURRENT_PHASE;
import static com.unimib.eden.utils.Constants.CROPS_CURRENT_WATERING_FREQUENCY;
import static com.unimib.eden.utils.Constants.CROPS_ID;
import static com.unimib.eden.utils.Constants.CROPS_NOTES;
import static com.unimib.eden.utils.Constants.CROPS_PLANT;
import static com.unimib.eden.utils.Constants.CROPS_OWNER;
import static com.unimib.eden.utils.Constants.CROPS_QUANTITY;
import static com.unimib.eden.utils.Constants.CROPS_LAST_WATERING;
import static com.unimib.eden.utils.Constants.CROP_DATABASE_NAME;
import static com.unimib.eden.utils.Constants.CROPS_WATERING_FREQUENCY;
import static com.unimib.eden.utils.Constants.PLANT_NAME;

/**
 * Classe modello che rappresenta una coltura.
 */
@Entity(tableName = CROP_DATABASE_NAME)
public class Coltura implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = CROPS_PLANT)
    private String idPianta;

    @ColumnInfo(name = CROPS_OWNER)
    private String proprietario;

    @ColumnInfo(name = CROPS_QUANTITY)
    private int quantita;

    @ColumnInfo(name = CROPS_NOTES)
    private String note;

    @ColumnInfo(name = CROPS_INSERTION_DATE)
    private Date dataInserimento;

    @ColumnInfo(name = CROPS_CURRENT_PHASE)
    private int faseAttuale;

    @ColumnInfo(name = CROPS_LAST_WATERING)
    private Date ultimoInnaffiamento;
    @ColumnInfo(name = PLANT_NAME)
    private String nomePianta;
    @TypeConverters(Converters.class)
    @ColumnInfo(name = CROPS_WATERING_FREQUENCY)
    private ArrayList<Integer> frequenzaInnaffiamento;

    @ColumnInfo(name = CROPS_CURRENT_WATERING_FREQUENCY)
    private int frequenzaInnaffiamentoAttuale;

    // Parametro interno locale che segnala quando la coltura è stata aggiornata rispetto al meteo
    private Date ultimoAggiornamento;

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
        // La data di ultimo aggiornamento è inizializzata al giorno precedente, così da permettere di aggiornare in base al meteo anche al primo inserimento
        this.ultimoAggiornamento = new Date((new Date()).getTime() - 1 * 24 * 60 * 60 * 1000);
    }

    /**
     * Costruttore per la classe Coltura da un documento Firestore.
     *
     * @param document Il documento Firestore che rappresenta la coltura.
     */
    public Coltura(QueryDocumentSnapshot document) {
        this.id = document.getId();
        Map<String, Object> tempMap = document.getData();
        this.idPianta = String.valueOf(tempMap.get(CROPS_PLANT));
        this.proprietario = String.valueOf(tempMap.get(CROPS_OWNER));
        this.quantita = Integer.parseInt(tempMap.get(CROPS_QUANTITY).toString());
        this.note = String.valueOf(tempMap.get(CROPS_NOTES));
        Timestamp dataInserimento = (Timestamp) tempMap.get(CROPS_INSERTION_DATE);
        this.dataInserimento = dataInserimento.toDate();
        this.faseAttuale = Integer.parseInt(tempMap.get(CROPS_CURRENT_PHASE).toString());
        Timestamp ultimoInnaffiamento = (Timestamp) tempMap.get(CROPS_LAST_WATERING);
        this.ultimoInnaffiamento = ultimoInnaffiamento.toDate();
        this.nomePianta = String.valueOf(tempMap.get(PLANT_NAME));
        this.frequenzaInnaffiamento = (ArrayList) document.getData().get(CROPS_WATERING_FREQUENCY);
        this.frequenzaInnaffiamentoAttuale = Integer.parseInt(tempMap.get(CROPS_CURRENT_WATERING_FREQUENCY).toString());
        this.ultimoAggiornamento = new Date((new Date()).getTime() - 1 * 24 * 60 * 60 * 1000);
    }

    /**
     * Costruttore per la classe Coltura partendo da una mappa di dati.
     *
     * @param dataMap La mappa di dati che rappresenta la coltura.
     */
    public Coltura(Map<String, Object> dataMap) {
        this.id = String.valueOf(dataMap.get(CROPS_ID));
        initFromMap(dataMap);
    }

    public void initFromMap(Map<String, Object> tempMap) {
        this.idPianta = String.valueOf(tempMap.get(CROPS_PLANT));
        this.proprietario = String.valueOf(tempMap.get(CROPS_OWNER));
        this.quantita = Integer.parseInt(tempMap.get(CROPS_QUANTITY).toString());
        this.note = String.valueOf(tempMap.get(CROPS_NOTES));
        Timestamp dataInserimento = (Timestamp) tempMap.get(CROPS_INSERTION_DATE);
        this.dataInserimento = dataInserimento.toDate();
        this.faseAttuale = Integer.parseInt(tempMap.get(CROPS_CURRENT_PHASE).toString());
        Timestamp ultimoInnaffiamento = (Timestamp) tempMap.get(CROPS_LAST_WATERING);
        this.ultimoInnaffiamento = ultimoInnaffiamento.toDate();
        this.nomePianta = String.valueOf(tempMap.get(PLANT_NAME));
        this.frequenzaInnaffiamento = (ArrayList<Integer>) (ArrayList) tempMap.get(CROPS_WATERING_FREQUENCY);
        this.frequenzaInnaffiamentoAttuale = Integer.parseInt(tempMap.get(CROPS_CURRENT_WATERING_FREQUENCY).toString());
        this.ultimoAggiornamento = new Date((new Date()).getTime() - 1 * 24 * 60 * 60 * 1000);
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

    public Date getUltimoAggiornamento() {
        return ultimoAggiornamento;
    }

    public void setUltimoAggiornamento(Date ultimoAggiornamento) {
        this.ultimoAggiornamento = ultimoAggiornamento;
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

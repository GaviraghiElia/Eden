package com.unimib.eden;

import static com.unimib.eden.utils.Constants.CROPS_INSERTION_DATE;
import static com.unimib.eden.utils.Constants.CROPS_CURRENT_PHASE;
import static com.unimib.eden.utils.Constants.CROPS_CURRENT_WATERING_FREQUENCY;
import static com.unimib.eden.utils.Constants.CROPS_ID;
import static com.unimib.eden.utils.Constants.CROPS_NOTES;
import static com.unimib.eden.utils.Constants.CROPS_PLANT;
import static com.unimib.eden.utils.Constants.CROPS_OWNER;
import static com.unimib.eden.utils.Constants.CROPS_QUANTITY;
import static com.unimib.eden.utils.Constants.CROPS_LAST_WATERING;
import static com.unimib.eden.utils.Constants.CROPS_WATERING_FREQUENCY;
import static com.unimib.eden.utils.Constants.PLANT_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.firebase.Timestamp;
import com.unimib.eden.model.Coltura;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ColturaUnitTest {
    private Coltura coltura;
    private final String id = "coltura1";
    private final String idPianta = "pianta1";
    private final String proprietario = "proprietario1";
    private final int quantita = 10;
    private final String note = "note";
    private final Date dataInserimento = new Date();
    private final int faseAttuale = 1;
    private final Date ultimoInnaffiamento = new Date();
    private final Date ultimoAggiornamento = new Date((new Date()).getTime() - 1 * 24 * 60 * 60 * 1000);
    private final String nomePianta = "nomePianta";
    private final ArrayList<Integer> frequenzaInnaffiamento = new ArrayList<>();
    private final int frequenzaInnaffiamentoAttuale = 2;

    @Before
    public void setUp() {
        frequenzaInnaffiamento.add(1);
        frequenzaInnaffiamento.add(2);
        frequenzaInnaffiamento.add(3);

        coltura = new Coltura(id, idPianta, proprietario, quantita, note, dataInserimento, faseAttuale, ultimoInnaffiamento, nomePianta, frequenzaInnaffiamento, frequenzaInnaffiamentoAttuale);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(id, coltura.getId());
        assertEquals(idPianta, coltura.getIdPianta());
        assertEquals(proprietario, coltura.getProprietario());
        assertEquals(quantita, coltura.getQuantita());
        assertEquals(note, coltura.getNote());
        assertEquals(dataInserimento, coltura.getDataInserimento());
        assertEquals(faseAttuale, coltura.getFaseAttuale());
        assertEquals(ultimoInnaffiamento, coltura.getUltimoInnaffiamento());
        assertEquals(nomePianta, coltura.getNomePianta());
        assertEquals(frequenzaInnaffiamento, coltura.getFrequenzaInnaffiamento());
        assertEquals(frequenzaInnaffiamentoAttuale, coltura.getFrequenzaInnaffiamentoAttuale());
        checkDate(ultimoAggiornamento, coltura.getUltimoAggiornamento());
    }

    private void checkDate(Date data1, Date data2){
        assertEquals(data1.getDay(), data2.getDay());
        assertEquals(data1.getHours(), data2.getHours());
        assertEquals(data1.getMinutes(), data2.getMinutes());
        assertEquals(data1.getSeconds(), data2.getSeconds());
        assertEquals(data1.getYear(), data2.getYear());
    }

    @Test
    public void testSetters() {
        coltura.setId("newId");
        coltura.setIdPianta("newIdPianta");
        coltura.setProprietario("newProprietario");
        coltura.setQuantita(20);
        coltura.setNote("newNote");
        coltura.setDataInserimento(new Date(0));
        coltura.setFaseAttuale(2);
        coltura.setUltimoInnaffiamento(new Date(0));
        coltura.setNomePianta("newNomePianta");
        ArrayList<Integer> newFrequenzaInnaffiamento = new ArrayList<>();
        newFrequenzaInnaffiamento.add(4);
        newFrequenzaInnaffiamento.add(5);
        coltura.setFrequenzaInnaffiamento(newFrequenzaInnaffiamento);
        coltura.setFrequenzaInnaffiamentoAttuale(3);
        coltura.setUltimoAggiornamento(new Date((new Date()).getTime() - 1 * 24 * 60 * 60 * 1000));

        assertEquals("newId", coltura.getId());
        assertEquals("newIdPianta", coltura.getIdPianta());
        assertEquals("newProprietario", coltura.getProprietario());
        assertEquals(20, coltura.getQuantita());
        assertEquals("newNote", coltura.getNote());
        assertEquals(new Date(0), coltura.getDataInserimento());
        assertEquals(2, coltura.getFaseAttuale());
        assertEquals(new Date(0), coltura.getUltimoInnaffiamento());
        assertEquals("newNomePianta", coltura.getNomePianta());
        assertEquals(newFrequenzaInnaffiamento, coltura.getFrequenzaInnaffiamento());
        assertEquals(3, coltura.getFrequenzaInnaffiamentoAttuale());
        checkDate(new Date((new Date()).getTime() - 1 * 24 * 60 * 60 * 1000), coltura.getUltimoAggiornamento());
    }

    @Test
    public void testEqualsAndHashCode() {
        Coltura sameColtura = new Coltura(id, idPianta, proprietario, quantita, note, dataInserimento, faseAttuale, ultimoInnaffiamento, nomePianta, frequenzaInnaffiamento, frequenzaInnaffiamentoAttuale);
        Coltura differentColtura = new Coltura("differentId", idPianta, proprietario, quantita, note, dataInserimento, faseAttuale, ultimoInnaffiamento, nomePianta, frequenzaInnaffiamento, frequenzaInnaffiamentoAttuale);

        Coltura newColtura = null;
        assertTrue(coltura.equals(sameColtura));
        assertFalse(coltura.equals(differentColtura));
        assertEquals(coltura.hashCode(), sameColtura.hashCode());
        assertNotEquals(coltura.hashCode(), differentColtura.hashCode());
        assertFalse(coltura.equals(newColtura));
        assertTrue(coltura.equals(coltura));
    }

    @Test
    public void testInitFromMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(CROPS_ID, id);
        dataMap.put(CROPS_PLANT, idPianta);
        dataMap.put(CROPS_OWNER, proprietario);
        dataMap.put(CROPS_QUANTITY, quantita);
        dataMap.put(CROPS_NOTES, note);
        dataMap.put(CROPS_INSERTION_DATE, new Timestamp(new Date()));
        dataMap.put(CROPS_CURRENT_PHASE, faseAttuale);
        dataMap.put(CROPS_LAST_WATERING, new Timestamp(new Date()));
        dataMap.put(PLANT_NAME, nomePianta);
        dataMap.put(CROPS_WATERING_FREQUENCY, frequenzaInnaffiamento);
        dataMap.put(CROPS_CURRENT_WATERING_FREQUENCY, frequenzaInnaffiamentoAttuale);

        // Initialize Coltura from the data map
        coltura.initFromMap(dataMap);

        // Check if Coltura fields are correctly initialized
        assertEquals(id, coltura.getId());
        assertEquals(idPianta, coltura.getIdPianta());
        assertEquals(proprietario, coltura.getProprietario());
        assertEquals(quantita, coltura.getQuantita());
        assertEquals(note, coltura.getNote());
        assertEquals(faseAttuale, coltura.getFaseAttuale());
        assertEquals(nomePianta, coltura.getNomePianta());
        assertEquals(frequenzaInnaffiamento, coltura.getFrequenzaInnaffiamento());
        assertEquals(frequenzaInnaffiamentoAttuale, coltura.getFrequenzaInnaffiamentoAttuale());
        // Check if Date fields are not null
        assertNotNull(coltura.getDataInserimento());
        assertNotNull(coltura.getUltimoInnaffiamento());
        assertNotNull(coltura.getUltimoAggiornamento());
    }

    @Test
    public void testConstructorFromMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(CROPS_ID, id);
        dataMap.put(CROPS_PLANT, idPianta);
        dataMap.put(CROPS_OWNER, proprietario);
        dataMap.put(CROPS_QUANTITY, quantita);
        dataMap.put(CROPS_NOTES, note);
        dataMap.put(CROPS_INSERTION_DATE, new Timestamp(new Date()));
        dataMap.put(CROPS_CURRENT_PHASE, faseAttuale);
        dataMap.put(CROPS_LAST_WATERING, new Timestamp(new Date()));
        dataMap.put(PLANT_NAME, nomePianta);
        dataMap.put(CROPS_WATERING_FREQUENCY, frequenzaInnaffiamento);
        dataMap.put(CROPS_CURRENT_WATERING_FREQUENCY, frequenzaInnaffiamentoAttuale);

        // Initialize Coltura from the data map
        Coltura newColtura =  new Coltura(dataMap);

        // Check if Coltura fields are correctly initialized
        assertEquals(id, newColtura.getId());
        assertEquals(idPianta, newColtura.getIdPianta());
        assertEquals(proprietario, newColtura.getProprietario());
        assertEquals(quantita, newColtura.getQuantita());
        assertEquals(note, newColtura.getNote());
        assertEquals(faseAttuale, newColtura.getFaseAttuale());
        assertEquals(nomePianta, newColtura.getNomePianta());
        assertEquals(frequenzaInnaffiamento, newColtura.getFrequenzaInnaffiamento());
        assertEquals(frequenzaInnaffiamentoAttuale, newColtura.getFrequenzaInnaffiamentoAttuale());
        // Check if Date fields are not null
        assertNotNull(newColtura.getDataInserimento());
        assertNotNull(newColtura.getUltimoInnaffiamento());
        assertNotNull(newColtura.getUltimoAggiornamento());
    }


}

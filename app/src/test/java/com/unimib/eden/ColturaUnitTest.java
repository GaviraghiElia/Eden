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
import com.unimib.eden.model.Crop;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ColturaUnitTest {
    private Crop coltura;
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

        coltura = new Crop(id, idPianta, proprietario, quantita, note, dataInserimento, faseAttuale, ultimoInnaffiamento, nomePianta, frequenzaInnaffiamento, frequenzaInnaffiamentoAttuale);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(id, coltura.getId());
        assertEquals(idPianta, coltura.getPlantId());
        assertEquals(proprietario, coltura.getOwner());
        assertEquals(quantita, coltura.getQuantity());
        assertEquals(note, coltura.getNote());
        assertEquals(dataInserimento, coltura.getInsertionDate());
        assertEquals(faseAttuale, coltura.getCurrentPhase());
        assertEquals(ultimoInnaffiamento, coltura.getLastWatering());
        assertEquals(nomePianta, coltura.getPlantName());
        assertEquals(frequenzaInnaffiamento, coltura.getWateringFrequency());
        assertEquals(frequenzaInnaffiamentoAttuale, coltura.getCurrentWateringFrequency());
        checkDate(ultimoAggiornamento, coltura.getLastUpdate());
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
        coltura.setPlantId("newIdPianta");
        coltura.setOwner("newProprietario");
        coltura.setQuantity(20);
        coltura.setNote("newNote");
        coltura.setInsertionDate(new Date(0));
        coltura.setCurrentPhase(2);
        coltura.setLastWatering(new Date(0));
        coltura.setPlantName("newNomePianta");
        ArrayList<Integer> newFrequenzaInnaffiamento = new ArrayList<>();
        newFrequenzaInnaffiamento.add(4);
        newFrequenzaInnaffiamento.add(5);
        coltura.setWateringFrequency(newFrequenzaInnaffiamento);
        coltura.setCurrentWateringFrequency(3);
        coltura.setLastUpdate(new Date((new Date()).getTime() - 1 * 24 * 60 * 60 * 1000));

        assertEquals("newId", coltura.getId());
        assertEquals("newIdPianta", coltura.getPlantId());
        assertEquals("newProprietario", coltura.getOwner());
        assertEquals(20, coltura.getQuantity());
        assertEquals("newNote", coltura.getNote());
        assertEquals(new Date(0), coltura.getInsertionDate());
        assertEquals(2, coltura.getCurrentPhase());
        assertEquals(new Date(0), coltura.getLastWatering());
        assertEquals("newNomePianta", coltura.getPlantName());
        assertEquals(newFrequenzaInnaffiamento, coltura.getWateringFrequency());
        assertEquals(3, coltura.getCurrentWateringFrequency());
        checkDate(new Date((new Date()).getTime() - 1 * 24 * 60 * 60 * 1000), coltura.getLastUpdate());
    }

    @Test
    public void testEqualsAndHashCode() {
        Crop sameColtura = new Crop(id, idPianta, proprietario, quantita, note, dataInserimento, faseAttuale, ultimoInnaffiamento, nomePianta, frequenzaInnaffiamento, frequenzaInnaffiamentoAttuale);
        Crop differentColtura = new Crop("differentId", idPianta, proprietario, quantita, note, dataInserimento, faseAttuale, ultimoInnaffiamento, nomePianta, frequenzaInnaffiamento, frequenzaInnaffiamentoAttuale);

        Crop newColtura = null;
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
        assertEquals(idPianta, coltura.getPlantId());
        assertEquals(proprietario, coltura.getOwner());
        assertEquals(quantita, coltura.getQuantity());
        assertEquals(note, coltura.getNote());
        assertEquals(faseAttuale, coltura.getCurrentPhase());
        assertEquals(nomePianta, coltura.getPlantName());
        assertEquals(frequenzaInnaffiamento, coltura.getWateringFrequency());
        assertEquals(frequenzaInnaffiamentoAttuale, coltura.getCurrentWateringFrequency());
        // Check if Date fields are not null
        assertNotNull(coltura.getInsertionDate());
        assertNotNull(coltura.getLastWatering());
        assertNotNull(coltura.getLastUpdate());
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
        Crop newColtura =  new Crop(dataMap);

        // Check if Coltura fields are correctly initialized
        assertEquals(id, newColtura.getId());
        assertEquals(idPianta, newColtura.getPlantId());
        assertEquals(proprietario, newColtura.getOwner());
        assertEquals(quantita, newColtura.getQuantity());
        assertEquals(note, newColtura.getNote());
        assertEquals(faseAttuale, newColtura.getCurrentPhase());
        assertEquals(nomePianta, newColtura.getPlantName());
        assertEquals(frequenzaInnaffiamento, newColtura.getWateringFrequency());
        assertEquals(frequenzaInnaffiamentoAttuale, newColtura.getCurrentWateringFrequency());
        // Check if Date fields are not null
        assertNotNull(newColtura.getInsertionDate());
        assertNotNull(newColtura.getLastWatering());
        assertNotNull(newColtura.getLastUpdate());
    }


}

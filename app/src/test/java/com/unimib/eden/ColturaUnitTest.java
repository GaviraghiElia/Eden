package com.unimib.eden;

import static com.unimib.eden.utils.Constants.COLTURA_DATA_INSERIMENTO;
import static com.unimib.eden.utils.Constants.COLTURA_FASE_ATTUALE;
import static com.unimib.eden.utils.Constants.COLTURA_FREQUENZA_INNAFFIAMENTO_ATTUALE;
import static com.unimib.eden.utils.Constants.COLTURA_ID;
import static com.unimib.eden.utils.Constants.COLTURA_NOTE;
import static com.unimib.eden.utils.Constants.COLTURA_PIANTA;
import static com.unimib.eden.utils.Constants.COLTURA_PROPRIETARIO;
import static com.unimib.eden.utils.Constants.COLTURA_QUANTITA;
import static com.unimib.eden.utils.Constants.COLTURA_ULTIMO_INNAFFIAMENTO;
import static com.unimib.eden.utils.Constants.COLTURA_FREQUENZA_INNAFFIAMENTO;
import static com.unimib.eden.utils.Constants.PIANTA_NOME;
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
    }

    @Test
    public void testEqualsAndHashCode() {
        Coltura sameColtura = new Coltura(id, idPianta, proprietario, quantita, note, dataInserimento, faseAttuale, ultimoInnaffiamento, nomePianta, frequenzaInnaffiamento, frequenzaInnaffiamentoAttuale);
        Coltura differentColtura = new Coltura("differentId", idPianta, proprietario, quantita, note, dataInserimento, faseAttuale, ultimoInnaffiamento, nomePianta, frequenzaInnaffiamento, frequenzaInnaffiamentoAttuale);

        assertTrue(coltura.equals(sameColtura));
        assertFalse(coltura.equals(differentColtura));
        assertEquals(coltura.hashCode(), sameColtura.hashCode());
        assertNotEquals(coltura.hashCode(), differentColtura.hashCode());
    }

    @Test
    public void testInitFromMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(COLTURA_ID, id);
        dataMap.put(COLTURA_PIANTA, idPianta);
        dataMap.put(COLTURA_PROPRIETARIO, proprietario);
        dataMap.put(COLTURA_QUANTITA, quantita);
        dataMap.put(COLTURA_NOTE, note);
        dataMap.put(COLTURA_DATA_INSERIMENTO, new Timestamp(new Date()));
        dataMap.put(COLTURA_FASE_ATTUALE, faseAttuale);
        dataMap.put(COLTURA_ULTIMO_INNAFFIAMENTO, new Timestamp(new Date()));
        dataMap.put(PIANTA_NOME, nomePianta);
        dataMap.put(COLTURA_FREQUENZA_INNAFFIAMENTO, frequenzaInnaffiamento);
        dataMap.put(COLTURA_FREQUENZA_INNAFFIAMENTO_ATTUALE, frequenzaInnaffiamentoAttuale);

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
    }

}

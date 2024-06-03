package com.unimib.eden;


import static com.unimib.eden.utils.Constants.PRODOTTO_ALTRE_INFORMAZIONI;
import static com.unimib.eden.utils.Constants.PRODOTTO_FASE_ATTUALE;
import static com.unimib.eden.utils.Constants.PRODOTTO_ID;
import static com.unimib.eden.utils.Constants.PRODOTTO_OFFERTE;
import static com.unimib.eden.utils.Constants.PRODOTTO_PIANTA;
import static com.unimib.eden.utils.Constants.PRODOTTO_PREZZO;
import static com.unimib.eden.utils.Constants.PRODOTTO_QUANTITA;
import static com.unimib.eden.utils.Constants.PRODOTTO_SCAMBIO_DISPONIBILE;
import static com.unimib.eden.utils.Constants.PRODOTTO_TIPO;
import static com.unimib.eden.utils.Constants.PRODOTTO_VENDITORE;
import com.unimib.eden.model.Prodotto;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ProdottoUnitTest {

    private Prodotto prodotto;

    @Before
    public void setUp() {
        ArrayList<String> offerte = new ArrayList<>();
        offerte.add("offerta1");
        offerte.add("offerta2");

        prodotto = new Prodotto(
                "1",
                "Frutta",
                "Venditore1",
                10.0,
                "Mela",
                offerte,
                5,
                "Fase1",
                "Altre informazioni",
                true
        );
    }

    @Test
    public void testConstructor() {
        assertNotNull(prodotto);
        assertEquals("1", prodotto.getId());
        assertEquals("Frutta", prodotto.getTipo());
        assertEquals("Venditore1", prodotto.getVenditore());
        assertEquals(10.0, prodotto.getPrezzo(), 0.01);
        assertEquals("Mela", prodotto.getPianta());
        assertEquals(2, prodotto.getOfferte().size());
        assertEquals("offerta1", prodotto.getOfferte().get(0));
        assertEquals(5, prodotto.getQuantita());
        assertEquals("Fase1", prodotto.getFaseAttuale());
        assertEquals("Altre informazioni", prodotto.getAltreInformazioni());
        assertTrue(prodotto.getScambioDisponibile());
    }
    @Test
    public void testGetters() {
        assertEquals("1", prodotto.getId());
        assertEquals("Frutta", prodotto.getTipo());
        assertEquals("Venditore1", prodotto.getVenditore());
        assertEquals(10.0, prodotto.getPrezzo(), 0.01);
        assertEquals("Mela", prodotto.getPianta());
        assertEquals(2, prodotto.getOfferte().size());
        assertEquals("offerta1", prodotto.getOfferte().get(0));
        assertEquals(5, prodotto.getQuantita());
        assertEquals("Fase1", prodotto.getFaseAttuale());
        assertEquals("Altre informazioni", prodotto.getAltreInformazioni());
        assertTrue(prodotto.getScambioDisponibile());
    }

    @Test
    public void testSetters() {
        ArrayList<String> newOfferte = new ArrayList<>();
        newOfferte.add("offerta3");

        prodotto.setId("2");
        prodotto.setTipo("Verdura");
        prodotto.setVenditore("Venditore2");
        prodotto.setPrezzo(15.0);
        prodotto.setPianta("Carota");
        prodotto.setOfferte(newOfferte);
        prodotto.setQuantita(10);
        prodotto.setFaseAttuale("Fase2");
        prodotto.setAltreInformazioni("Nuove informazioni");
        prodotto.setScambioDisponibile(false);

        assertEquals("2", prodotto.getId());
        assertEquals("Verdura", prodotto.getTipo());
        assertEquals("Venditore2", prodotto.getVenditore());
        assertEquals(15.0, prodotto.getPrezzo(), 0.01);
        assertEquals("Carota", prodotto.getPianta());
        assertEquals(1, prodotto.getOfferte().size());
        assertEquals("offerta3", prodotto.getOfferte().get(0));
        assertEquals(10, prodotto.getQuantita());
        assertEquals("Fase2", prodotto.getFaseAttuale());
        assertEquals("Nuove informazioni", prodotto.getAltreInformazioni());
        assertFalse(prodotto.getScambioDisponibile());
    }

    @Test
    public void testEqualsAndHashCode() {
        ArrayList<String> offerte1 = new ArrayList<>();
        offerte1.add("offerta1");
        offerte1.add("offerta2");

        Prodotto prodotto1 = new Prodotto(
                "1",
                "Frutta",
                "Venditore1",
                10.0,
                "Mela",
                offerte1,
                5,
                "Fase1",
                "Altre informazioni",
                true
        );

        ArrayList<String> offerte2 = new ArrayList<>();
        offerte2.add("offerta1");
        offerte2.add("offerta2");

        Prodotto prodotto2 = new Prodotto(
                "1",
                "Frutta",
                "Venditore1",
                10.0,
                "Mela",
                offerte2,
                5,
                "Fase1",
                "Altre informazioni",
                true
        );

        assertTrue(prodotto1.equals(prodotto2));
        assertEquals(prodotto1.hashCode(), prodotto2.hashCode());

        prodotto2.setId("2");
        assertFalse(prodotto1.equals(prodotto2));
        assertNotEquals(prodotto1.hashCode(), prodotto2.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "Prodotto{id='1', tipo='Frutta', venditore='Venditore1', prezzo=10.0, " +
                "pianta='Mela', offerte=[offerta1, offerta2], quantita=5, faseAttuale=Fase1, " +
                "altreInformazioni='Altre informazioni', scambioDisponibile=true}";
        assertEquals(expected, prodotto.toString());
    }

    @Test
    public void testInitFromMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(PRODOTTO_TIPO, "Verdura");
        dataMap.put(PRODOTTO_PREZZO, 15.0);
        dataMap.put(PRODOTTO_PIANTA, "Carota");
        dataMap.put(PRODOTTO_QUANTITA, 10);
        dataMap.put(PRODOTTO_FASE_ATTUALE, "Fase2");
        dataMap.put(PRODOTTO_ALTRE_INFORMAZIONI, "Nuove informazioni");
        dataMap.put(PRODOTTO_VENDITORE, "Venditore2");
        dataMap.put(PRODOTTO_OFFERTE, new ArrayList<>());
        dataMap.put(PRODOTTO_SCAMBIO_DISPONIBILE, false);

        prodotto.initFromMap(dataMap);

        assertEquals("1", prodotto.getId());
        assertEquals("Verdura", prodotto.getTipo());
        assertEquals("Venditore2", prodotto.getVenditore());
        assertEquals(15.0, prodotto.getPrezzo(), 0.01);
        assertEquals("Carota", prodotto.getPianta());
        assertEquals(0, prodotto.getOfferte().size());
        assertEquals(10, prodotto.getQuantita());
        assertEquals("Fase2", prodotto.getFaseAttuale());
        assertEquals("Nuove informazioni", prodotto.getAltreInformazioni());
        assertFalse(prodotto.getScambioDisponibile());
    }

    @Test
    public void testProdottoConstructorFromMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(PRODOTTO_ID, "2");
        dataMap.put(PRODOTTO_TIPO, "Verdura");
        dataMap.put(PRODOTTO_PREZZO, 15.0);
        dataMap.put(PRODOTTO_PIANTA, "Carota");
        dataMap.put(PRODOTTO_QUANTITA, 10);
        dataMap.put(PRODOTTO_FASE_ATTUALE, "Fase2");
        dataMap.put(PRODOTTO_ALTRE_INFORMAZIONI, "Nuove informazioni");
        dataMap.put(PRODOTTO_VENDITORE, "Venditore2");
        dataMap.put(PRODOTTO_OFFERTE, new ArrayList<>());
        dataMap.put(PRODOTTO_SCAMBIO_DISPONIBILE, false);

        Prodotto prodottoFromMap = new Prodotto(dataMap);

        assertEquals("2", prodottoFromMap.getId());
        assertEquals("Verdura", prodottoFromMap.getTipo());
        assertEquals("Venditore2", prodottoFromMap.getVenditore());
        assertEquals(15.0, prodottoFromMap.getPrezzo(), 0.01);
        assertEquals("Carota", prodottoFromMap.getPianta());
        assertEquals(0, prodottoFromMap.getOfferte().size());
        assertEquals(10, prodottoFromMap.getQuantita());
        assertEquals("Fase2", prodottoFromMap.getFaseAttuale());
        assertEquals("Nuove informazioni", prodottoFromMap.getAltreInformazioni());
        assertFalse(prodottoFromMap.getScambioDisponibile());
    }
}


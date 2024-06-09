package com.unimib.eden;


import static com.unimib.eden.utils.Constants.PRODUCT_OTHER_INFORMATION;
import static com.unimib.eden.utils.Constants.PRODUCT_CURRENT_PHASE;
import static com.unimib.eden.utils.Constants.PRODUCT_ID;
import static com.unimib.eden.utils.Constants.PRODUCT_OFFERS;
import static com.unimib.eden.utils.Constants.PRODUCT_PLANT;
import static com.unimib.eden.utils.Constants.PRODUCT_PRICE;
import static com.unimib.eden.utils.Constants.PRODUCT_QUANTITY;
import static com.unimib.eden.utils.Constants.PRODUCT_EXCHANGE_AVAILABLE;
import static com.unimib.eden.utils.Constants.PRODUCT_TYPE;
import static com.unimib.eden.utils.Constants.PRODUCT_SELLER;
import com.unimib.eden.model.Product;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ProdottoUnitTest {

    private Product prodotto;

    @Before
    public void setUp() {
        ArrayList<String> offerte = new ArrayList<>();
        offerte.add("offerta1");
        offerte.add("offerta2");

        prodotto = new Product(
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
        assertEquals("Frutta", prodotto.getType());
        assertEquals("Venditore1", prodotto.getSeller());
        assertEquals(10.0, prodotto.getPrice(), 0.01);
        assertEquals("Mela", prodotto.getPlant());
        assertEquals(2, prodotto.getOffers().size());
        assertEquals("offerta1", prodotto.getOffers().get(0));
        assertEquals(5, prodotto.getQuantity());
        assertEquals("Fase1", prodotto.getCurrentPhase());
        assertEquals("Altre informazioni", prodotto.getOtherInformation());
        assertTrue(prodotto.getExchangeAvailable());
    }
    @Test
    public void testGetters() {
        assertEquals("1", prodotto.getId());
        assertEquals("Frutta", prodotto.getType());
        assertEquals("Venditore1", prodotto.getSeller());
        assertEquals(10.0, prodotto.getPrice(), 0.01);
        assertEquals("Mela", prodotto.getPlant());
        assertEquals(2, prodotto.getOffers().size());
        assertEquals("offerta1", prodotto.getOffers().get(0));
        assertEquals(5, prodotto.getQuantity());
        assertEquals("Fase1", prodotto.getCurrentPhase());
        assertEquals("Altre informazioni", prodotto.getOtherInformation());
        assertTrue(prodotto.getExchangeAvailable());
    }

    @Test
    public void testSetters() {
        ArrayList<String> newOfferte = new ArrayList<>();
        newOfferte.add("offerta3");

        prodotto.setId("2");
        prodotto.setType("Verdura");
        prodotto.setSeller("Venditore2");
        prodotto.setPrice(15.0);
        prodotto.setPlant("Carota");
        prodotto.setOffers(newOfferte);
        prodotto.setQuantity(10);
        prodotto.setCurrentPhase("Fase2");
        prodotto.setOtherInformation("Nuove informazioni");
        prodotto.setExchangeAvailable(false);

        assertEquals("2", prodotto.getId());
        assertEquals("Verdura", prodotto.getType());
        assertEquals("Venditore2", prodotto.getSeller());
        assertEquals(15.0, prodotto.getPrice(), 0.01);
        assertEquals("Carota", prodotto.getPlant());
        assertEquals(1, prodotto.getOffers().size());
        assertEquals("offerta3", prodotto.getOffers().get(0));
        assertEquals(10, prodotto.getQuantity());
        assertEquals("Fase2", prodotto.getCurrentPhase());
        assertEquals("Nuove informazioni", prodotto.getOtherInformation());
        assertFalse(prodotto.getExchangeAvailable());
    }

    @Test
    public void testEqualsAndHashCode() {
        ArrayList<String> offerte1 = new ArrayList<>();
        offerte1.add("offerta1");
        offerte1.add("offerta2");

        Product prodotto1 = new Product(
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

        Product prodotto2 = new Product(
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
        dataMap.put(PRODUCT_TYPE, "Verdura");
        dataMap.put(PRODUCT_PRICE, 15.0);
        dataMap.put(PRODUCT_PLANT, "Carota");
        dataMap.put(PRODUCT_QUANTITY, 10);
        dataMap.put(PRODUCT_CURRENT_PHASE, "Fase2");
        dataMap.put(PRODUCT_OTHER_INFORMATION, "Nuove informazioni");
        dataMap.put(PRODUCT_SELLER, "Venditore2");
        dataMap.put(PRODUCT_OFFERS, new ArrayList<>());
        dataMap.put(PRODUCT_EXCHANGE_AVAILABLE, false);

        prodotto.initFromMap(dataMap);

        assertEquals("1", prodotto.getId());
        assertEquals("Verdura", prodotto.getType());
        assertEquals("Venditore2", prodotto.getSeller());
        assertEquals(15.0, prodotto.getPrice(), 0.01);
        assertEquals("Carota", prodotto.getPlant());
        assertEquals(0, prodotto.getOffers().size());
        assertEquals(10, prodotto.getQuantity());
        assertEquals("Fase2", prodotto.getCurrentPhase());
        assertEquals("Nuove informazioni", prodotto.getOtherInformation());
        assertFalse(prodotto.getExchangeAvailable());
    }

    @Test
    public void testProdottoConstructorFromMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(PRODUCT_ID, "2");
        dataMap.put(PRODUCT_TYPE, "Verdura");
        dataMap.put(PRODUCT_PRICE, 15.0);
        dataMap.put(PRODUCT_PLANT, "Carota");
        dataMap.put(PRODUCT_QUANTITY, 10);
        dataMap.put(PRODUCT_CURRENT_PHASE, "Fase2");
        dataMap.put(PRODUCT_OTHER_INFORMATION, "Nuove informazioni");
        dataMap.put(PRODUCT_SELLER, "Venditore2");
        dataMap.put(PRODUCT_OFFERS, new ArrayList<>());
        dataMap.put(PRODUCT_EXCHANGE_AVAILABLE, false);

        Product prodottoFromMap = new Product(dataMap);

        assertEquals("2", prodottoFromMap.getId());
        assertEquals("Verdura", prodottoFromMap.getType());
        assertEquals("Venditore2", prodottoFromMap.getSeller());
        assertEquals(15.0, prodottoFromMap.getPrice(), 0.01);
        assertEquals("Carota", prodottoFromMap.getPlant());
        assertEquals(0, prodottoFromMap.getOffers().size());
        assertEquals(10, prodottoFromMap.getQuantity());
        assertEquals("Fase2", prodottoFromMap.getCurrentPhase());
        assertEquals("Nuove informazioni", prodottoFromMap.getOtherInformation());
        assertFalse(prodottoFromMap.getExchangeAvailable());
    }
}


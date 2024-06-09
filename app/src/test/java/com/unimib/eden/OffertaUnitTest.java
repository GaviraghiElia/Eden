package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unimib.eden.model.Offer;

import org.junit.Before;
import org.junit.Test;

import com.unimib.eden.utils.Enum;

public class OffertaUnitTest {
    private QueryDocumentSnapshot document;
    private Offer offerta;

    @Before
    public void setUp() {
        offerta = new Offer("idOfferta", "Sandro", 150, Enum.StatoProposta.ACCEPTED);
    }

    @Test
    public void testConstructor() {
        assertNotNull(offerta);
        assertEquals("idOfferta", offerta.getId());
        assertEquals("Sandro", offerta.getBuyer());
        assertEquals(150, offerta.getPrice(), 0.01);
        assertEquals(Enum.StatoProposta.ACCEPTED, offerta.getOfferStatus());
    }

    @Test
    public void testGetters() {
        assertEquals("idOfferta", offerta.getId());
        assertEquals("Sandro", offerta.getBuyer());
        assertEquals(150, offerta.getPrice(), 0.01);
        assertEquals(Enum.StatoProposta.ACCEPTED, offerta.getOfferStatus());
    }

    @Test
    public void testSetters() {
        offerta.setId("idOfferta2");
        offerta.setBuyer("Alice");
        offerta.setPrice(200);
        offerta.setOfferStatus(Enum.StatoProposta.REJECTED);

        assertEquals("idOfferta2", offerta.getId());
        assertEquals("Alice", offerta.getBuyer());
        assertEquals(200, offerta.getPrice(), 0.01);
        assertEquals(Enum.StatoProposta.REJECTED, offerta.getOfferStatus());
    }

    @Test
    public void testEqualsAndHashCode() {
        Offer sameOfferta = new Offer("idOfferta", "Sandro", 150, Enum.StatoProposta.ACCEPTED);
        Offer differentOfferta = new Offer("idOfferta2", "Alice", 200, Enum.StatoProposta.REJECTED);

        Offer newOfferta = null;
        assertEquals(offerta, sameOfferta);
        assertNotEquals(offerta, differentOfferta);
        assertEquals(offerta.hashCode(), sameOfferta.hashCode());
        assertNotEquals(offerta.hashCode(), differentOfferta.hashCode());
        assertFalse(offerta.equals(newOfferta));
        assertTrue(offerta.equals(offerta));
    }

    @Test
    public void testToString() {
        String expectedString = "Offerta{id='idOfferta', acquirente='Sandro', prezzo=150.0, statoPropostaEnum=ACCETTATA}";
        assertEquals(expectedString, offerta.toString());
    }
}

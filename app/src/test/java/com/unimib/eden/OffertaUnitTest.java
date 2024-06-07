package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unimib.eden.model.Offerta;

import org.junit.Before;
import org.junit.Test;

import com.unimib.eden.utils.Enum;

public class OffertaUnitTest {
    private QueryDocumentSnapshot document;
    private Offerta offerta;

    @Before
    public void setUp() {
        offerta = new Offerta("idOfferta", "Sandro", 150, Enum.StatoProposta.ACCEPTED);
    }

    @Test
    public void testConstructor() {
        assertNotNull(offerta);
        assertEquals("idOfferta", offerta.getId());
        assertEquals("Sandro", offerta.getAcquirente());
        assertEquals(150, offerta.getPrezzo(), 0.01);
        assertEquals(Enum.StatoProposta.ACCEPTED, offerta.getStatoPropostaEnum());
    }

    @Test
    public void testGetters() {
        assertEquals("idOfferta", offerta.getId());
        assertEquals("Sandro", offerta.getAcquirente());
        assertEquals(150, offerta.getPrezzo(), 0.01);
        assertEquals(Enum.StatoProposta.ACCEPTED, offerta.getStatoPropostaEnum());
    }

    @Test
    public void testSetters() {
        offerta.setId("idOfferta2");
        offerta.setAcquirente("Alice");
        offerta.setPrezzo(200);
        offerta.setStatoPropostaEnum(Enum.StatoProposta.REJECTED);

        assertEquals("idOfferta2", offerta.getId());
        assertEquals("Alice", offerta.getAcquirente());
        assertEquals(200, offerta.getPrezzo(), 0.01);
        assertEquals(Enum.StatoProposta.REJECTED, offerta.getStatoPropostaEnum());
    }

    @Test
    public void testEqualsAndHashCode() {
        Offerta sameOfferta = new Offerta("idOfferta", "Sandro", 150, Enum.StatoProposta.ACCEPTED);
        Offerta differentOfferta = new Offerta("idOfferta2", "Alice", 200, Enum.StatoProposta.REJECTED);

        Offerta newOfferta = null;
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

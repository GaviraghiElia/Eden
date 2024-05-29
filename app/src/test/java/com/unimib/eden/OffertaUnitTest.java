package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unimib.eden.model.Offerta;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import com.unimib.eden.utils.Enum;

public class OffertaUnitTest {
    private QueryDocumentSnapshot document;
    private Offerta offerta;

    @Before
    public void setUp() {
        offerta = new Offerta("idOfferta", "Sandro", 150, Enum.StatoProposta.ACCETTATA);
    }

    @Test
    public void testConstructor() {
        assertNotNull(offerta);
        assertEquals("idOfferta", offerta.getId());
        assertEquals("Sandro", offerta.getAcquirente());
        assertEquals(150, offerta.getPrezzo(), 0.01);
        assertEquals(Enum.StatoProposta.ACCETTATA, offerta.getStatoPropostaEnum());
    }

    @Test
    public void testGetters() {
        assertEquals("idOfferta", offerta.getId());
        assertEquals("Sandro", offerta.getAcquirente());
        assertEquals(150, offerta.getPrezzo(), 0.01);
        assertEquals(Enum.StatoProposta.ACCETTATA, offerta.getStatoPropostaEnum());
    }

    @Test
    public void testSetters() {
        offerta.setId("idOfferta2");
        offerta.setAcquirente("Alice");
        offerta.setPrezzo(200);
        offerta.setStatoPropostaEnum(Enum.StatoProposta.RIFIUTATA);

        assertEquals("idOfferta2", offerta.getId());
        assertEquals("Alice", offerta.getAcquirente());
        assertEquals(200, offerta.getPrezzo(), 0.01);
        assertEquals(Enum.StatoProposta.RIFIUTATA, offerta.getStatoPropostaEnum());
    }
}

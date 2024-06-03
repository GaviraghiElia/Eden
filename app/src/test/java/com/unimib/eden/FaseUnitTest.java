package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.unimib.eden.model.Fase;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class FaseUnitTest {

    private Fase fase;

    @Before
    public void setUp() {
        fase = new Fase("1", "Germinazione", 1, 30, "Inizio della crescita", "https://example.com/image.jpg", 7);
    }

    @Test
    public void testConstructor() {
        assertNotNull(fase);
        assertEquals("1", fase.getId());
        assertEquals("Germinazione", fase.getNomeFase());
        assertEquals(1, fase.getInizioFase());
        assertEquals(30, fase.getDurataFase());
        assertEquals("Inizio della crescita", fase.getDescrizione());
        assertEquals("https://example.com/image.jpg", fase.getImmagine());
        assertEquals(7, fase.getFrequenzaInnaffiamento());
    }

    @Test
    public void testGetters() {
        assertEquals("1", fase.getId());
        assertEquals("Germinazione", fase.getNomeFase());
        assertEquals(1, fase.getInizioFase());
        assertEquals(30, fase.getDurataFase());
        assertEquals("Inizio della crescita", fase.getDescrizione());
        assertEquals("https://example.com/image.jpg", fase.getImmagine());
        assertEquals(7, fase.getFrequenzaInnaffiamento());
    }

    @Test
    public void testSetters() {
        fase.setId("2");
        fase.setNomeFase("Crescita");
        fase.setInizioFase(2);
        fase.setDurataFase(60);
        fase.setDescrizione("Crescita della pianta");
        fase.setImmagine("https://example.com/image2.jpg");
        fase.setFrequenzaInnaffiamento(14);

        assertEquals("2", fase.getId());
        assertEquals("Crescita", fase.getNomeFase());
        assertEquals(2, fase.getInizioFase());
        assertEquals(60, fase.getDurataFase());
        assertEquals("Crescita della pianta", fase.getDescrizione());
        assertEquals("https://example.com/image2.jpg", fase.getImmagine());
        assertEquals(14, fase.getFrequenzaInnaffiamento());
    }

    @Test
    public void testEquals() {
        Fase fase2 = new Fase("1", "Germinazione", 1, 30, "Inizio della crescita", "https://example.com/image.jpg", 7);
        Fase fase3 = new Fase("3", "Fioritura", 3, 90, "Fase della fioritura", "https://example.com/image3.jpg", 10);

        assertEquals(fase, fase2);
        assertNotEquals(fase, fase3);
    }

    @Test
    public void testHashCode() {
        Fase fase2 = new Fase("1", "Germinazione", 1, 30, "Inizio della crescita", "https://example.com/image.jpg", 7);
        Fase fase3 = new Fase("3", "Fioritura", 3, 90, "Fase della fioritura", "https://example.com/image3.jpg", 10);

        assertEquals(fase.hashCode(), fase2.hashCode());
        assertNotEquals(fase.hashCode(), fase3.hashCode());
    }
}

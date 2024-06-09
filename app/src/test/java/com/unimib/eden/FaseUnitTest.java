package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.unimib.eden.model.Phase;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class FaseUnitTest {

    private Phase fase;

    @Before
    public void setUp() {
        fase = new Phase("1", "Germinazione", 1, 30, "Inizio della crescita", "https://example.com/image.jpg", 7);
    }

    @Test
    public void testConstructor() {
        assertNotNull(fase);
        assertEquals("1", fase.getId());
        assertEquals("Germinazione", fase.getPhaseName());
        assertEquals(1, fase.getPhaseStart());
        assertEquals(30, fase.getPhaseDuration());
        assertEquals("Inizio della crescita", fase.getDescription());
        assertEquals("https://example.com/image.jpg", fase.getImage());
        assertEquals(7, fase.getWateringFrequency());
    }

    @Test
    public void testGetters() {
        assertEquals("1", fase.getId());
        assertEquals("Germinazione", fase.getPhaseName());
        assertEquals(1, fase.getPhaseStart());
        assertEquals(30, fase.getPhaseDuration());
        assertEquals("Inizio della crescita", fase.getDescription());
        assertEquals("https://example.com/image.jpg", fase.getImage());
        assertEquals(7, fase.getWateringFrequency());
    }

    @Test
    public void testSetters() {
        fase.setId("2");
        fase.setPhaseName("Crescita");
        fase.setPhaseStart(2);
        fase.setPhaseDuration(60);
        fase.setDescription("Crescita della pianta");
        fase.setImage("https://example.com/image2.jpg");
        fase.setWateringFrequency(14);

        assertEquals("2", fase.getId());
        assertEquals("Crescita", fase.getPhaseName());
        assertEquals(2, fase.getPhaseStart());
        assertEquals(60, fase.getPhaseDuration());
        assertEquals("Crescita della pianta", fase.getDescription());
        assertEquals("https://example.com/image2.jpg", fase.getImage());
        assertEquals(14, fase.getWateringFrequency());
    }

    @Test
    public void testEquals() {
        Phase fase2 = new Phase("1", "Germinazione", 1, 30, "Inizio della crescita", "https://example.com/image.jpg", 7);
        Phase fase3 = new Phase("3", "Fioritura", 3, 90, "Fase della fioritura", "https://example.com/image3.jpg", 10);

        assertEquals(fase, fase2);
        assertNotEquals(fase, fase3);
    }

    @Test
    public void testHashCode() {
        Phase fase2 = new Phase("1", "Germinazione", 1, 30, "Inizio della crescita", "https://example.com/image.jpg", 7);
        Phase fase3 = new Phase("3", "Fioritura", 3, 90, "Fase della fioritura", "https://example.com/image3.jpg", 10);

        assertEquals(fase.hashCode(), fase2.hashCode());
        assertNotEquals(fase.hashCode(), fase3.hashCode());
    }
}

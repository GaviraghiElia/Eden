package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.unimib.eden.model.Phase;

import org.junit.Before;
import org.junit.Test;

public class PhaseUnitTest {

    private Phase phase;

    @Before
    public void setUp() {
        phase = new Phase("1", "Germination", 1, 30, "Start of growth", "https://example.com/image.jpg", 7);
    }

    @Test
    public void testConstructor() {
        assertNotNull(phase);
        assertEquals("1", phase.getId());
        assertEquals("Germination", phase.getPhaseName());
        assertEquals(1, phase.getPhaseStart());
        assertEquals(30, phase.getPhaseDuration());
        assertEquals("Start of growth", phase.getDescription());
        assertEquals("https://example.com/image.jpg", phase.getImage());
        assertEquals(7, phase.getWateringFrequency());
    }

    @Test
    public void testGetters() {
        assertEquals("1", phase.getId());
        assertEquals("Germination", phase.getPhaseName());
        assertEquals(1, phase.getPhaseStart());
        assertEquals(30, phase.getPhaseDuration());
        assertEquals("Start of growth", phase.getDescription());
        assertEquals("https://example.com/image.jpg", phase.getImage());
        assertEquals(7, phase.getWateringFrequency());
    }

    @Test
    public void testSetters() {
        phase.setId("2");
        phase.setPhaseName("Growth");
        phase.setPhaseStart(2);
        phase.setPhaseDuration(60);
        phase.setDescription("Plant growth");
        phase.setImage("https://example.com/image2.jpg");
        phase.setWateringFrequency(14);

        assertEquals("2", phase.getId());
        assertEquals("Growth", phase.getPhaseName());
        assertEquals(2, phase.getPhaseStart());
        assertEquals(60, phase.getPhaseDuration());
        assertEquals("Plant growth", phase.getDescription());
        assertEquals("https://example.com/image2.jpg", phase.getImage());
        assertEquals(14, phase.getWateringFrequency());
    }

    @Test
    public void testEquals() {
        Phase phase2 = new Phase("1", "Germination", 1, 30, "Start of growth", "https://example.com/image.jpg", 7);
        Phase phase3 = new Phase("3", "Flowering", 3, 90, "Flowering phase", "https://example.com/image3.jpg", 10);

        assertEquals(phase, phase2);
        assertNotEquals(phase, phase3);
    }

    @Test
    public void testHashCode() {
        Phase phase2 = new Phase("1", "Germination", 1, 30, "Start of growth", "https://example.com/image.jpg", 7);
        Phase phase3 = new Phase("3", "Flowering", 3, 90, "Flowering phase", "https://example.com/image3.jpg", 10);

        assertEquals(phase.hashCode(), phase2.hashCode());
        assertNotEquals(phase.hashCode(), phase3.hashCode());
    }
}

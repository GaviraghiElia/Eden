package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.unimib.eden.model.Plant;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PiantaUnitTest {
    private Plant pianta;

    @Before
    public void setUp() {
        ArrayList<String> fasi = new ArrayList<>();
        fasi.add("fase1");
        fasi.add("fase2");

        pianta = new Plant(
                "beVITqkLHWCerI1XLRxj",
                "pomodoro",
                "Pianta di pomodoro (Solanum lycopersicum)",
                "Solanaceae",
                3,
                4,
                fasi,
                0.0,
                "pieno sole",
                "ben drenato, ricco di sostanze nutritive",
                18,
                24,
                2.0
        );
    }

    @Test
    public void testConstructor() {
        assertNotNull(pianta);
        assertEquals("beVITqkLHWCerI1XLRxj", pianta.getId());
        assertEquals("pomodoro", pianta.getName());
        assertEquals("Pianta di pomodoro (Solanum lycopersicum)", pianta.getDescription());
        assertEquals("Solanaceae", pianta.getBotanicalFamily());
        assertEquals(3, pianta.getSowingStart());
        assertEquals(4, pianta.getSowingEnd());
        assertEquals(2, pianta.getPhases().size());
        assertEquals("fase1", pianta.getPhases().get(0));
        assertEquals(0.0, pianta.getRequiredSpace(), 0.01);
        assertEquals("pieno sole", pianta.getSunExposure());
        assertEquals("ben drenato, ricco di sostanze nutritive", pianta.getSoilType());
        assertEquals(18, pianta.getMinTemperature());
        assertEquals(24, pianta.getMaxTemperature());
        assertEquals(2.0, pianta.getMaxExpectedHeight(), 0.01);
    }

    @Test
    public void testGetters() {
        assertEquals("beVITqkLHWCerI1XLRxj", pianta.getId());
        assertEquals("pomodoro", pianta.getName());
        assertEquals("Pianta di pomodoro (Solanum lycopersicum)", pianta.getDescription());
        assertEquals("Solanaceae", pianta.getBotanicalFamily());
        assertEquals(3, pianta.getSowingStart());
        assertEquals(4, pianta.getSowingEnd());
        assertEquals(2, pianta.getPhases().size());
        assertEquals("fase1", pianta.getPhases().get(0));
        assertEquals(0.0, pianta.getRequiredSpace(), 0.01);
        assertEquals("pieno sole", pianta.getSunExposure());
        assertEquals("ben drenato, ricco di sostanze nutritive", pianta.getSoilType());
        assertEquals(18, pianta.getMinTemperature());
        assertEquals(24, pianta.getMaxTemperature());
        assertEquals(2.0, pianta.getMaxExpectedHeight(), 0.01);
    }

    @Test
    public void testSetters() {
        ArrayList<String> newFasi = new ArrayList<>();
        newFasi.add("fase3");

        pianta.setId("GIuCsu9ircjfN4RXWgxe");
        pianta.setName("Spinaci");
        pianta.setDescription("Pianta di spinaci (Spinacia oleracea)");
        pianta.setBotanicalFamily("Amaranthaceae");
        pianta.setSowingStart(3);
        pianta.setSowingEnd(5);
        pianta.setPhases(newFasi);
        pianta.setRequiredSpace(0.0);
        pianta.setSunExposure("mezz'ombra");
        pianta.setSoilType("ben drenato, ricco di humus");
        pianta.setMinTemperature(10);
        pianta.setMaxTemperature(20);
        pianta.setMaxExpectedHeight(0.3);

        assertEquals("GIuCsu9ircjfN4RXWgxe", pianta.getId());
        assertEquals("Spinaci", pianta.getName());
        assertEquals("Pianta di spinaci (Spinacia oleracea)", pianta.getDescription());
        assertEquals("Amaranthaceae", pianta.getBotanicalFamily());
        assertEquals(3, pianta.getSowingStart());
        assertEquals(5, pianta.getSowingEnd());
        assertEquals(1, pianta.getPhases().size());
        assertEquals("fase3", pianta.getPhases().get(0));
        assertEquals(0.0, pianta.getRequiredSpace(), 0.01);
        assertEquals("mezz'ombra", pianta.getSunExposure());
        assertEquals("ben drenato, ricco di humus", pianta.getSoilType());
        assertEquals(10, pianta.getMinTemperature());
        assertEquals(20, pianta.getMaxTemperature());
        assertEquals(0.3, pianta.getMaxExpectedHeight(), 0.01);
    }

    @Test
    public void testEqualsAndHashCode() {
        ArrayList<String> fasi1 = new ArrayList<>();
        fasi1.add("fase1");
        fasi1.add("fase2");

        Plant pianta1 = new Plant(
                "beVITqkLHWCerI1XLRxj",
                "pomodoro",
                "Pianta di pomodoro (Solanum lycopersicum)",
                "Solanaceae",
                3,
                4,
                fasi1,
                0.0,
                "pieno sole",
                "ben drenato, ricco di sostanze nutritive",
                18,
                24,
                2.0
        );

        ArrayList<String> fasi2 = new ArrayList<>();
        fasi2.add("fase1");
        fasi2.add("fase2");

        Plant pianta2 = new Plant(
                "beVITqkLHWCerI1XLRxj",
                "pomodoro",
                "Pianta di pomodoro (Solanum lycopersicum)",
                "Solanaceae",
                3,
                4,
                fasi2,
                0.0,
                "pieno sole",
                "ben drenato, ricco di sostanze nutritive",
                18,
                24,
                2.0
        );

        assertTrue(pianta1.equals(pianta2));
        assertEquals(pianta1.hashCode(), pianta2.hashCode());

        pianta2.setId("GIuCsu9ircjfN4RXWgxe");
        assertFalse(pianta1.equals(pianta2));
        assertNotEquals(pianta1.hashCode(), pianta2.hashCode());
    }

    @Test
    public void testToString() {
        String expectedToString = "Pianta{id='beVITqkLHWCerI1XLRxj', nome='pomodoro', descrizione='Pianta di pomodoro (Solanum lycopersicum)', famigliaBotanica='Solanaceae', inizioSemina=3, fineSemina=4, fasi=[fase1, fase2], spazioNecessario=0.0, esposizioneSole='pieno sole', tipoTerreno='ben drenato, ricco di sostanze nutritive', minTemperatura=18, maxTemperatura=24, altezzaMaxPrevista=2.0}";

        assertEquals(expectedToString, pianta.toString());
    }
}

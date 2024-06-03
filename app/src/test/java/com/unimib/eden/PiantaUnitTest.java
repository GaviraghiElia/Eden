package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.unimib.eden.model.Pianta;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PiantaUnitTest {
    private Pianta pianta;

    @Before
    public void setUp() {
        ArrayList<String> fasi = new ArrayList<>();
        fasi.add("fase1");
        fasi.add("fase2");

        pianta = new Pianta(
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
        assertEquals("pomodoro", pianta.getNome());
        assertEquals("Pianta di pomodoro (Solanum lycopersicum)", pianta.getDescrizione());
        assertEquals("Solanaceae", pianta.getFamigliaBotanica());
        assertEquals(3, pianta.getInizioSemina());
        assertEquals(4, pianta.getFineSemina());
        assertEquals(2, pianta.getFasi().size());
        assertEquals("fase1", pianta.getFasi().get(0));
        assertEquals(0.0, pianta.getSpazioNecessario(), 0.01);
        assertEquals("pieno sole", pianta.getEsposizioneSole());
        assertEquals("ben drenato, ricco di sostanze nutritive", pianta.getTipoTerreno());
        assertEquals(18, pianta.getMinTemperatura());
        assertEquals(24, pianta.getMaxTemperatura());
        assertEquals(2.0, pianta.getAltezzaMaxPrevista(), 0.01);
    }

    @Test
    public void testGetters() {
        assertEquals("beVITqkLHWCerI1XLRxj", pianta.getId());
        assertEquals("pomodoro", pianta.getNome());
        assertEquals("Pianta di pomodoro (Solanum lycopersicum)", pianta.getDescrizione());
        assertEquals("Solanaceae", pianta.getFamigliaBotanica());
        assertEquals(3, pianta.getInizioSemina());
        assertEquals(4, pianta.getFineSemina());
        assertEquals(2, pianta.getFasi().size());
        assertEquals("fase1", pianta.getFasi().get(0));
        assertEquals(0.0, pianta.getSpazioNecessario(), 0.01);
        assertEquals("pieno sole", pianta.getEsposizioneSole());
        assertEquals("ben drenato, ricco di sostanze nutritive", pianta.getTipoTerreno());
        assertEquals(18, pianta.getMinTemperatura());
        assertEquals(24, pianta.getMaxTemperatura());
        assertEquals(2.0, pianta.getAltezzaMaxPrevista(), 0.01);
    }

    @Test
    public void testSetters() {
        ArrayList<String> newFasi = new ArrayList<>();
        newFasi.add("fase3");

        pianta.setId("GIuCsu9ircjfN4RXWgxe");
        pianta.setNome("Spinaci");
        pianta.setDescrizione("Pianta di spinaci (Spinacia oleracea)");
        pianta.setFamigliaBotanica("Amaranthaceae");
        pianta.setInizioSemina(3);
        pianta.setFineSemina(5);
        pianta.setFasi(newFasi);
        pianta.setSpazioNecessario(0.0);
        pianta.setEsposizioneSole("mezz'ombra");
        pianta.setTipoTerreno("ben drenato, ricco di humus");
        pianta.setMinTemperatura(10);
        pianta.setMaxTemperatura(20);
        pianta.setAltezzaMaxPrevista(0.3);

        assertEquals("GIuCsu9ircjfN4RXWgxe", pianta.getId());
        assertEquals("Spinaci", pianta.getNome());
        assertEquals("Pianta di spinaci (Spinacia oleracea)", pianta.getDescrizione());
        assertEquals("Amaranthaceae", pianta.getFamigliaBotanica());
        assertEquals(3, pianta.getInizioSemina());
        assertEquals(5, pianta.getFineSemina());
        assertEquals(1, pianta.getFasi().size());
        assertEquals("fase3", pianta.getFasi().get(0));
        assertEquals(0.0, pianta.getSpazioNecessario(), 0.01);
        assertEquals("mezz'ombra", pianta.getEsposizioneSole());
        assertEquals("ben drenato, ricco di humus", pianta.getTipoTerreno());
        assertEquals(10, pianta.getMinTemperatura());
        assertEquals(20, pianta.getMaxTemperatura());
        assertEquals(0.3, pianta.getAltezzaMaxPrevista(), 0.01);
    }

    @Test
    public void testEqualsAndHashCode() {
        ArrayList<String> fasi1 = new ArrayList<>();
        fasi1.add("fase1");
        fasi1.add("fase2");

        Pianta pianta1 = new Pianta(
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

        Pianta pianta2 = new Pianta(
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

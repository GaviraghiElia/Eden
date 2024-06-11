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

public class PlantUnitTest {
    private Plant plant;

    @Before
    public void setUp() {
        ArrayList<String> phases = new ArrayList<>();
        phases.add("phase1");
        phases.add("phase2");

        plant = new Plant(
                "beVITqkLHWCerI1XLRxj",
                "tomato",
                "Tomato plant (Solanum lycopersicum)",
                "Solanaceae",
                3,
                4,
                phases,
                0.0,
                "full sun",
                "well-drained, nutrient-rich",
                18,
                24,
                2.0
        );
    }

    @Test
    public void testConstructor() {
        assertNotNull(plant);
        assertEquals("beVITqkLHWCerI1XLRxj", plant.getId());
        assertEquals("tomato", plant.getName());
        assertEquals("Tomato plant (Solanum lycopersicum)", plant.getDescription());
        assertEquals("Solanaceae", plant.getBotanicalFamily());
        assertEquals(3, plant.getSowingStart());
        assertEquals(4, plant.getSowingEnd());
        assertEquals(2, plant.getPhases().size());
        assertEquals("phase1", plant.getPhases().get(0));
        assertEquals(0.0, plant.getRequiredSpace(), 0.01);
        assertEquals("full sun", plant.getSunExposure());
        assertEquals("well-drained, nutrient-rich", plant.getSoilType());
        assertEquals(18, plant.getMinTemperature());
        assertEquals(24, plant.getMaxTemperature());
        assertEquals(2.0, plant.getMaxExpectedHeight(), 0.01);
    }

    @Test
    public void testGetters() {
        assertEquals("beVITqkLHWCerI1XLRxj", plant.getId());
        assertEquals("tomato", plant.getName());
        assertEquals("Tomato plant (Solanum lycopersicum)", plant.getDescription());
        assertEquals("Solanaceae", plant.getBotanicalFamily());
        assertEquals(3, plant.getSowingStart());
        assertEquals(4, plant.getSowingEnd());
        assertEquals(2, plant.getPhases().size());
        assertEquals("phase1", plant.getPhases().get(0));
        assertEquals(0.0, plant.getRequiredSpace(), 0.01);
        assertEquals("full sun", plant.getSunExposure());
        assertEquals("well-drained, nutrient-rich", plant.getSoilType());
        assertEquals(18, plant.getMinTemperature());
        assertEquals(24, plant.getMaxTemperature());
        assertEquals(2.0, plant.getMaxExpectedHeight(), 0.01);
    }

    @Test
    public void testSetters() {
        ArrayList<String> newPhases = new ArrayList<>();
        newPhases.add("phase3");

        plant.setId("GIuCsu9ircjfN4RXWgxe");
        plant.setName("Spinach");
        plant.setDescription("Spinach plant (Spinacia oleracea)");
        plant.setBotanicalFamily("Amaranthaceae");
        plant.setSowingStart(3);
        plant.setSowingEnd(5);
        plant.setPhases(newPhases);
        plant.setRequiredSpace(0.0);
        plant.setSunExposure("partial shade");
        plant.setSoilType("well-drained, rich in humus");
        plant.setMinTemperature(10);
        plant.setMaxTemperature(20);
        plant.setMaxExpectedHeight(0.3);

        assertEquals("GIuCsu9ircjfN4RXWgxe", plant.getId());
        assertEquals("Spinach", plant.getName());
        assertEquals("Spinach plant (Spinacia oleracea)", plant.getDescription());
        assertEquals("Amaranthaceae", plant.getBotanicalFamily());
        assertEquals(3, plant.getSowingStart());
        assertEquals(5, plant.getSowingEnd());
        assertEquals(1, plant.getPhases().size());
        assertEquals("phase3", plant.getPhases().get(0));
        assertEquals(0.0, plant.getRequiredSpace(), 0.01);
        assertEquals("partial shade", plant.getSunExposure());
        assertEquals("well-drained, rich in humus", plant.getSoilType());
        assertEquals(10, plant.getMinTemperature());
        assertEquals(20, plant.getMaxTemperature());
        assertEquals(0.3, plant.getMaxExpectedHeight(), 0.01);
    }

    @Test
    public void testEqualsAndHashCode() {
        ArrayList<String> phases1 = new ArrayList<>();
        phases1.add("phase1");
        phases1.add("phase2");

        Plant plant1 = new Plant(
                "beVITqkLHWCerI1XLRxj",
                "tomato",
                "Tomato plant (Solanum lycopersicum)",
                "Solanaceae",
                3,
                4,
                phases1,
                0.0,
                "full sun",
                "well-drained, nutrient-rich",
                18,
                24,
                2.0
        );

        ArrayList<String> phases2 = new ArrayList<>();
        phases2.add("phase1");
        phases2.add("phase2");

        Plant plant2 = new Plant(
                "beVITqkLHWCerI1XLRxj",
                "tomato",
                "Tomato plant (Solanum lycopersicum)",
                "Solanaceae",
                3,
                4,
                phases2,
                0.0,
                "full sun",
                "well-drained, nutrient-rich",
                18,
                24,
                2.0
        );

        assertTrue(plant1.equals(plant2));
        assertEquals(plant1.hashCode(), plant2.hashCode());

        plant2.setId("GIuCsu9ircjfN4RXWgxe");
        assertFalse(plant1.equals(plant2));
        assertNotEquals(plant1.hashCode(), plant2.hashCode());
    }

    @Test
    public void testToString() {
        String expectedToString = "Plant{id='beVITqkLHWCerI1XLRxj', name='tomato', description='Tomato plant (Solanum lycopersicum)', botanicalFamily='Solanaceae', sowingStart=3, sowingEnd=4, phases=[phase1, phase2], requiredSpace=0.0, sunExposure='full sun', soilType='well-drained, nutrient-rich', minTemperature=18, maxTemperature=24, maxExpectedHeight=2.0}";

        assertEquals(expectedToString, plant.toString());
    }
}

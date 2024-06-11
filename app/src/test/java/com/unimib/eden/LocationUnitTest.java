package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.unimib.eden.model.weather.Location;

public class LocationUnitTest {
    private Location location;

    @Before
    public void setUp() {
        location = new Location("Milan", "Lombardy", "Italy", 45.4642, 9.1900);
    }

    @Test
    public void testConstructor() {
        assertNotNull(location);
        assertEquals("Milan", location.getName());
        assertEquals("Lombardy", location.getRegion());
        assertEquals("Italy", location.getCountry());
        assertEquals(45.4642, location.getLat(), 0.0001);
        assertEquals(9.1900, location.getLon(), 0.0001);
    }

    @Test
    public void testGetName() {
        assertEquals("Milan", location.getName());
    }

    @Test
    public void testSetName() {
        location.setName("Rome");
        assertEquals("Rome", location.getName());
    }

    @Test
    public void testGetRegion() {
        assertEquals("Lombardy", location.getRegion());
    }

    @Test
    public void testSetRegion() {
        location.setRegion("Lazio");
        assertEquals("Lazio", location.getRegion());
    }

    @Test
    public void testGetCountry() {
        assertEquals("Italy", location.getCountry());
    }

    @Test
    public void testSetCountry() {
        location.setCountry("France");
        assertEquals("France", location.getCountry());
    }

    @Test
    public void testGetLat() {
        assertEquals(45.4642, location.getLat(), 0.0001);
    }

    @Test
    public void testSetLat() {
        location.setLat(41.9028);
        assertEquals(41.9028, location.getLat(), 0.0001);
    }

    @Test
    public void testGetLon() {
        assertEquals(9.1900, location.getLon(), 0.0001);
    }

    @Test
    public void testSetLon() {
        location.setLon(12.4964);
        assertEquals(12.4964, location.getLon(), 0.0001);
    }

    @Test
    public void testToString() {
        String expectedString = "Location{name='Milan', region='Lombardy', country='Italy', lat=45.4642, lon=9.19}";
        assertEquals(expectedString, location.toString());
    }
}


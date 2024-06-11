package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.unimib.eden.model.weather.WeatherSearchLocation;

public class WeatherSearchLocationUnitTest {

    private WeatherSearchLocation weatherSearchLocation;

    @Before
    public void setUp() {
        weatherSearchLocation = new WeatherSearchLocation();
        weatherSearchLocation.setId(1);
        weatherSearchLocation.setName("Milan");
        weatherSearchLocation.setRegion("Lombardy");
        weatherSearchLocation.setCountry("Italy");
        weatherSearchLocation.setLat(45.4642);
        weatherSearchLocation.setLon(9.1900);
        weatherSearchLocation.setUrl("http://example.com/milan");
    }

    @Test
    public void testConstructor() {
        assertNotNull(weatherSearchLocation);
    }

    @Test
    public void testGetAndSetId() {
        weatherSearchLocation.setId(2);
        assertEquals(2, weatherSearchLocation.getId());
    }

    @Test
    public void testGetAndSetName() {
        weatherSearchLocation.setName("Rome");
        assertEquals("Rome", weatherSearchLocation.getName());
    }

    @Test
    public void testGetAndSetRegion() {
        weatherSearchLocation.setRegion("Lazio");
        assertEquals("Lazio", weatherSearchLocation.getRegion());
    }

    @Test
    public void testGetAndSetCountry() {
        weatherSearchLocation.setCountry("France");
        assertEquals("France", weatherSearchLocation.getCountry());
    }

    @Test
    public void testGetAndSetLat() {
        weatherSearchLocation.setLat(41.9028);
        assertEquals(41.9028, weatherSearchLocation.getLat(), 0);
    }

    @Test
    public void testGetAndSetLon() {
        weatherSearchLocation.setLon(12.4964);
        assertEquals(12.4964, weatherSearchLocation.getLon(), 0);
    }

    @Test
    public void testGetAndSetUrl() {
        weatherSearchLocation.setUrl("http://example.com/rome");
        assertEquals("http://example.com/rome", weatherSearchLocation.getUrl());
    }

    @Test
    public void testToString() {
        String expectedString = "WeatherSearchLocation{id=1, name='Milan', region='Lombardy', country='Italy', lat=45.4642, lon=9.19, url='http://example.com/milan'}";
        assertEquals(expectedString, weatherSearchLocation.toString());
    }
}

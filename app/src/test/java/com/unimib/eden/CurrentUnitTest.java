package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.unimib.eden.model.weather.Condition;
import com.unimib.eden.model.weather.Current;

import org.junit.Before;
import org.junit.Test;

public class CurrentUnitTest {
    private Current current;
    private Condition condition;

    @Before
    public void setUp() {
        condition = new Condition("Sunny", "http://example.com/sunny.png");
        current = new Current("2024-05-29 10:00", 25.0, condition, 5.0, 0.2, 60.0);
    }

    @Test
    public void testConstructor() {
        assertNotNull(current);
        assertEquals("2024-05-29 10:00", current.getLastUpdated());
        assertEquals(25.0, current.getTemp_c(), 0.01);
        assertEquals(condition, current.getCondition());
        assertEquals(5.0, current.getPrecipitations_mm(), 0.01);
        assertEquals(0.2, current.getPrecipitations_in(), 0.01);
        assertEquals(60.0, current.getHumidity(), 0.01);
    }

    @Test
    public void testGetters() {
        assertEquals("2024-05-29 10:00", current.getLastUpdated());
        assertEquals(25.0, current.getTemp_c(), 0.01);
        assertEquals(condition, current.getCondition());
        assertEquals(5.0, current.getPrecipitations_mm(), 0.01);
        assertEquals(0.2, current.getPrecipitations_in(), 0.01);
        assertEquals(60.0, current.getHumidity(), 0.01);
    }

    @Test
    public void testSetters() {
        Condition newCondition = new Condition("Rainy", "http://example.com/rainy.png");

        current.setLastUpdated("2024-05-30 11:00");
        current.setTemp_c(18.0);
        current.setCondition(newCondition);
        current.setPrecipitations_mm(10.0);
        current.setPrecipitations_in(0.4);
        current.setHumidity(80.0);

        assertEquals("2024-05-30 11:00", current.getLastUpdated());
        assertEquals(18.0, current.getTemp_c(), 0.01);
        assertEquals(newCondition, current.getCondition());
        assertEquals(10.0, current.getPrecipitations_mm(), 0.01);
        assertEquals(0.4, current.getPrecipitations_in(), 0.01);
        assertEquals(80.0, current.getHumidity(), 0.01);
    }

    @Test
    public void testToString() {
        String expectedString = "Current{last_updated='2024-05-29 10:00', temp_c=25.0, condition=Condition{text='Sunny', icon='http://example.com/sunny.png'}, precipitations_mm=5.0, precipitations_in=0.2, humidity=60.0}";
        assertEquals(expectedString, current.toString());
    }
}

package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.unimib.eden.model.weather.Condition;
import com.unimib.eden.model.weather.Day;

import org.junit.Before;
import org.junit.Test;

public class DayUnitTest {
    private Day day;
    private Condition condition;

    @Before
    public void setUp() {
        condition = new Condition("Cloudy", "http://example.com/cloudy.png");
        day = new Day(30.0, 20.0, 25.0, 10.0, 70, 1, 50, condition);
    }

    @Test
    public void testConstructor() {
        assertNotNull(day);
        assertEquals(30.0, day.getMaxtemp_c(), 0.01);
        assertEquals(20.0, day.getMintemp_c(), 0.01);
        assertEquals(25.0, day.getAvgtemp_c(), 0.01);
        assertEquals(10.0, day.getTotalprecip_mm(), 0.01);
        assertEquals(70, day.getAvghumidity());
        assertEquals(1, day.getDaily_will_it_rain());
        assertEquals(50, day.getDaily_chance_of_rain());
        assertEquals(condition, day.getCondition());
    }

    @Test
    public void testGetters() {
        assertEquals(30.0, day.getMaxtemp_c(), 0.01);
        assertEquals(20.0, day.getMintemp_c(), 0.01);
        assertEquals(25.0, day.getAvgtemp_c(), 0.01);
        assertEquals(10.0, day.getTotalprecip_mm(), 0.01);
        assertEquals(70, day.getAvghumidity());
        assertEquals(1, day.getDaily_will_it_rain());
        assertEquals(50, day.getDaily_chance_of_rain());
        assertEquals(condition, day.getCondition());
    }

    @Test
    public void testToString() {
        String expectedString = "Day{maxtemp_c=30.0, mintemp_c=20.0, avgtemp_c=25.0, totalprecip_mm=10.0, avghumidity=70, daily_will_it_rain=1, daily_chance_of_rain=50, condition=Condition{text='Cloudy', icon='http://example.com/cloudy.png'}}";
        assertEquals(expectedString, day.toString());
    }
}
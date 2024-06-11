package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.unimib.eden.model.weather.Condition;
import com.unimib.eden.model.weather.Day;
import com.unimib.eden.model.weather.ForecastDay;

import org.junit.Before;
import org.junit.Test;

public class ForecastDayUnitTest {
    private ForecastDay forecastDay;
    private Day day;

    @Before
    public void setUp() {
        Condition condition = new Condition("Sunny", "http://example.com/sunny.png");
        day = new Day(30.0, 20.0, 25.0, 10.0, 70, 1, 50, condition);
        forecastDay = new ForecastDay("2024-05-01", day);
    }

    @Test
    public void testConstructor() {
        assertNotNull(forecastDay);
        assertEquals("2024-05-01", forecastDay.getDate());
        assertEquals(day, forecastDay.getDay());
    }

    @Test
    public void testGetDate() {
        assertEquals("2024-05-01", forecastDay.getDate());
    }

    @Test
    public void testGetDay() {
        assertEquals(day, forecastDay.getDay());
    }

    @Test
    public void testToString() {
        String expectedString = "\nForecastDay{" +
                "date='2024-05-01'" +
                "\nday=Day{maxtemp_c=30.0, mintemp_c=20.0, avgtemp_c=25.0, totalprecip_mm=10.0, avghumidity=70, daily_will_it_rain=1, daily_chance_of_rain=50, condition=Condition{text='Sunny', icon='http://example.com/sunny.png'}}" +
                '}';
        assertEquals(expectedString, forecastDay.toString());
    }
}

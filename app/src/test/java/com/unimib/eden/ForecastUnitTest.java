package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.unimib.eden.model.weather.Condition;
import com.unimib.eden.model.weather.Day;
import com.unimib.eden.model.weather.Forecast;
import com.unimib.eden.model.weather.ForecastDay;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ForecastUnitTest {
    private Forecast forecast;
    private List<ForecastDay> forecastDays;

    @Before
    public void setUp() {
        Condition condition1 = new Condition("Sunny", "http://example.com/sunny.png");
        Condition condition2 = new Condition("Rainy", "http://example.com/rainy.png");

        ForecastDay forecastDay1 = new ForecastDay("2024-05-01", new Day(30.0, 20.0, 25.0, 10.0, 70, 1, 50, condition1));
        ForecastDay forecastDay2 = new ForecastDay("2024-05-02", new Day(28.0, 18.0, 23.0, 15.0, 75, 1, 60, condition2));

        forecastDays = Arrays.asList(forecastDay1, forecastDay2);
        forecast = new Forecast(forecastDays);
    }

    @Test
    public void testConstructor() {
        assertNotNull(forecast);
        assertEquals(forecastDays, forecast.getForecastday());
    }

    @Test
    public void testGetForecastday() {
        assertEquals(forecastDays, forecast.getForecastday());
    }

    @Test
    public void testToString() {
        String expectedString = "Forecast{forecastday=[\n" +
                "ForecastDay{date='2024-05-01'\n" +
                "day=Day{maxtemp_c=30.0, mintemp_c=20.0, avgtemp_c=25.0, totalprecip_mm=10.0, avghumidity=70, daily_will_it_rain=1, daily_chance_of_rain=50, condition=Condition{text='Sunny', icon='http://example.com/sunny.png'}}}, \n" +
                "ForecastDay{date='2024-05-02'\n" +
                "day=Day{maxtemp_c=28.0, mintemp_c=18.0, avgtemp_c=23.0, totalprecip_mm=15.0, avghumidity=75, daily_will_it_rain=1, daily_chance_of_rain=60, condition=Condition{text='Rainy', icon='http://example.com/rainy.png'}}}]}";
        assertEquals(expectedString, forecast.toString());
    }
}

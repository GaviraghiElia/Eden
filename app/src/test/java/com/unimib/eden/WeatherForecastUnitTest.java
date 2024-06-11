package com.unimib.eden;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.unimib.eden.model.weather.Condition;
import com.unimib.eden.model.weather.Current;
import com.unimib.eden.model.weather.Day;
import com.unimib.eden.model.weather.Forecast;
import com.unimib.eden.model.weather.ForecastDay;
import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.model.weather.Location;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class WeatherForecastUnitTest {

    private WeatherForecast weatherForecast;
    private Location location;
    private Current current;
    private Forecast forecast;
    private Condition condition;
    private Day day;
    private ForecastDay forecastDay;

    @Before
    public void setUp() {
        location = new Location("Milan", "Lombardy", "Italy", 45.4642, 9.1900);
        condition = new Condition("Sunny", "http://example.com/sunny.png");
        current = new Current("2024-05-29 12:00", 25.0, condition, 0.0, 0.0, 50.0);
        day = new Day(30.0, 20.0, 25.0, 0.0, 50, 0, 10, condition);
        forecastDay = new ForecastDay("2024-05-29", day);
        forecast = new Forecast(Arrays.asList(forecastDay));
        weatherForecast = new WeatherForecast(location, current, forecast);
    }

    @Test
    public void testConstructor() {
        assertNotNull(weatherForecast);
        assertEquals(location, weatherForecast.getLocation());
        assertEquals(current, weatherForecast.getCurrent());
        assertEquals(forecast, weatherForecast.getForecast());
    }

    @Test
    public void testEmptyConstructor() {
        WeatherForecast emptyWeatherForecast = new WeatherForecast();
        assertNotNull(emptyWeatherForecast);
        assertNull(emptyWeatherForecast.getLocation());
        assertNull(emptyWeatherForecast.getCurrent());
        assertNull(emptyWeatherForecast.getForecast());
    }

    @Test
    public void testGetLocation() {
        assertEquals(location, weatherForecast.getLocation());
    }

    @Test
    public void testGetCurrent() {
        assertEquals(current, weatherForecast.getCurrent());
    }

    @Test
    public void testGetForecast() {
        assertEquals(forecast, weatherForecast.getForecast());
    }

    @Test
    public void testToString() {
        String expectedString = "\nLocation{name='Milan', region='Lombardy', country='Italy', lat=45.4642, lon=9.19}\n" +
                "Current{last_updated='2024-05-29 12:00', temp_c=25.0, condition=Condition{text='Sunny', icon='http://example.com/sunny.png'}, precipitations_mm=0.0, precipitations_in=0.0, humidity=50.0}\n" +
                "Forecast{forecastday=[\n" +
                "ForecastDay{date='2024-05-29'\n" +
                "day=Day{maxtemp_c=30.0, mintemp_c=20.0, avgtemp_c=25.0, totalprecip_mm=0.0, avghumidity=50, daily_will_it_rain=0, daily_chance_of_rain=10, condition=Condition{text='Sunny', icon='http://example.com/sunny.png'}}}]}}";
        assertEquals(expectedString, weatherForecast.toString());
    }
}


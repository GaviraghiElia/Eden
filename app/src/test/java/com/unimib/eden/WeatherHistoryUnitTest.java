package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import com.unimib.eden.model.weather.Condition;
import com.unimib.eden.model.weather.Current;
import com.unimib.eden.model.weather.Day;
import com.unimib.eden.model.weather.Forecast;
import com.unimib.eden.model.weather.ForecastDay;
import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.model.weather.Location;
import com.unimib.eden.model.weather.WeatherHistory;

public class WeatherHistoryUnitTest {

    private WeatherHistory weatherHistory;
    private Location location;
    private Forecast forecast;
    private Condition condition;
    private Day day;
    private ForecastDay forecastDay;

    @Before
    public void setUp() {
        location = new Location("Milan", "Lombardy", "Italy", 45.4642, 9.1900);
        condition = new Condition("Sunny", "http://example.com/sunny.png");
        day = new Day(30.0, 20.0, 25.0, 0.0, 50, 0, 10, condition);
        forecastDay = new ForecastDay("2024-05-29", day);
        forecast = new Forecast(Arrays.asList(forecastDay));
        weatherHistory = new WeatherHistory(location, forecast);
    }

    @Test
    public void testConstructor() {
        assertNotNull(weatherHistory);
        assertEquals(location, weatherHistory.getLocation());
        assertEquals(forecast, weatherHistory.getForecast());
    }

    @Test
    public void testSetLocation() {
        Location newLocation = new Location("Rome", "Lazio", "Italy", 41.9028, 12.4964);
        weatherHistory.setLocation(newLocation);
        assertEquals(newLocation, weatherHistory.getLocation());
    }

    @Test
    public void testSetForecast() {
        Day newDay = new Day(32.0, 22.0, 27.0, 1.0, 60, 1, 20, condition);
        ForecastDay newForecastDay = new ForecastDay("2024-05-30", newDay);
        Forecast newForecast = new Forecast(Arrays.asList(newForecastDay));
        weatherHistory.setForecast(newForecast);
        assertEquals(newForecast, weatherHistory.getForecast());
    }

    @Test
    public void testToString() {
        String expectedString = "WeatherHistory{location=Location{name='Milan', region='Lombardy', country='Italy', lat=45.4642, lon=9.19}, forecast=Forecast{forecastday=[\n" +
                "ForecastDay{date='2024-05-29'\n" +
                "day=Day{maxtemp_c=30.0, mintemp_c=20.0, avgtemp_c=25.0, totalprecip_mm=0.0, avghumidity=50, daily_will_it_rain=0, daily_chance_of_rain=10, condition=Condition{text='Sunny', icon='http://example.com/sunny.png'}}}]}}";
        assertEquals(expectedString, weatherHistory.toString());
    }
}

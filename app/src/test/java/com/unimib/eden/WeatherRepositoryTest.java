package com.unimib.eden;


import static org.mockito.Mockito.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.model.weather.WeatherHistory;
import com.unimib.eden.model.weather.WeatherSearchLocation;
import com.unimib.eden.repository.WeatherRepository;
import com.unimib.eden.service.WeatherService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepositoryTest {

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    private WeatherService mockService;

    @Mock
    private Call<WeatherHistory> mockCallHistory;

    @Mock
    private Call<List<WeatherSearchLocation>> mockCallSearchLocation;

    @Mock
    private Call<WeatherForecast> mockCallForecast;

    private WeatherRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        repository = new WeatherRepository(mockService);
    }

    /*@Test
    public void testGetHistory() {
        String location = "Milan";
        Date date = new Date();
        WeatherHistory mockHistory = new WeatherHistory();

        when(mockService.getHistory(anyString(), eq(location), eq(date.toString()))).thenReturn(mockCallHistory);
        doAnswer(invocation -> {
            Callback<WeatherHistory> callback = invocation.getArgument(0);
            callback.onResponse(null, Response.success(mockHistory));
            return null;
        }).when(mockCallHistory).enqueue(any());

        Observer<WeatherHistory> observer = mock(Observer.class);
        repository.getHistory(location, date).observeForever(observer);
        verify(observer).onChanged(mockHistory);
    }

    @Test
    public void testGetHistoryFailure() {
        String location = "Agrate Brianza";
        Date date = new Date();

        when(mockService.getHistory(anyString(), eq(location), eq(date.toString()))).thenReturn(mockCallHistory);
        doAnswer(invocation -> {
            Callback<WeatherHistory> callback = invocation.getArgument(0);
            callback.onFailure(null, new Throwable("Network error"));
            return null;
        }).when(mockCallHistory).enqueue(any());

        Observer<WeatherHistory> observer = mock(Observer.class);
        repository.getHistory(location, date).observeForever(observer);
        verify(observer, never()).onChanged(any());
    }*/

    @Test
    public void testGetSearchLocation() {
        String locationSearch = "Milan";
        List<WeatherSearchLocation> mockSearchLocation = Collections.singletonList(new WeatherSearchLocation());

        when(mockService.getAutocompleteSearch(anyString(), eq(locationSearch))).thenReturn(mockCallSearchLocation);
        doAnswer(invocation -> {
            Callback<List<WeatherSearchLocation>> callback = invocation.getArgument(0);
            callback.onResponse(null, Response.success(mockSearchLocation));
            return null;
        }).when(mockCallSearchLocation).enqueue(any());

        Observer<List<WeatherSearchLocation>> observer = mock(Observer.class);
        repository.getSearchlocation(locationSearch).observeForever(observer);
        verify(observer).onChanged(mockSearchLocation);
    }

    @Test
    public void testGetSearchLocationFailure() {
        String locationSearch = "Milan";

        when(mockService.getAutocompleteSearch(anyString(), eq(locationSearch))).thenReturn(mockCallSearchLocation);
        doAnswer(invocation -> {
            Callback<List<WeatherSearchLocation>> callback = invocation.getArgument(0);
            callback.onFailure(null, new Throwable("Network error"));
            return null;
        }).when(mockCallSearchLocation).enqueue(any());

        Observer<List<WeatherSearchLocation>> observer = mock(Observer.class);
        repository.getSearchlocation(locationSearch).observeForever(observer);
        verify(observer, never()).onChanged(any());
    }


    @Test
    public void testGetForecast() {
        String location = "Milan";
        int days = 7;
        String aqi = "yes";
        String alerts = "yes";
        WeatherForecast mockForecast = new WeatherForecast();

        when(mockService.getForecast(anyString(), eq(location), eq(days), eq(aqi), eq(alerts))).thenReturn(mockCallForecast);
        doAnswer(invocation -> {
            Callback<WeatherForecast> callback = invocation.getArgument(0);
            callback.onResponse(null, Response.success(mockForecast));
            return null;
        }).when(mockCallForecast).enqueue(any());

        Observer<WeatherForecast> observer = mock(Observer.class);
        repository.getForecast(location, days, aqi, alerts).observeForever(observer);
        verify(observer).onChanged(mockForecast);
    }


    @Test
    public void testGetForecastFailure() {
        String location = "Milan";
        int days = 7;
        String aqi = "yes";
        String alerts = "yes";

        when(mockService.getForecast(anyString(), eq(location), eq(days), eq(aqi), eq(alerts))).thenReturn(mockCallForecast);
        doAnswer(invocation -> {
            Callback<WeatherForecast> callback = invocation.getArgument(0);
            callback.onFailure(null, new Throwable("Network error"));
            return null;
        }).when(mockCallForecast).enqueue(any());

        Observer<WeatherForecast> observer = mock(Observer.class);
        repository.getForecast(location, days, aqi, alerts).observeForever(observer);
        verify(observer, never()).onChanged(any());
    }

}

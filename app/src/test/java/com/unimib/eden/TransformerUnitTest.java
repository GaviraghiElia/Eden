package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;

import com.unimib.eden.model.Crop;
import com.unimib.eden.utils.Transformer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class TransformerUnitTest {

    private Transformer transformer;

    @Before
    public void setUp() throws Exception {
        transformer = new Transformer();
    }

    @Test
    public void daysToNextWatering_ReturnsCorrectDays() {
        Crop crop = mock(Crop.class);

        Date currentDate = new Date();
        Date lastWatering = new Date(currentDate.getTime() - 5 * 24 * 60 * 60 * 1000); // 5 days ago
        when(crop.getCurrentPhase()).thenReturn(0);
        when(crop.getLastWatering()).thenReturn(lastWatering);
        when(crop.getWateringFrequency()).thenReturn(new ArrayList<>(Arrays.asList(5, 4, 4, 3, 3, 4, 7)));

        long daysRemaining = transformer.daysToNextWatering(crop);

        assertEquals(0, daysRemaining); // Days to next watering
    }

    @Test
    public void formatNextWatering_ReturnsCorrectString_ForZeroDaysRemaining() {
        Context context = mock(Context.class);
        Crop crop = mock(Crop.class);

        when(context.getString(R.string.today)).thenReturn("Oggi");
        Date currentDate = new Date();
        Date lastWatering = new Date(currentDate.getTime() - 5 * 24 * 60 * 60 * 1000); // 5 days ago
        when(crop.getCurrentPhase()).thenReturn(0);
        when(crop.getLastWatering()).thenReturn(lastWatering);
        when(crop.getWateringFrequency()).thenReturn(new ArrayList<>(Arrays.asList(5, 4, 4, 3, 3, 4, 7)));

        String formattedString = transformer.formatNextWatering(context, crop);

        assertEquals("Oggi", formattedString);
    }

    @Test
    public void formatNextWatering_ReturnsCorrectString_ForPositiveDaysRemaining() {
        Context context = mock(Context.class);
        Crop crop = mock(Crop.class);

        when(context.getString(R.string.within_days)).thenReturn("Tra %d giorni");
        when(crop.getCurrentPhase()).thenReturn(0);
        when(crop.getLastWatering()).thenReturn(new Date());
        when(crop.getWateringFrequency()).thenReturn(new ArrayList<>(Arrays.asList(5, 4, 4, 3, 3, 4, 7)));

        String formattedString = transformer.formatNextWatering(context, crop);

        assertEquals("Tra 5 giorni", formattedString);
    }

    @Test
    public void formatNextWatering_ReturnsCorrectString_ForOnePositiveDayRemaining() {
        Context context = mock(Context.class);
        Crop crop = mock(Crop.class);

        when(context.getString(R.string.tomorrow)).thenReturn("Domani");
        Date currentDate = new Date();
        Date lastWatering = new Date(currentDate.getTime() - 4 * 24 * 60 * 60 * 1000); // 4 days ago
        when(crop.getCurrentPhase()).thenReturn(0);
        when(crop.getLastWatering()).thenReturn(lastWatering);
        when(crop.getWateringFrequency()).thenReturn(new ArrayList<>(Arrays.asList(5, 4, 4, 3, 3, 4, 7)));

        String formattedString = transformer.formatNextWatering(context, crop);

        assertEquals("Domani", formattedString);
    }

    @Test
    public void formatNextWatering_ReturnsCorrectString_ForNegativeDaysRemaining() {
        Context context = mock(Context.class);
        Crop crop = mock(Crop.class);

        when(context.getString(R.string.days_ago)).thenReturn("In ritardo di %1$d giorni");
        Date currentDate = new Date();
        Date lastWatering = new Date(currentDate.getTime() - 5 * 24 * 60 * 60 * 1000); // 5 days ago
        when(crop.getCurrentPhase()).thenReturn(3);
        when(crop.getLastWatering()).thenReturn(lastWatering);
        when(crop.getWateringFrequency()).thenReturn(new ArrayList<>(Arrays.asList(5, 4, 4, 3, 3, 4, 7)));

        String formattedString = transformer.formatNextWatering(context, crop);

        assertEquals("In ritardo di 2 giorni", formattedString);
    }

    @Test
    public void formatNextWatering_ReturnsCorrectString_ForOneNegativeDayRemaining() {
        Context context = mock(Context.class);
        Crop crop = mock(Crop.class);

        when(context.getString(R.string.days_delay)).thenReturn("In ritardo di 1 giorno");
        Date currentDate = new Date();
        Date lastWatering = new Date(currentDate.getTime() - 5 * 24 * 60 * 60 * 1000); // 5 days ago
        when(crop.getCurrentPhase()).thenReturn(1);
        when(crop.getLastWatering()).thenReturn(lastWatering);
        when(crop.getWateringFrequency()).thenReturn(new ArrayList<>(Arrays.asList(5, 4, 4, 3, 3, 4, 7)));

        String formattedString = transformer.formatNextWatering(context, crop);

        assertEquals("In ritardo di 1 giorno", formattedString);
    }

    @Test
    public void testGetRelativeDateToday() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(new Date());
        assertEquals("Oggi", Transformer.getRelativeDate(today));
    }

    @Test
    public void testGetRelativeDateTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tomorrow = dateFormat.format(calendar.getTime());
        assertEquals("Domani", Transformer.getRelativeDate(tomorrow));
    }

    @Test
    public void testGetRelativeDateDayAfterTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dayAfterTomorrow = dateFormat.format(calendar.getTime());
        assertEquals("Dopodomani", Transformer.getRelativeDate(dayAfterTomorrow));
    }

    @Test
    public void testGetRelativeDateOther() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 5); // 5 days after today
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String otherDate = dateFormat.format(calendar.getTime());
        assertEquals("altro", Transformer.getRelativeDate(otherDate));
    }

    @Test
    public void testGetRelativeDateInvalidFormat() {
        String invalidDate = "31-12-2024"; // invalid format
        assertEquals("altro", Transformer.getRelativeDate(invalidDate));
    }
}

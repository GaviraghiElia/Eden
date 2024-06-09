package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.unimib.eden.utils.Converters;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ConvertersUnitTest {

    @Test
    public void testFromString() {
        String json = "[\"4\",\"5\",\"3\"]";
        ArrayList<String> expected = new ArrayList<>();
        expected.add("4");
        expected.add("5");
        expected.add("3");

        ArrayList<String> result = Converters.fromString(json);
        assertEquals(expected, result);
    }

    @Test
    public void testFromArrayList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("4");
        list.add("5");
        list.add("3");
        String expected = "[\"4\",\"5\",\"3\"]";

        String result = Converters.fromArrayList(list);
        assertEquals(expected, result);
    }

    @Test
    public void testFromStringToArrayInt() {
        String json = "[4,5,3]";
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(4);
        expected.add(5);
        expected.add(3);

        ArrayList<Integer> result = Converters.fromStringToArrayInt(json);
        assertEquals(expected, result);
    }

    @Test
    public void testFromArrayListInteger() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(5);
        list.add(3);
        String expected = "[4,5,3]";

        String result = Converters.fromArrayListInteger(list);
        assertEquals(expected, result);
    }

    @Test
    public void testFromTimestamp() {
        long timestamp = 1716935170723L;
        Date expected = new Date(timestamp);

        Date result = new Converters().fromTimestamp(timestamp);
        assertEquals(expected, result);
    }

    @Test
    public void testDateToTimestamp() {
        Date date = new Date(1716935170723L);
        Long expected = 1716935170723L;

        Long result = new Converters().dateToTimestamp(date);
        assertEquals(expected, result);
    }

    @Test
    public void testDateToString() {
        Date date = new Date(1716935170723L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        String expected = sdf.format(date);

        String result = Converters.dateToString(date);
        assertEquals(expected, result);
    }

    @Test
    public void testFromTimestamp_null() {
        assertNull(new Converters().fromTimestamp(null));
    }

    @Test
    public void testDateToTimestamp_null() {
        assertNull(new Converters().dateToTimestamp(null));
    }

    @Test
    public void testDateToString_null() {
        assertNull(Converters.dateToString(null));
    }
}

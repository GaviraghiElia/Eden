package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import com.unimib.eden.utils.ConvertIntMonthToString;

import org.junit.Before;
import org.junit.Test;

public class PlantDetailsActivityUnitTest {


    private ConvertIntMonthToString convertIntMonthToString;

    @Before
    public void setUp() throws Exception {
        convertIntMonthToString = new ConvertIntMonthToString();
    }
    @Test
    public void testGetMese() {

        assertEquals("Gennaio", convertIntMonthToString.getMonth(1));
        assertEquals("Febbraio", convertIntMonthToString.getMonth(2));
        assertEquals("Marzo", convertIntMonthToString.getMonth(3));
        assertEquals("Aprile", convertIntMonthToString.getMonth(4));
        assertEquals("Maggio", convertIntMonthToString.getMonth(5));
        assertEquals("Giugno", convertIntMonthToString.getMonth(6));
        assertEquals("Luglio", convertIntMonthToString.getMonth(7));
        assertEquals("Agosto", convertIntMonthToString.getMonth(8));
        assertEquals("Settembre", convertIntMonthToString.getMonth(9));
        assertEquals("Ottobre", convertIntMonthToString.getMonth(10));
        assertEquals("Novembre", convertIntMonthToString.getMonth(11));
        assertEquals("Dicembre", convertIntMonthToString.getMonth(12));
    }

}

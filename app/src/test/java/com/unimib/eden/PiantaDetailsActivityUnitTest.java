package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import com.unimib.eden.utils.ConvertIntMonthToString;

import org.junit.Before;
import org.junit.Test;

public class PiantaDetailsActivityUnitTest {


    private ConvertIntMonthToString convertIntMonthToString;

    @Before
    public void setUp() throws Exception {
        convertIntMonthToString = new ConvertIntMonthToString();
    }
    @Test
    public void testGetMese() {

        assertEquals("Gennaio", convertIntMonthToString.getMese(1));
        assertEquals("Febbraio", convertIntMonthToString.getMese(2));
        assertEquals("Marzo", convertIntMonthToString.getMese(3));
        assertEquals("Aprile", convertIntMonthToString.getMese(4));
        assertEquals("Maggio", convertIntMonthToString.getMese(5));
        assertEquals("Giugno", convertIntMonthToString.getMese(6));
        assertEquals("Luglio", convertIntMonthToString.getMese(7));
        assertEquals("Agosto", convertIntMonthToString.getMese(8));
        assertEquals("Settembre", convertIntMonthToString.getMese(9));
        assertEquals("Ottobre", convertIntMonthToString.getMese(10));
        assertEquals("Novembre", convertIntMonthToString.getMese(11));
        assertEquals("Dicembre", convertIntMonthToString.getMese(12));
    }

}

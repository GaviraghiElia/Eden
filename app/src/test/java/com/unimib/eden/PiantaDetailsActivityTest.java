package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import androidx.fragment.app.FragmentActivity;

import com.unimib.eden.ui.piantaDetails.PiantaDetailsActivity;
import com.unimib.eden.utils.ConvertIntMonthToString;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class PiantaDetailsActivityTest {


    @Test
    public void testGetMese() {

        assertEquals("Gennaio", ConvertIntMonthToString.getMese(1));
        assertEquals("Febbraio", ConvertIntMonthToString.getMese(2));
        assertEquals("Marzo", ConvertIntMonthToString.getMese(3));
        assertEquals("Aprile", ConvertIntMonthToString.getMese(4));
        assertEquals("Maggio", ConvertIntMonthToString.getMese(5));
        assertEquals("Giugno", ConvertIntMonthToString.getMese(6));
        assertEquals("Luglio", ConvertIntMonthToString.getMese(7));
        assertEquals("Agosto", ConvertIntMonthToString.getMese(8));
        assertEquals("Settembre", ConvertIntMonthToString.getMese(9));
        assertEquals("Ottobre", ConvertIntMonthToString.getMese(10));
        assertEquals("Novembre", ConvertIntMonthToString.getMese(11));
        assertEquals("Dicembre", ConvertIntMonthToString.getMese(12));
    }

}

package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;

import com.unimib.eden.R;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.utils.Transformer;

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

    /**
     * Testa il metodo per il calcolo dei giorni fino al prossimo innaffiamento.
     * Verifica che il metodo restituisca il numero corretto di giorni rimanenti.
     */
    @Test
    public void daysToProssimoInnaffiamento_ReturnsCorrectDays() {
        // Mocking objects
        Coltura coltura = mock(Coltura.class);
        Pianta pianta = mock(Pianta.class);

        // Stubbing methods
        Date currentDate = new Date();
        Date ultimoInnaffiamento = new Date(currentDate.getTime() - 5 * 24 * 60 * 60 * 1000); // 5 days ago
        when(coltura.getFaseAttuale()).thenReturn(0);
        when(coltura.getUltimoInnaffiamento()).thenReturn(ultimoInnaffiamento);
        when(coltura.getFrequenzaInnaffiamento()).thenReturn(new ArrayList<>(Arrays.asList(5, 4, 4, 3, 3, 4, 7)));
        //when(pianta.getFrequenzaInnaffiamento()).thenReturn(7); // La frequenza di innaffiamento è ogni 7 giorni

        // Calling the method to test
        long daysRemaining = Transformer.daysToProssimoInnaffiamento(coltura);

        // Asserting the result
        assertEquals(0, daysRemaining); // Giorni rimanenti fino al prossimo innaffiamento
    }

    /**
     * Testa il metodo per la formattazione della stringa per l'innaffiamento
     * successivo quando non ci sono giorni rimanenti.
     * Verifica che la stringa formattata sia corretta per la situazione in cui
     * l'innaffiamento è previsto per oggi.
     */
    @Test
    public void formatProssimoInnaffiamento_ReturnsCorrectString_ForZeroDaysRemaining() {
        // Mocking objects
        Context context = mock(Context.class);
        Coltura coltura = mock(Coltura.class);
        Pianta pianta = mock(Pianta.class);

        // Stubbing methods
        when(context.getString(R.string.oggi)).thenReturn("Oggi");
        Date currentDate = new Date();
        Date ultimoInnaffiamento = new Date(currentDate.getTime() - 5 * 24 * 60 * 60 * 1000); // 5 days ago
        when(coltura.getFaseAttuale()).thenReturn(0);
        when(coltura.getUltimoInnaffiamento()).thenReturn(ultimoInnaffiamento);
        when(coltura.getFrequenzaInnaffiamento()).thenReturn(new ArrayList<>(Arrays.asList(5, 4, 4, 3, 3, 4, 7)));
        //when(pianta.getFrequenzaInnaffiamento()).thenReturn(5); // La frequenza di innaffiamento è ogni giorno

        // Calling the method to test
        String formattedString = Transformer.formatProssimoInnaffiamento(context, coltura);

        // Asserting the result
        assertEquals("Oggi", formattedString); // Ci si aspetta "Oggi" per oggi
    }

    /**
     * Testa il metodo per la formattazione della stringa per l'innaffiamento
     * successivo quando ci sono giorni rimanenti.
     * Verifica che la stringa formattata sia corretta per la situazione in cui
     * l'innaffiamento è previsto tra un certo numero di giorni.
     */
    @Test
    public void formatProssimoInnaffiamento_ReturnsCorrectString_ForPositiveDaysRemaining() {
        // Mocking objects
        Context context = mock(Context.class);
        Coltura coltura = mock(Coltura.class);
        Pianta pianta = mock(Pianta.class);

        // Stubbing methods
        when(context.getString(R.string.tra_giorni)).thenReturn("Tra %d giorni");
        when(coltura.getFaseAttuale()).thenReturn(0);
        when(coltura.getUltimoInnaffiamento()).thenReturn(new Date());
        when(coltura.getFrequenzaInnaffiamento()).thenReturn(new ArrayList<>(Arrays.asList(5, 4, 4, 3, 3, 4, 7)));
        //when(pianta.getFrequenzaInnaffiamento()).thenReturn(7); // La frequenza di innaffiamento è ogni 7 giorni

        // Calling the method to test
        String formattedString = Transformer.formatProssimoInnaffiamento(context, coltura);

        // Asserting the result
        assertEquals("Tra 5 giorni", formattedString); // Ci si aspetta "Tra 7 giorni" per il prossimo innaffiamento fra 7 giorni
    }

    /**
     * Testa il metodo per la formattazione della stringa per l'innaffiamento
     * successivo quando manca un solo giorno.
     * Verifica che la stringa formattata sia corretta per la situazione in cui
     * l'innaffiamento è previsto per il giorno successivo.
     */
    @Test
    public void formatProssimoInnaffiamento_ReturnsCorrectString_ForOnePositiveDayRemaining() {
        // Mocking objects
        Context context = mock(Context.class);
        Coltura coltura = mock(Coltura.class);
        Pianta pianta = mock(Pianta.class);

        // Stubbing methods
        when(context.getString(R.string.domani)).thenReturn("Domani");
        Date currentDate = new Date();
        when(coltura.getFaseAttuale()).thenReturn(0);
        Date ultimoInnaffiamento = new Date(currentDate.getTime() - 4 * 24 * 60 * 60 * 1000); // 5 days ago
        when(coltura.getUltimoInnaffiamento()).thenReturn(ultimoInnaffiamento);
        when(coltura.getFrequenzaInnaffiamento()).thenReturn(new ArrayList<>(Arrays.asList(5, 4, 4, 3, 3, 4, 7)));
        //when(pianta.getFrequenzaInnaffiamento()).thenReturn(1); // La frequenza di innaffiamento è ogni giorno

        // Calling the method to test
        String formattedString = Transformer.formatProssimoInnaffiamento(context, coltura);

        // Asserting the result
        assertEquals("Domani", formattedString); // Ci si aspetta "Domani" per il prossimo innaffiamento
    }

    /**
     * Testa il metodo per la formattazione della stringa per l'innaffiamento
     * successivo quando ci sono giorni passati rispetto alla data prevista.
     * Verifica che la stringa formattata sia corretta per la situazione in cui
     * l'innaffiamento è già in ritardo di qualche giorno rispetto alla data prevista.
     */
    @Test
    public void formatProssimoInnaffiamento_ReturnsCorrectString_ForNegativeDaysRemaining() {
        // Mocking objects
        Context context = mock(Context.class);
        Coltura coltura = mock(Coltura.class);
        Pianta pianta = mock(Pianta.class);

        // Stubbing methods
        when(context.getString(R.string.giorni_fa)).thenReturn("In ritardo di %1$d giorni");
        Date currentDate = new Date();
        when(coltura.getFaseAttuale()).thenReturn(3);
        Date ultimoInnaffiamento = new Date(currentDate.getTime() - 5 * 24 * 60 * 60 * 1000); // 5 giorni fa
        when(coltura.getUltimoInnaffiamento()).thenReturn(ultimoInnaffiamento);
        when(coltura.getFrequenzaInnaffiamento()).thenReturn(new ArrayList<>(Arrays.asList(5, 4, 4, 3, 3, 4, 7)));
        //when(pianta.getFrequenzaInnaffiamento()).thenReturn(3); // La frequenza di innaffiamento è ogni giorno

        // Calling the method to test
        String formattedString = Transformer.formatProssimoInnaffiamento(context, coltura);

        // Asserting the result
        assertEquals("In ritardo di 2 giorni", formattedString); // Ci si aspetta "Ritardo di un giorno" per i giorni di ritardo
    }

    /**
     * Testa il metodo per la formattazione della stringa per l'innaffiamento
     * successivo quando c'è un giorno passato rispetto alla data prevista.
     * Verifica che la stringa formattata sia corretta per la situazione in cui
     * l'innaffiamento è in ritardo di un giorno rispetto alla data prevista.
     */
    @Test
    public void formatProssimoInnaffiamento_ReturnsCorrectString_ForOneNegativeDayRemaining() {
        // Mocking objects
        Context context = mock(Context.class);
        Coltura coltura = mock(Coltura.class);
        Pianta pianta = mock(Pianta.class);

        // Stubbing methods
        when(context.getString(R.string.ritardo_giorno)).thenReturn("In ritardo di 1 giorno");
        Date currentDate = new Date();
        when(coltura.getFaseAttuale()).thenReturn(1);
        Date ultimoInnaffiamento = new Date(currentDate.getTime() - 5 * 24 * 60 * 60 * 1000); // 5 giorni fa
        when(coltura.getUltimoInnaffiamento()).thenReturn(ultimoInnaffiamento);
        when(coltura.getFrequenzaInnaffiamento()).thenReturn(new ArrayList<>(Arrays.asList(5, 4, 4, 3, 3, 4, 7)));
        //when(pianta.getFrequenzaInnaffiamento()).thenReturn(4); // La frequenza di innaffiamento è ogni giorno

        // Calling the method to test
        String formattedString = Transformer.formatProssimoInnaffiamento(context, coltura);

        // Asserting the result
        assertEquals("In ritardo di 1 giorno", formattedString); // Ci si aspetta "Ritardo di un giorno" per il giorno di ritardo
    }

    /**
     * Testa il metodo getRelativeDate per la data di oggi.
     * Questo test imposta la data corrente nel formato "yyyy-MM-dd" e verifica che
     * il metodo getRelativeDate restituisca "Oggi".
     */
    @Test
    public void testGetRelativeDateToday() {
        // Otteniamo la data di oggi in formato yyyy-MM-dd
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(new Date());
        // Verifichiamo che il metodo restituisca "Oggi" per la data di oggi
        assertEquals("Oggi", Transformer.getRelativeDate(today));
    }

    /**
     * Testa il metodo getRelativeDate per la data di domani.
     * Questo test imposta la data di domani nel formato "yyyy-MM-dd" e verifica che
     * il metodo getRelativeDate restituisca "Domani".
     */
    @Test
    public void testGetRelativeDateTomorrow() {
        // Otteniamo la data di domani in formato yyyy-MM-dd
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tomorrow = dateFormat.format(calendar.getTime());
        // Verifichiamo che il metodo restituisca "Domani" per la data di domani
        assertEquals("Domani", Transformer.getRelativeDate(tomorrow));
    }

    /**
     * Testa il metodo getRelativeDate per la data di dopodomani.
     * Questo test imposta la data di dopodomani nel formato "yyyy-MM-dd" e verifica che
     * il metodo getRelativeDate restituisca "Dopodomani".
     */
    @Test
    public void testGetRelativeDateDayAfterTomorrow() {
        // Otteniamo la data di dopodomani in formato yyyy-MM-dd
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dayAfterTomorrow = dateFormat.format(calendar.getTime());
        // Verifichiamo che il metodo restituisca "Dopodomani" per la data di dopodomani
        assertEquals("Dopodomani", Transformer.getRelativeDate(dayAfterTomorrow));
    }

    /**
     * Testa il metodo getRelativeDate per una data diversa da oggi, domani o dopodomani.
     * Questo test imposta una data che è cinque giorni nel futuro nel formato "yyyy-MM-dd" e verifica che
     * il metodo getRelativeDate restituisca "altro".
     */
    @Test
    public void testGetRelativeDateOther() {
        // Otteniamo una data che non è oggi, domani, o dopodomani
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 5); // 5 giorni dopo oggi
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String otherDate = dateFormat.format(calendar.getTime());
        // Verifichiamo che il metodo restituisca "altro" per una data diversa da oggi, domani, e dopodomani
        assertEquals("altro", Transformer.getRelativeDate(otherDate));
    }

    /**
     * Testa il metodo getRelativeDate per un formato di data non valido.
     * Questo test fornisce una stringa di data con un formato non valido e verifica che
     * il metodo getRelativeDate restituisca "altro".
     */
    @Test
    public void testGetRelativeDateInvalidFormat() {
        // Passiamo una data in un formato non valido
        String invalidDate = "31-12-2024"; // formato non valido
        // Verifichiamo che il metodo restituisca "altro" per una data con un formato non valido
        assertEquals("altro", Transformer.getRelativeDate(invalidDate));
    }

}

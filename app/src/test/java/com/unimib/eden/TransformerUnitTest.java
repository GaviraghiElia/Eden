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
        when(coltura.getUltimoInnaffiamento()).thenReturn(ultimoInnaffiamento);
        when(coltura.getFrequenzaInnaffiamento()).thenReturn(7);
        //when(pianta.getFrequenzaInnaffiamento()).thenReturn(7); // La frequenza di innaffiamento è ogni 7 giorni

        // Calling the method to test
        int daysRemaining = Transformer.daysToProssimoInnaffiamento(coltura);

        // Asserting the result
        assertEquals(2, daysRemaining); // Giorni rimanenti fino al prossimo innaffiamento
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
        when(coltura.getUltimoInnaffiamento()).thenReturn(ultimoInnaffiamento);
        when(coltura.getFrequenzaInnaffiamento()).thenReturn(5);
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
        when(coltura.getUltimoInnaffiamento()).thenReturn(new Date());
        when(coltura.getFrequenzaInnaffiamento()).thenReturn(7);
        //when(pianta.getFrequenzaInnaffiamento()).thenReturn(7); // La frequenza di innaffiamento è ogni 7 giorni

        // Calling the method to test
        String formattedString = Transformer.formatProssimoInnaffiamento(context, coltura);

        // Asserting the result
        assertEquals("Tra 7 giorni", formattedString); // Ci si aspetta "Tra 7 giorni" per il prossimo innaffiamento fra 7 giorni
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
        when(coltura.getUltimoInnaffiamento()).thenReturn(new Date());
        when(coltura.getFrequenzaInnaffiamento()).thenReturn(1);
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
        Date ultimoInnaffiamento = new Date(currentDate.getTime() - 5 * 24 * 60 * 60 * 1000); // 5 giorni fa
        when(coltura.getUltimoInnaffiamento()).thenReturn(ultimoInnaffiamento);
        when(coltura.getFrequenzaInnaffiamento()).thenReturn(3);
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
        Date ultimoInnaffiamento = new Date(currentDate.getTime() - 5 * 24 * 60 * 60 * 1000); // 5 giorni fa
        when(coltura.getUltimoInnaffiamento()).thenReturn(ultimoInnaffiamento);
        when(coltura.getFrequenzaInnaffiamento()).thenReturn(4);
        //when(pianta.getFrequenzaInnaffiamento()).thenReturn(4); // La frequenza di innaffiamento è ogni giorno

        // Calling the method to test
        String formattedString = Transformer.formatProssimoInnaffiamento(context, coltura);

        // Asserting the result
        assertEquals("In ritardo di 1 giorno", formattedString); // Ci si aspetta "Ritardo di un giorno" per il giorno di ritardo
    }
}

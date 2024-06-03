package com.unimib.eden;

import static org.junit.Assert.assertEquals;

import com.unimib.eden.model.Utente;

import org.junit.Test;

public class UtenteUnitTest {
    @Test
    public void testGetSetNome() {
        // Create an instance of Utente
        Utente utente = new Utente();

        // Set the name
        utente.setNome("Elia");

        // Get the name and assert it
        assertEquals("Elia", utente.getNome());
    }

    @Test
    public void testGetSetEmail() {
        // Create an instance of Utente
        Utente utente = new Utente();

        // Set the email
        utente.setEmail("elia@gmail.com");

        // Get the email and assert it
        assertEquals("elia@gmail.com", utente.getEmail());
    }

    @Test
    public void testGetSetId() {
        // Create an instance of Utente
        Utente utente = new Utente();

        // Set the ID
        utente.setId("123456");

        // Get the ID and assert it
        assertEquals("123456", utente.getId());
    }
}

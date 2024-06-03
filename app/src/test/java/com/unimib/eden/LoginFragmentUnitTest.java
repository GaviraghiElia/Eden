package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.unimib.eden.utils.AuthenticationFieldValidator;

import org.junit.Before;
import org.junit.Test;

public class LoginFragmentUnitTest {

    private AuthenticationFieldValidator authenticationFieldValidator;

    @Before
    public void setUp() throws Exception {
        authenticationFieldValidator = new AuthenticationFieldValidator();
    }

    @Test
    public void testIsValidEmail() {
        assertTrue(authenticationFieldValidator.isValidEmail("test@example.com"));
        assertFalse(authenticationFieldValidator.isValidEmail("test@example"));
        assertFalse(authenticationFieldValidator.isValidEmail("test@.com"));
        assertFalse(authenticationFieldValidator.isValidEmail("test"));
    }

    @Test
    public void testIsValidPassword() {
        assertTrue(authenticationFieldValidator.isValidPassword("Test$1234"));
        assertFalse(authenticationFieldValidator.isValidPassword("test"));
        assertFalse(authenticationFieldValidator.isValidPassword("Test1234"));
        assertFalse(authenticationFieldValidator.isValidPassword("Test$abcd"));
    }


    @Test
    public void testIsValidCredential() {
        assertEquals("success", authenticationFieldValidator.isValidCredential("test@example.com", "Test@1234"));
        assertEquals("Email non valida", authenticationFieldValidator.isValidCredential("fd@.com", "Test@1234"));
        assertEquals("Password errata", authenticationFieldValidator.isValidCredential("test@example.com", "fre4"));
    }
}
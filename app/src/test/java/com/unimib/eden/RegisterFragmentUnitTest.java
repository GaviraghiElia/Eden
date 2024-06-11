package com.unimib.eden;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.unimib.eden.utils.AuthenticationFieldValidator;

public class RegisterFragmentUnitTest {

    private AuthenticationFieldValidator authenticationFieldValidator;

    @Before
    public void setUp() throws Exception {
        authenticationFieldValidator = new AuthenticationFieldValidator();
    }

    @Test
    public void testIsValidCredential() {
        assertEquals("success", authenticationFieldValidator.isValidCredential("test@example.com", "Test@1234"));
        assertEquals("Email non valida", authenticationFieldValidator.isValidCredential("fd@.com", "Test@1234"));
        assertEquals("Password errata", authenticationFieldValidator.isValidCredential("test@example.com", "fre4"));
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
        assertTrue(authenticationFieldValidator.isValidPassword("Test@1234"));
        assertFalse(authenticationFieldValidator.isValidPassword("test"));
        assertFalse(authenticationFieldValidator.isValidPassword("Test1234"));
        assertFalse(authenticationFieldValidator.isValidPassword("Test@abcd"));
    }

}

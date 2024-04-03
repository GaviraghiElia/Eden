package com.unimib.eden;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.unimib.eden.ui.authentication.RegisterFragment;

public class RegisterFragmentUnitTest {

    private RegisterFragment registerFragment;

    @Before
    public void setUp() throws Exception {
        registerFragment = new RegisterFragment();
    }

    @Test
    public void testIsValidCredential() {
        assertEquals("success", registerFragment.isValidCredential("test", "test@example.com", "Test@1234"));
    }

    @Test
    public void testIsValidEmail() {
        assertTrue(registerFragment.isValidEmail("test@example.com"));
        assertFalse(registerFragment.isValidEmail("test@example"));
        assertFalse(registerFragment.isValidEmail("test@.com"));
        assertFalse(registerFragment.isValidEmail("test"));
    }

    @Test
    public void testIsValidPassword() {
        assertTrue(registerFragment.isValidPassword("Test@1234"));
        assertFalse(registerFragment.isValidPassword("test"));
        assertFalse(registerFragment.isValidPassword("Test1234"));
        assertFalse(registerFragment.isValidPassword("Test@abcd"));
    }

}

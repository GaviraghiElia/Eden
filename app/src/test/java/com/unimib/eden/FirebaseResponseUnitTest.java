package com.unimib.eden;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.unimib.eden.model.FirebaseResponse;

import org.junit.Before;
import org.junit.Test;

public class FirebaseResponseUnitTest {

    private FirebaseResponse firebaseResponse;

    @Before
    public void setUp() {
        firebaseResponse = new FirebaseResponse();
    }

    @Test
    public void testConstructor() {
        // Testing the default constructor
        assertFalse(firebaseResponse.isSuccess());
        assertEquals(null, firebaseResponse.getMessage());
    }

    @Test
    public void testGetAndSetSuccess() {
        // Testing the setter and getter for success
        firebaseResponse.setSuccess(true);
        assertTrue(firebaseResponse.isSuccess());

        firebaseResponse.setSuccess(false);
        assertFalse(firebaseResponse.isSuccess());
    }

    @Test
    public void testGetAndSetMessage() {
        // Testing the setter and getter for message
        String testMessage = "Test message";
        firebaseResponse.setMessage(testMessage);
        assertEquals(testMessage, firebaseResponse.getMessage());

        testMessage = "Another test message";
        firebaseResponse.setMessage(testMessage);
        assertEquals(testMessage, firebaseResponse.getMessage());
    }
}


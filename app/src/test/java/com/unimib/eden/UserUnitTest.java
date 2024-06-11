package com.unimib.eden;

import static org.junit.Assert.assertEquals;

import com.unimib.eden.model.User;

import org.junit.Test;

public class UserUnitTest {
    @Test
    public void testGetSetName() {
        // Create an instance of User
        User user = new User();

        // Set the name
        user.setName("Elia");

        // Get the name and assert it
        assertEquals("Elia", user.getName());
    }

    @Test
    public void testGetSetEmail() {
        // Create an instance of User
        User user = new User();

        // Set the email
        user.setEmail("elia@gmail.com");

        // Get the email and assert it
        assertEquals("elia@gmail.com", user.getEmail());
    }

    @Test
    public void testGetSetId() {
        // Create an instance of User
        User user = new User();

        // Set the ID
        user.setId("123456");

        // Get the ID and assert it
        assertEquals("123456", user.getId());
    }
}

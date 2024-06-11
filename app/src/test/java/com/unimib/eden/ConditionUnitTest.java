package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.unimib.eden.model.weather.Condition;

import org.junit.Before;
import org.junit.Test;

public class ConditionUnitTest {
    private Condition condition;

    @Before
    public void setUp() {
        condition = new Condition("Sunny", "http://example.com/sunny.png");
    }

    @Test
    public void testConstructor() {
        assertNotNull(condition);
        assertEquals("Sunny", condition.getText());
        assertEquals("http://example.com/sunny.png", condition.getIcon());
    }

    @Test
    public void testGetters() {
        assertEquals("Sunny", condition.getText());
        assertEquals("http://example.com/sunny.png", condition.getIcon());
    }

    @Test
    public void testToString() {
        String expectedString = "Condition{text='Sunny', icon='http://example.com/sunny.png'}";
        assertEquals(expectedString, condition.toString());
    }
}

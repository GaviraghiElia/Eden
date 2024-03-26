package com.unimib.eden;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.unimib.eden.ui.home.HomeFragment;

@RunWith(MockitoJUnitRunner.class)
public class HomeFragmentUnitTest {

    @Mock
    LayoutInflater mockInflater;

    @Mock
    private ViewGroup mockContainer;
    @Mock
    private Bundle mockSavedInstanceState;

    HomeFragment homeFragment;

    @Before
    public void setUp() {
        //homeFragment = new HomeFragment();
        MockitoAnnotations.initMocks(this);
        homeFragment = new HomeFragment();

    }

    @Test
    public void testOnCreateView() {
        // Mock inflater behavior
        when(mockInflater.inflate(Mockito.anyInt(), Mockito.any(ViewGroup.class), Mockito.anyBoolean()))
                .thenReturn(new View(homeFragment.getContext()));

        // Invoke onCreateView method
        View view = homeFragment.onCreateView(mockInflater, mockContainer, mockSavedInstanceState);

        // Verify that the view is not null
        assertNotNull(view);
    }
}
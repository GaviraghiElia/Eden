package com.unimib.eden;


import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unimib.eden.ui.home.HomeFragment;
import com.unimib.eden.ui.profile.ProfileFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProfileFragmentUnitTest {

    @Mock
    LayoutInflater mockInflater;

    @Mock
    private ViewGroup mockContainer;
    @Mock
    private Bundle mockSavedInstanceState;

    ProfileFragment profileFragment;

    @Before
    public void setUp() {
        //homeFragment = new HomeFragment();
        MockitoAnnotations.initMocks(this);
        profileFragment = new ProfileFragment();

    }

    @Test
    public void testOnCreateView() {
        // Mock inflater behavior
        when(mockInflater.inflate(Mockito.anyInt(), Mockito.any(ViewGroup.class), Mockito.anyBoolean()))
                .thenReturn(new View(profileFragment.getContext()));

        // Invoke onCreateView method
        View view = profileFragment.onCreateView(mockInflater, mockContainer, mockSavedInstanceState);

        // Verify that the view is not null
        assertNotNull(view);
    }
}

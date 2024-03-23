package com.unimib.eden;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unimib.eden.ui.bancarella.BancarellaFragment;
import com.unimib.eden.ui.home.HomeFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
public class BancarellaFragmentUnitTest {

    private static final String TAG = "BancarellaFragmentUnitTest";
    @Mock
    LayoutInflater mockInflater;

    @Mock
    private ViewGroup mockContainer;
    @Mock
    private Bundle mockSavedInstanceState;

    BancarellaFragment bancarellaFragment;

    @Before
    public void setUp() {
        //homeFragment = new HomeFragment();
        MockitoAnnotations.initMocks(this);
        bancarellaFragment = new BancarellaFragment();

    }

    @Test
    public void testOnCreateView() {
        // Mock inflater behavior
        when(mockInflater.inflate(Mockito.anyInt(), Mockito.any(ViewGroup.class), Mockito.anyBoolean()))
                .thenReturn(new View(bancarellaFragment.getContext()));

        // Invoke onCreateView method
        View view = bancarellaFragment.onCreateView(mockInflater, mockContainer, mockSavedInstanceState);

        // Verify that the view is not null
        assertNotNull(view);
    }

    @Test
    public void testFail() {
        //assert false;
    }
}

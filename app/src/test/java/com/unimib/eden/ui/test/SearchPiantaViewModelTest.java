package com.unimib.eden.ui.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.PiantaRepository;
import com.unimib.eden.ui.searchPianta.SearchPiantaViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RunWith(MockitoJUnitRunner.class)
//@RunWith(AndroidJUnit4.class)
public class SearchPiantaViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private PiantaRepository mockRepository;

    @Mock
    private Observer<List<Pianta>> mockObserver;

    private SearchPiantaViewModel viewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Initialize mocks

        // Mock FirebaseApp and FirebaseFirestore
        FirebaseApp mockFirebaseApp = mock(FirebaseApp.class);
        when(FirebaseApp.initializeApp(any())).thenReturn(mockFirebaseApp);

        FirebaseFirestore mockFirestore = mock(FirebaseFirestore.class);
        when(FirebaseFirestore.getInstance()).thenReturn(mockFirestore);

        // Initialize view model
        viewModel = new SearchPiantaViewModel(mock(Application.class));
        viewModel.getPiantaList().observeForever(mockObserver);
    }

    @Test
    public void testSearchPianta() throws ExecutionException, InterruptedException {
        // Given
        String query = "pomodoro";
        List<Pianta> piantaList = new ArrayList<>();
        ArrayList<String> fasi = new ArrayList<String>();
        fasi.add("QhhUcsLVFpDppyxen9tM");
        fasi.add("CmM3qdb9aMTVWbrEQdZe");
        fasi.add("mtqfP931yhNDQzFRN8RU");
        fasi.add("IfjXQLn98rnUDu1rax8h");
        fasi.add("fpXbN6BsOq69P2NR2ZUm");
        piantaList.add(new Pianta(
                "beVITqkLHWCerI1XLRxj",
                "pomodoro",
                "Pianta di pomodoro (Solanum lycopersicum)",
                "Solanaceae",
                3,
                4,
                2,
                fasi,
                0.0,
                "pieno sole",
                "ben drenato, ricco di sostanze nutritive",
                18,
                24,
                2.0
        ));
        when(mockRepository.SearchPiante(query)).thenReturn(piantaList);

        // When
        viewModel.searchPianta(query);

        // Then
        verify(mockObserver).onChanged(piantaList);
    }

    // Add more test cases for other methods as needed
}


package com.unimib.eden.ui.home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.unimib.eden.adapter.ColturaAdapter;
import com.unimib.eden.databinding.FragmentHomeBinding;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Una semplice sottoclasse di {@link Fragment} per la schermata Home.
 * Questo frammento visualizza un elenco di colture.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding mBinding;
    private List<Pianta> piante = new ArrayList<Pianta>();
    private List<Coltura> mColture;
    public HomeViewModel homeViewModel;
    private ColturaAdapter mColturaAdapter;
    private FirebaseAuth firebaseAuth;
    /**
     * Costruttore predefinito per HomeFragment.
     */
    public HomeFragment() {
        // Costruttore pubblico vuoto richiesto
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Inizializza l'istanza di Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();

        // Inizializza ViewModel
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        Log.d(TAG, "onCreate: " + homeViewModel.getPiante());

        // Recupera le colture dal ViewModel
        mColture = homeViewModel.getColture();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate il layout per questo frammento
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        // Aggiorna il database se necessario
        //TODO: aggiornare con utente attuale
        //homeViewModel.updateDB(firebaseAuth.getCurrentUser().getUid());
        homeViewModel.updateDB("g.colombo147@campus.unimib.it");

        // Imposta RecyclerView con LinearLayoutManager
        mBinding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inizializza l'adapter con l'elenco delle colture e il listener del clic sull'elemento
        mColturaAdapter = new ColturaAdapter(mColture, new ColturaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Coltura coltura) {
                // Gestisce l'evento di clic sull'elemento
                Log.d(TAG, "OnItemClick " + coltura.toString());

                // Naviga verso ColturaDetailsFragment con la coltura selezionata
                HomeFragmentDirections.ActionNavigationHomeToColturaDetailsFragment action = HomeFragmentDirections.actionNavigationHomeToColturaDetailsFragment(coltura);
                Navigation.findNavController(view).navigate(action);
            }
        }, R.layout.coltura_small_card, getActivity().getApplication());

        // Imposta l'adapter su RecyclerView
        mBinding.homeRecyclerView.setAdapter(mColturaAdapter);
        Log.d(TAG, "mColture: " + mColture.toString());

        return view;
    }
}

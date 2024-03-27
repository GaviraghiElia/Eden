package com.unimib.eden.ui.bancarella;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.R;

import com.unimib.eden.databinding.FragmentHomeBinding;
import com.unimib.eden.databinding.FragmentStandBinding;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.model.Prodotto;
import com.unimib.eden.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class BancarellaFragment extends Fragment {
    private static final String TAG = "BancarellaFragment";
    private FragmentStandBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Prodotto> prodotti = new ArrayList<Prodotto>();
    private BancarellaViewModel bancarellaViewModel;

    public BancarellaFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        bancarellaViewModel = new ViewModelProvider(this).get(BancarellaViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStandBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        bancarellaViewModel.updateDB();
        Log.d(TAG, "onCreate: " + bancarellaViewModel.getProdotti());
        // Inflate the layout for this fragment
        return view;
    }
 }
package com.unimib.eden.ui.home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.databinding.FragmentHomeBinding;


import com.unimib.eden.R;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Pianta> piante = new ArrayList<Pianta>();

    private HomeViewModel homeViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        Log.d(TAG, "onCreate: " + homeViewModel.getPiante());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        homeViewModel.updateDB();
        // Inflate the layout for this fragment
        return view;

    }
}
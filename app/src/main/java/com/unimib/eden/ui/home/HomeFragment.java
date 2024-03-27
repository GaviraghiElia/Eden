package com.unimib.eden.ui.home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Pianta> piante = new ArrayList<Pianta>();
    private NavController navController;
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
        mAuth = FirebaseAuth.getInstance();
        Log.d("mAuth", "mAuth success");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        homeViewModel.updateDB();
        navController = NavHostFragment.findNavController(this);
        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            Log.d("mAuth", "current user != null");
        }else
        {
            Log.d("mAuth", "current user == null");
            navController.navigate(R.id.action_navigation_home_to_loginFragment);
        }
    }
}
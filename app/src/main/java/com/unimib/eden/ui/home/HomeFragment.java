package com.unimib.eden.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.unimib.eden.ui.authentication.AuthenticationActivity;
import com.unimib.eden.ui.searchPianta.SearchPiantaActivity;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        homeViewModel.updateDB();
        navController = NavHostFragment.findNavController(this);
        if(checkSession())
        {
            Log.d("mAuth", "home fragment - user sign in");
            Log.d("mAuth", "home fragment " + requireActivity());

        }else
        {
            Log.d("mAuth", "home fragment - user not auth");
            Log.d("mAuth", "home fragment - this activity is" + requireActivity());
            navController.navigate(R.id.action_navigation_home_to_registerFragment);
        }
        return view;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Listener for the items in the custom menu
        if (item.getItemId() == R.id.app_bar_search) {
            Intent intent = new Intent(getActivity(), SearchPiantaActivity.class);
            intent.putExtra("operationCode", Constants.SEARCH_PIANTA_OPERATION_CODE);
            startActivity(intent);
            return true;
        }
        if(item.getItemId() == R.id.action_logout){
            mAuth.signOut();
            startActivity(new Intent(getActivity().getApplicationContext(), AuthenticationActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean checkSession()
    {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null)
            return true;

        return false;
    }
}
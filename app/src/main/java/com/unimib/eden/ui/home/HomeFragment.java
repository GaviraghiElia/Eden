package com.unimib.eden.ui.home;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

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

import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.adapter.ColturaAdapter;
import com.unimib.eden.databinding.FragmentHomeBinding;


import com.unimib.eden.R;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Pianta;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding binding;
    //FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Pianta> piante = new ArrayList<Pianta>();

    private List<Coltura> mColture;
    private HomeViewModel homeViewModel;

    private ColturaAdapter mColturaAdapter;

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

        mColture = homeViewModel.getColture();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.updateDB();

        RecyclerView mRecyclerViewHome = view.findViewById(R.id.homeRecyclerView);
        mRecyclerViewHome.setLayoutManager(new LinearLayoutManager(getContext()));

        mColturaAdapter = new ColturaAdapter(mColture, new ColturaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Coltura coltura) {
                Log.d(TAG, "OnItemClick " + coltura.toString());

                HomeFragmentDirections.ActionNavigationHomeToColturaDetailsFragment action = HomeFragmentDirections.actionNavigationHomeToColturaDetailsFragment(coltura);
                Navigation.findNavController(view).navigate(action);

            }
        }, R.layout.coltura_small_card, getActivity().getApplication());
        mRecyclerViewHome.setAdapter(mColturaAdapter);
        Log.d(TAG, "mColture: " + mColture.toString());

        return view;
    }
}
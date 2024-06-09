package com.unimib.eden.ui.home;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unimib.eden.R;
import com.unimib.eden.adapter.CropAdapter;
import com.unimib.eden.databinding.FragmentHomeBinding;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.ui.cropDetails.CropDetailsActivity;
import com.unimib.eden.ui.insertCrop.InsertCropActivity;
import com.unimib.eden.ui.searchPlant.SearchPlantActivity;
import com.unimib.eden.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass for the Home screen.
 * This fragment displays a list of crops.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding mBinding;
    private FirebaseAuth mAuth;
    private NavController navController;

    private List<Coltura> mCrops = new ArrayList<>();
    public HomeViewModel homeViewModel;
    private CropAdapter mCropAdapter;

    /**
     * Default constructor for HomeFragment.
     */
    public HomeFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle(requireActivity().getResources().getText(R.string.exitApp))
                        .setMessage(requireActivity().getResources().getText(R.string.exitAppInfo))
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requireActivity().finishAffinity();
                                    }
                                })
                        .setNegativeButton(requireActivity().getResources().getText(R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mAuth = FirebaseAuth.getInstance();

        final Observer<List<Coltura>> allColtureObserver = new Observer<>() {
            @Override
            public void onChanged(List<Coltura> coltura) {
                mCrops = coltura;
                mCropAdapter.update(mCrops);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        homeViewModel.getCrops().observe(this, allColtureObserver);

        homeViewModel.updateDB(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        // Code to handle the click of the button to add a new crop
        Button addButton = view.findViewById(R.id.buttonAddColtura);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.buttonAddColtura) {
                    Intent intent = new Intent(requireContext(), InsertCropActivity.class);
                    startActivity(intent);
                }
            }
        });

        navController = NavHostFragment.findNavController(this);
        if (!checkSession()) {
            navController.navigate(R.id.action_navigation_home_to_registerFragment);
        }

        BottomNavigationView navBar = requireActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);

        // Set up RecyclerView with LinearLayoutManager
        mBinding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the adapter with the list of crops and the item click listener
        mCropAdapter = new CropAdapter(mCrops, new CropAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Coltura coltura) {
                // Navigate to CropDetailsFragment with the selected crop
                Intent intent = new Intent(getActivity(), CropDetailsActivity.class);
                intent.putExtra("coltura", coltura);
                startActivity(intent);
            }
        }, R.layout.crop_item, getActivity().getApplication());

        // Set the adapter to RecyclerView
        mBinding.homeRecyclerView.setAdapter(mCropAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBinding.homeRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY > oldScrollY + 5 && mBinding.buttonAddColtura.isShown())
                        mBinding.buttonAddColtura.shrink();
                    else if (scrollY < oldScrollY - 20)
                        mBinding.buttonAddColtura.extend();
                    else if (scrollY == 0)
                        mBinding.buttonAddColtura.extend();
                }
            });
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
            Intent intent = new Intent(getActivity(), SearchPlantActivity.class);
            intent.putExtra("operationCode", Constants.SEARCH_PLANT_OPERATION_CODE);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkSession() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }
}

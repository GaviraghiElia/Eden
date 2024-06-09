package com.unimib.eden.ui.watering;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

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
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.unimib.eden.R;
import com.unimib.eden.adapter.CropAdapter;
import com.unimib.eden.adapter.ForecastDayAdapter;
import com.unimib.eden.databinding.FragmentIrrigazioniBinding;
import com.unimib.eden.model.Crop;
import com.unimib.eden.model.weather.ForecastDay;
import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A Fragment for the Watering screen.
 * This Fragment displays a list of crops to be watered on the current day.
 * It also displays the weather forecast for the current day and the next two days.
 */
public class WateringFragment extends Fragment {

    private static final String TAG = "WateringFragment";
    private FragmentIrrigazioniBinding mBinding;
    private FirebaseAuth mAuth;
    private NavController navController;

    private List<Crop> mCrops = new ArrayList<>();
    private WeatherForecast mWeatherForecast;
    private List<ForecastDay> mWeatherForecastList = new ArrayList<>();
    private List<Crop> cropsToUpdate = new ArrayList<>();
    public WateringViewModel wateringViewModel;
    private CropAdapter mCropAdapter;
    private ForecastDayAdapter mForecastDayAdapter;


    /**
     * Default constructor for WateringFragment.
     */
    public WateringFragment() {
        // Public empty constructor required
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
                        .setPositiveButton(R.string.si,
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        requireActivity().finish();
                                    }
                                })
                        .setNegativeButton(requireActivity().getResources().getText(R.string.cancel),
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                }).show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        wateringViewModel = new ViewModelProvider(this).get(WateringViewModel.class);
        mAuth = FirebaseAuth.getInstance();

        final Observer<List<Crop>> allCropsObserver = new Observer<List<Crop>>() {
            @Override
            public void onChanged(List<Crop> crops) {
                mCrops = crops;
                mCropAdapter.update(mCrops);
            }
        };

        final Observer<WeatherForecast> allWeatherForecastObserver = new Observer<WeatherForecast>() {
            @Override
            public void onChanged(WeatherForecast weatherForecast) {
                if (weatherForecast != null) {
                    mWeatherForecast = weatherForecast;
                    mForecastDayAdapter.update(mWeatherForecast.getForecast().getForecastday());
                }
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        wateringViewModel.getCropsToWater().observe(this, allCropsObserver);
        wateringViewModel.getForecast(Constants.LOCATION, 3, "no", "no").observe(this, allWeatherForecastObserver);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mBinding = FragmentIrrigazioniBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        navController = NavHostFragment.findNavController(this);

        BottomNavigationView navBar = requireActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);


        // Set up RecyclerView with LinearLayoutManager
        mBinding.irrigazioniRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mForecastDayAdapter = new ForecastDayAdapter(mWeatherForecastList, R.layout.weather_forecast_item);

        // Initialize the adapter with the list of crops and the item click listener
        mCropAdapter = new CropAdapter(mCrops, new CropAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop crop) {
                if (cropsToUpdate.contains(crop)) {
                    cropsToUpdate.remove(crop);
                } else {
                    cropsToUpdate.add(crop);
                }

                if(cropsToUpdate.isEmpty()) {
                    mBinding.buttonUpdateIrrigazioni.setVisibility(View.GONE);
                }
                else {
                    mBinding.buttonUpdateIrrigazioni.setVisibility(View.VISIBLE);
                }
            }
        }, R.layout.irrigazioni_item, getActivity().getApplication());

        // Set the adapter to RecyclerView
        mBinding.irrigazioniRecyclerView.setAdapter(mCropAdapter);

        mBinding.buttonUpdateIrrigazioni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(getContext())
                        .setTitle(getResources().getText(R.string.confermaInnaffiamento))
                        .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!cropsToUpdate.isEmpty()) {
                                    for (Crop coltura: cropsToUpdate) {
                                        wateringViewModel.updateWateringDateCrop(coltura);

                                    }
                                    mCrops.removeAll(cropsToUpdate);
                                    cropsToUpdate.clear();
                                    mCropAdapter.update(mCrops);
                                }
                            }
                        })
                        .setNegativeButton(getResources().getText(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });

        RecyclerView recyclerViewWeather = mBinding.previsioniRecyclerView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewWeather.setLayoutManager(layoutManager);

        recyclerViewWeather.setVerticalScrollBarEnabled(true);
        recyclerViewWeather.setHorizontalScrollBarEnabled(true);
        recyclerViewWeather.setScrollbarFadingEnabled(false);

        // Attach PagerSnapHelper to RecyclerView
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerViewWeather);


        // Create LinearSmoothScroller
        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override
            protected int getHorizontalSnapPreference() {
                return SNAP_TO_START; // Snap to the start of the view
            }
        };

        // Set your adapter to the RecyclerView
        recyclerViewWeather.setAdapter(mForecastDayAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_menu, menu);
        menu.getItem(0).setVisible(false);
    }

}

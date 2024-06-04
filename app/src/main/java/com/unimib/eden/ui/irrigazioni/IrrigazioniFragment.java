package com.unimib.eden.ui.irrigazioni;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
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

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unimib.eden.R;
import com.unimib.eden.adapter.ColturaAdapter;
import com.unimib.eden.adapter.ForecastDayAdapter;
import com.unimib.eden.databinding.FragmentIrrigazioniBinding;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.weather.ForecastDay;
import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.ui.authentication.AuthenticationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Un Fragment per la schermata Irrigazioni.
 * Questo Fragment visualizza un elenco delle colture da innaffiare nel giorno corrente.
 * Visualizza inoltre le previsioni meteo del giorno corrente e i prossimi due giorni
 *
 * @author Alice Hoa Galli
 */
public class IrrigazioniFragment extends Fragment {

    private static final String TAG = "IrrigazioniFragment";
    private FragmentIrrigazioniBinding mBinding;
    private FirebaseAuth mAuth;
    private NavController navController;

    private List<Coltura> mColture = new ArrayList<>();
    private WeatherForecast mWeatherForecast;
    private List<ForecastDay> mWeatherForecastList = new ArrayList<>();
    private List<Coltura> coltureDaAggiornare = new ArrayList<>();
    public IrrigazioniViewModel irrigazioniViewModel;
    private ColturaAdapter mColturaAdapter;
    private ForecastDayAdapter mForecastDayAdapter;


    /**
     * Costruttore predefinito per IrrigazioniFragment.
     */
    public IrrigazioniFragment() {
        // Costruttore pubblico vuoto richiesto
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
                        .setPositiveButton("Si",
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

        irrigazioniViewModel = new ViewModelProvider(this).get(IrrigazioniViewModel.class);
        mAuth = FirebaseAuth.getInstance();

        final Observer<List<Coltura>> allColtureObserver = new Observer<List<Coltura>>() {
            @Override
            public void onChanged(List<Coltura> colture) {

                mColture = colture;
                mColturaAdapter.update(mColture);
            }
        };

        final Observer<WeatherForecast> allWeatherForecastObserver = new Observer<WeatherForecast>() {
            @Override
            public void onChanged(WeatherForecast weatherForecast) {
                if (weatherForecast != null) {
                    Log.d(TAG, "weatherForecast: " + weatherForecast.toString());
                    mWeatherForecast = weatherForecast;
                    mForecastDayAdapter.update(mWeatherForecast.getForecast().getForecastday());
                } else {
                    Log.d(TAG, "null");
                }
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        irrigazioniViewModel.getColtureDaIrrigare().observe(this, allColtureObserver);
        irrigazioniViewModel.getForecast("Agrate Brianza", 3, "no", "no").observe(this, allWeatherForecastObserver);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate il layout per questo frammento
        mBinding = FragmentIrrigazioniBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        navController = NavHostFragment.findNavController(this);

        BottomNavigationView navBar = requireActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);


        // Imposta RecyclerView con LinearLayoutManager
        mBinding.irrigazioniRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mForecastDayAdapter = new ForecastDayAdapter(mWeatherForecastList, R.layout.weather_forecast_item);

        // Inizializza l'adapter con l'elenco delle colture e il listener del clic sull'elemento
        mColturaAdapter = new ColturaAdapter(mColture, new ColturaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Coltura coltura) {
                /*
                materialDateBuilder = MaterialDatePicker.Builder.datePicker();
                materialDateBuilder.setTitleText(R.string.date_picker_title);
                materialDateBuilder.build();
                materialDatePicker = materialDateBuilder.build();
                materialDatePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
                materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                    Log.d(TAG, "onItemClick: DATE: " + materialDatePicker.getHeaderText());

                    Log.d(TAG, "onItemClick: DATE3: " + new Date(materialDatePicker.getHeaderText()));

                    irrigazioniViewModel.updateDataInnaffiamentoColtura(coltura, new Date(materialDatePicker.getHeaderText()));

                });

                 */

                if (coltureDaAggiornare.contains(coltura)) {
                    coltureDaAggiornare.remove(coltura);
                } else {
                    coltureDaAggiornare.add(coltura);
                }

                if(coltureDaAggiornare.isEmpty()) {
                    mBinding.buttonUpdateIrrigazioni.setVisibility(View.GONE);
                    }
                else {
                    mBinding.buttonUpdateIrrigazioni.setVisibility(View.VISIBLE);
                }
            }
        }, R.layout.irrigazioni_item, getActivity().getApplication());

        // Imposta l'adapter su RecyclerView
        mBinding.irrigazioniRecyclerView.setAdapter(mColturaAdapter);

        mBinding.buttonUpdateIrrigazioni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (!coltureDaAggiornare.isEmpty()) {
                    for (Coltura coltura: coltureDaAggiornare) {
                        irrigazioniViewModel.updateDataInnaffiamentoColtura(coltura);

                    }
                    mColture.removeAll(coltureDaAggiornare);
                    coltureDaAggiornare.clear();
                    mColturaAdapter.update(mColture);
                }*/

                new MaterialAlertDialogBuilder(getContext())
                        .setTitle(getResources().getText(R.string.confermaInnaffiamento))
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!coltureDaAggiornare.isEmpty()) {
                                    for (Coltura coltura: coltureDaAggiornare) {
                                        irrigazioniViewModel.updateDataInnaffiamentoColtura(coltura);

                                    }
                                    mColture.removeAll(coltureDaAggiornare);
                                    coltureDaAggiornare.clear();
                                    mColturaAdapter.update(mColture);
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
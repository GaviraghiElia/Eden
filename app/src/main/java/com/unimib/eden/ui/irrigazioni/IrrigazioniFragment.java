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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.R;
import com.unimib.eden.adapter.ColturaAdapter;
import com.unimib.eden.adapter.WeatherForecastAdapter;
import com.unimib.eden.databinding.FragmentIrrigazioniBinding;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.model.weather.ForecastDay;
import com.unimib.eden.model.weather.WeatherForecast;
import java.util.ArrayList;
import java.util.List;

/**
 * Un Fragment per la schermata Irrigazioni.
 * Questo Fragment visualizza un elenco delle colture da innaffiare nel giorno corrente.
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
    private WeatherForecastAdapter mWeatherForecastAdapter;


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
                    mWeatherForecastAdapter.update(mWeatherForecast.getForecast().getForecastday());
                } else {
                    Log.d(TAG, "null");
                }
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        irrigazioniViewModel.getColtureDaIrrigare().observe(this, allColtureObserver);
        //TODO: chiedere posizione
        irrigazioniViewModel.getForecast("Antananarivo", 3, "no", "no").observe(this, allWeatherForecastObserver);

        // Recupera le colture dal ViewModel
        //mColture = homeViewModel.getColture();
        //irrigazioniViewModel.updateDB("g.colombo147@campus.unimib.it");
        Log.d(TAG, "onCreate: IRRIGAZIONI: " + mColture.toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate il layout per questo frammento
        mBinding = FragmentIrrigazioniBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

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

        BottomNavigationView navBar = requireActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);

        // Imposta RecyclerView con LinearLayoutManager
        mBinding.irrigazioniRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mWeatherForecastAdapter = new WeatherForecastAdapter(mWeatherForecastList, R.layout.weather_forecast_item, getActivity().getApplication());

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
            }
        }, R.layout.irrigazioni_item, getActivity().getApplication());

        //copiato da sotto, per le previsioni
        mBinding.previsioniRecyclerView.setAdapter(mWeatherForecastAdapter);

        // Imposta l'adapter su RecyclerView
        mBinding.irrigazioniRecyclerView.setAdapter(mColturaAdapter);

        Log.d(TAG, "onCreateView: mFasi " + irrigazioniViewModel.getFasi().toString());
        Log.d(TAG, "onCreateView: mPiante " + irrigazioniViewModel.getPiante().toString());
        Log.d(TAG, "onCreateView: mColture " + irrigazioniViewModel.getColture().getValue());

        mBinding.buttonUpdateIrrigazioni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!coltureDaAggiornare.isEmpty()) {
                    for (Coltura coltura: coltureDaAggiornare) {
                        irrigazioniViewModel.updateDataInnaffiamentoColtura(coltura);

                    }
                    mColture.removeAll(coltureDaAggiornare);
                    coltureDaAggiornare.clear();
                    mColturaAdapter.update(mColture);
                }
            }
        });

        //homeViewModel.updateDB("g.colombo147@campus.unimib.it");

        return view;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_menu, menu);
        menu.getItem(0).setVisible(false);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Listener for the items in the custom menu
        /*
        if (item.getItemId() == R.id.app_bar_search) {
            Intent intent = new Intent(getActivity(), SearchPiantaActivity.class);
            intent.putExtra("operationCode", Constants.SEARCH_PIANTA_OPERATION_CODE);
            startActivity(intent);
            return true;
        }

         */


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
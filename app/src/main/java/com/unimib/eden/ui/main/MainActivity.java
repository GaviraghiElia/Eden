package com.unimib.eden.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

import android.os.Build;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unimib.eden.R;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.model.weather.WeatherHistory;
import com.unimib.eden.model.weather.WeatherSearchLocation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.unimib.eden.ui.authentication.AuthenticationActivity;
import com.unimib.eden.ui.home.HomeViewModel;

/**
 * MainActivity è il punto di ingresso principale dell'applicazione. Configura i componenti di navigazione,
 * inizializza l'autenticazione Firebase e osserva i cambiamenti nei dati meteorologici e nelle colture.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;

    NavHostFragment navHostFragment;
    NavController navController;

    public WeatherViewModel viewModel;

    private List<Coltura> mColture = new ArrayList<>();

    boolean firstOpening = true;

    /**
     * Chiamato quando l'attività viene creata per la prima volta. Questo è il punto in cui di fa tutta la configurazione
     * statica normale: creare viste, collegare dati alle liste, ecc. Questo metodo ti fornisce anche un Bundle contenente
     * lo stato precedentemente congelato dell'attività, se presente.
     *
     * @param savedInstanceState Se l'attività viene ricreata dopo essere stata precedentemente chiusa, questo Bundle contiene i dati
     *                           che ha fornito più recentemente in {@link #onSaveInstanceState}.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_stand).build();

        // Logica per gestire il comportamento di BottomNavigationView, navHostFragmentActivityMain e Toolbar
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(androidx.navigation.fragment.R.id.nav_host_fragment_container);
        navController = navHostFragment.getNavController();

        // Per la Toolbar
        setSupportActionBar(findViewById(R.id.toolbarMain));
        //((Toolbar) findViewById(R.id.toolbarMain)).setTitleTextAppearance(this, R.style.TextAppearance_App_CollapsingToolbar_Collapsed);

        navController.getCurrentDestination().setLabel(getString(R.string.app_name));

        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Per la BottomNavigationView
        NavigationUI.setupWithNavController((BottomNavigationView) findViewById(R.id.nav_view), navController);

        // Inizializza Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setEnabled(true);

        // Crea un'istanza del ViewModel
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        LifecycleOwner lifecycleOwner = this;
        final Observer<List<Coltura>> allColtureObserver = new Observer<List<Coltura>>() {
            @Override
            public void onChanged(List<Coltura> coltura) {

                Log.d(TAG, "onChanged: ");
                mColture = coltura;
                Log.d(TAG, "firstOpening" + firstOpening);
                viewModel.getHistory("Agrate Brianza", new Date()).observe(lifecycleOwner, new Observer<WeatherHistory>() {
                    @Override
                    public void onChanged(WeatherHistory weatherHistory) {
                        if (weatherHistory != null) {
                            Log.d("WeatherAppLog", "weatherHistory: " + weatherHistory.toString());
                            viewModel.updateInnaffiamenti(weatherHistory.getForecast().getForecastday().get(0).getDay(), coltura);
                            Log.d("WeatherAppLog", "updateInnaffiamenti: " + weatherHistory.getForecast().getForecastday().get(0).getDay().getTotalprecip_mm());
                        } else {
                            Log.d("WeatherAppLog", "weatherHistory - null");
                        }
                    }
                });
            }
        };

        viewModel.getColture().observe(this, allColtureObserver);

        /*
        viewModel.getForecast("Agrate Brianza", 2, "no", "no").observe(this, new Observer<WeatherForecast>() {
            @Override
            public void onChanged(WeatherForecast weatherForecast) {
                if (weatherForecast != null) {
                    Log.d("WeatherAppLog", "weatherForecast: " + weatherForecast.toString());
                } else {
                    Log.d("WeatherAppLog", "null");
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            viewModel.getHistory("Agrate Brianza", LocalDate.now()).observe(this, new Observer<WeatherHistory>() {
                @Override
                public void onChanged(WeatherHistory weatherHistory) {
                    if(weatherHistory != null){
                        Log.d("WeatherAppLog", "weatherHistory: " + weatherHistory.toString());
                    }else{
                        Log.d("WeatherAppLog", "weatherHistory - null");
                    }
                }
            });
        }

        viewModel.getSearchLocation("Carugo").observe(this, new Observer<List<WeatherSearchLocation>>() {
            @Override
            public void onChanged(List<WeatherSearchLocation> weatherSearchLocation) {
                if(weatherSearchLocation != null){
                    for (WeatherSearchLocation location : weatherSearchLocation) {
                        Log.d("WeatherAppLogSearch", "weatherSearch: " + location.toString());
                    }
                }else{
                    Log.d("WeatherAppLogSearch", "weatherSearch - null");
                }
            }
        });
         */
    }

    /**
     * Questo metodo viene chiamato ogni volta che viene selezionato un elemento nel menu delle opzioni.
     * L'implementazione predefinita restituisce semplicemente false per consentire l'elaborazione normale
     * (chiamando il Runnable dell'elemento o inviando un messaggio al suo Handler come appropriato).
     *
     * @param item L'elemento del menu che è stato selezionato.
     * @return boolean Restituisce false per consentire l'elaborazione normale del menu, true per gestirlo qui.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_logout){
            new MaterialAlertDialogBuilder(this)
                    .setTitle(getResources().getText(R.string.wantLogout))
                    .setMessage(getResources().getText(R.string.logoutInfo))
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAuth.signOut();
                            startActivity(new Intent(getApplicationContext(), AuthenticationActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton(getResources().getText(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
}

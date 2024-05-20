package com.unimib.eden.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unimib.eden.R;
import com.unimib.eden.model.weather.WeatherForecast;
import com.unimib.eden.model.weather.WeatherHistory;
import com.unimib.eden.model.weather.WeatherSearchLocation;

import java.time.LocalDate;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
    private WeatherViewModel viewModel;

    NavHostFragment navHostFragment;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_profile, R.id.navigation_stand).build();

        // Logic to manage the behavior of the BottomNavigationView, navHostFragmentActivityMain and Toolbar
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(androidx.navigation.fragment.R.id.nav_host_fragment_container);
        navController = navHostFragment.getNavController();

        // For the Toolbar
        setSupportActionBar(findViewById(R.id.toolbarMain));
        //((Toolbar) findViewById(R.id.toolbarMain)).setTitleTextAppearance(this, R.style.TextAppearance_App_CollapsingToolbar_Collapsed);

        navController.getCurrentDestination().setLabel(getString(R.string.app_name));

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // For the BottomNavigationView
        NavigationUI.setupWithNavController((BottomNavigationView) findViewById(R.id.nav_view), navController);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setEnabled(true);


        // Crea un'istanza del ViewModel
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
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
}
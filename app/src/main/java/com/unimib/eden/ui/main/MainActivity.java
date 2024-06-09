package com.unimib.eden.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unimib.eden.R;
import com.unimib.eden.model.Crop;
import com.unimib.eden.model.weather.WeatherHistory;
import com.unimib.eden.ui.authentication.AuthenticationActivity;
import com.unimib.eden.utils.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * MainActivity is the main entry point of the application. It configures navigation components,
 * initializes Firebase authentication, and observes changes in weather data and crops.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;

    private NavHostFragment navHostFragment;
    private NavController navController;

    private WeatherViewModel viewModel;

    private List<Crop> mCrops = new ArrayList<>();


    /**
     * Called when the activity is first created. This is where all the static setup is done: create views,
     * bind data to lists, etc. This method also provides a Bundle containing the activity's previously saved state, if any.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in {@link #onSaveInstanceState}.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_stand).build();

        // Logic for handling BottomNavigationView, navHostFragmentActivityMain, and Toolbar
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        // For the Toolbar
        setSupportActionBar(findViewById(R.id.toolbarMain));
        if (navController != null) {
            navController.getCurrentDestination().setLabel(getString(R.string.app_name));
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        }

        // For BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        if (navController != null) {
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Create an instance of ViewModel
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        final Observer<List<Crop>> allColtureObserver = new Observer<>() {
            @Override
            public void onChanged(List<Crop> crop) {
                mCrops = crop;
                viewModel.getHistory(Constants.LOCATION, new Date()).observe(MainActivity.this, new Observer<>() {
                    @Override
                    public void onChanged(WeatherHistory weatherHistory) {
                        if (weatherHistory != null) {
                            viewModel.updateWateringDays(weatherHistory.getForecast().getForecastday().get(0).getDay(), crop);
                        }
                    }
                });
            }
        };

        viewModel.getCrops().observe(this, allColtureObserver);
    }

    /**
     * This method is called whenever an item in the options menu is selected.
     * The default implementation simply returns false to allow normal menu processing
     * to proceed (calling the item's Runnable or sending a message to its Handler as appropriate).
     *
     * @param item The menu item that was selected.
     * @return boolean Returns false to allow normal menu processing to proceed, true to handle it here.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            new MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.wantLogout))
                    .setMessage(getString(R.string.logoutInfo))
                    .setPositiveButton(getString(R.string.si), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mAuth.signOut();
                            startActivity(new Intent(getApplicationContext(), AuthenticationActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                        }
                    })
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

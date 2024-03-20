package com.unimib.eden.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.unimib.eden.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_profile).build();

        // Logic to manage the behavior of the BottomNavigationView, navHostFragmentActivityMain and Toolbar
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(androidx.navigation.fragment.R.id.nav_host_fragment_container);
        NavController navController = navHostFragment.getNavController();

        // For the Toolbar
        setSupportActionBar(findViewById(R.id.toolbar));
        ((Toolbar) findViewById(R.id.toolbar)).setTitleTextAppearance(this, R.style.TextAppearance_App_CollapsingToolbar_Collapsed);

        navController.getCurrentDestination().setLabel(getString(R.string.app_name));

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // For the BottomNavigationView
        NavigationUI.setupWithNavController((BottomNavigationView) findViewById(R.id.nav_view), navController);
    }
}
package com.unimib.eden.ui.main;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.auth.FirebaseUser;
import com.unimib.eden.R;
import com.unimib.eden.ui.authentication.AuthenticationActivity;
import com.unimib.eden.ui.searchPianta.SearchPiantaActivity;
import com.unimib.eden.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;
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
        setSupportActionBar(findViewById(R.id.toolbar));
        ((Toolbar) findViewById(R.id.toolbar)).setTitleTextAppearance(this, R.style.TextAppearance_App_CollapsingToolbar_Collapsed);

        navController.getCurrentDestination().setLabel(getString(R.string.app_name));

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // For the BottomNavigationView
        NavigationUI.setupWithNavController((BottomNavigationView) findViewById(R.id.nav_view), navController);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setEnabled(true);

    }


}
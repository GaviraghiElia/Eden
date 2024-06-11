package com.unimib.eden.ui.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unimib.eden.databinding.ActivityAuthenticationBinding;
import com.unimib.eden.ui.main.MainActivity;

/**
 * AuthenticationActivity is responsible for handling user authentication-related operations.
 * It manages the authentication flow, allowing users to log in or sign up.
 */
public class AuthenticationActivity extends AppCompatActivity {
    private NavHostFragment navHostFragment;
    private NavController navController;
    private ActivityAuthenticationBinding mBinding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        // Check if a user session exists. If yes, redirect to the MainActivity.
        if (checkSession()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        mBinding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        // Initialize navigation components
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(mBinding.fragmentAuthContainerView.getId());
        navController = navHostFragment.getNavController();
    }

    /**
     * Checks if a user session exists.
     *
     * @return True if a user session exists, false otherwise.
     */
    private boolean checkSession() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;
    }
}

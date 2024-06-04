package com.unimib.eden.ui.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unimib.eden.R;
import com.unimib.eden.databinding.ActivityAuthenticationBinding;
import com.unimib.eden.ui.main.MainActivity;

public class AuthenticationActivity extends AppCompatActivity
{
    private NavHostFragment navHostFragment;
    private NavController navController;
    private ActivityAuthenticationBinding mBinding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        if (checkSession())
        {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        mBinding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(mBinding.fragmentAuthContainerView.getId());
        navController = navHostFragment.getNavController();

    }

    private boolean checkSession()
    {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null)
            return true;

        return false;
    }
}
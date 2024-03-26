package com.unimib.eden.ui.authentication;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class AuthenticationActivity extends AppCompatActivity {
    private NavHostFragment navHostFragment;
    private NavController navController;
    private ActivityAuthenticationBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

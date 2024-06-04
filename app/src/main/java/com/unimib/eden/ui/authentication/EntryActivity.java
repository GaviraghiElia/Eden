package com.unimib.eden.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unimib.eden.ui.main.MainActivity;

public class EntryActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        Log.d("mauth", "siamo dentro all'entryActivity");

        if(currentUser != null) {
            Log.d("mauth", "andiamo dentro alla main activity");
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else
        {
            Log.d("mauth", "andiamo dentro alla auth activity");
            startActivity(new Intent(this, AuthenticationActivity.class));
            finish();
        }
        finish();
    }
}

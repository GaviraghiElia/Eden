package com.unimib.eden.ui.piantaDetails;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.unimib.eden.databinding.ActivityPiantaDetailsBinding;
import com.unimib.eden.model.Pianta;

public class PiantaDetailsActivity extends AppCompatActivity {

    private static final String TAG = "PiantaDetailsActivity";
    private ActivityPiantaDetailsBinding binding;
    private PiantaDetailsViewModel piantaDetailsViewModel;
    private LiveData<Pianta> game = new LiveData<Pianta>() {};
    String idNotification = null;
    Pianta pianta = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPiantaDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        piantaDetailsViewModel = new ViewModelProvider(this).get(PiantaDetailsViewModel.class);

        Intent intent = getIntent();
        pianta = (Pianta) intent.getSerializableExtra("pianta");

        binding.textView.setText(pianta.getNome());

    }
}

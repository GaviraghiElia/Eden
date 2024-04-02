package com.unimib.eden.ui.searchPianta;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.unimib.eden.adapter.PiantaAdapter;
import com.unimib.eden.databinding.ActivitySearchPiantaBinding;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SearchPiantaActivity extends AppCompatActivity {
    private ActivitySearchPiantaBinding binding;
    private SearchPiantaViewModel searchPiantaViewModel;
    private Handler mHandler = new Handler();
    private PiantaAdapter piantaAdapter;
    private int operationCode;
    private LiveData<List<Pianta>> piantaList = new LiveData<List<Pianta>>() {
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySearchPiantaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        searchPiantaViewModel = new ViewModelProvider(this).get(SearchPiantaViewModel.class);

        Intent intent = getIntent();
        operationCode = (int) intent.getSerializableExtra("operationCode");

        binding.progressBarSearchPianta.setVisibility(View.VISIBLE);
        piantaList = searchPiantaViewModel.getPiantaList();

        binding.searchPiantaRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        piantaAdapter = new PiantaAdapter(new ArrayList<>());
        binding.searchPiantaRecyclerView.setAdapter(piantaAdapter);

        searchPiantaViewModel.getPiantaList().observe(this, new Observer<List<Pianta>>() {
            @Override
            public void onChanged(List<Pianta> piante) {
                piantaAdapter.update(piante, Constants.SEARCH_PIANTA_OPERATION_CODE);
                binding.progressBarSearchPianta.setVisibility(View.GONE);
            }
        });

        binding.textInputSearchPianta.requestFocus();
        binding.textInputSearchPianta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.progressBarSearchPianta.setVisibility(View.VISIBLE);
                        searchPiantaViewModel.searchPianta(s.toString());
                    }
                }, Constants.API_SEARCH_DELAY);
            }
        });
    }
}
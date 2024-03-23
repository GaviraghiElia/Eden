package com.unimib.eden.ui.bancarella;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unimib.eden.R;


public class BancarellaFragment extends Fragment {

    private BancarellaViewModel viewModel;
    private static final String TAG = "BancarellaFragment";
    public BancarellaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BancarellaViewModel.class);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stand, container, false);
    }
}
package com.unimib.eden.ui.bancarella;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.R;

import com.unimib.eden.databinding.FragmentStandBinding;
import com.unimib.eden.model.Prodotto;
import com.unimib.eden.ui.inserimentoProdotto.InserimentoProdottoActivity;

import java.util.ArrayList;
import java.util.List;

public class BancarellaFragment extends Fragment {
    private static final String TAG = "BancarellaFragment";
    private FragmentStandBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Prodotto> prodotti = new ArrayList<Prodotto>();
    private BancarellaViewModel bancarellaViewModel;

    public BancarellaFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        bancarellaViewModel = new ViewModelProvider(this).get(BancarellaViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStandBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //codice per spostarsi in "InserimentoProdottoActivity"
        Button addButton = view.findViewById(R.id.buttonAddProduct);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "sei dentro onClick");
                if (v.getId() == R.id.buttonAddProduct) {
                    Intent intent = new Intent(requireContext(), InserimentoProdottoActivity.class);
                    startActivity(intent);
                }
            }
        });

        bancarellaViewModel.updateDB();
        Log.d(TAG, "onCreate: " + bancarellaViewModel.getProdotti());
        // Inflate the layout for this fragment
        return view;
    }
}
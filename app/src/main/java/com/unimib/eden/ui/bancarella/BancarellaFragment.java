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

/**
 * Un semplice {@link Fragment} per la visualizzazione della bancarella, con possibilità di aggiunta di nuovi prodotti.
 */
public class BancarellaFragment extends Fragment {
    private static final String TAG = "BancarellaFragment";
    private FragmentStandBinding binding;
    private List<Prodotto> prodotti = new ArrayList<Prodotto>();
    private BancarellaViewModel bancarellaViewModel;

    /**
     * Costruttore predefinito per BancarellaFragment.
     */
    public BancarellaFragment() {
        // Costruttore pubblico vuoto richiesto
    }

    /**
     * Metodo richiamato al momento della creazione del fragment.
     *
     * @param savedInstanceState lo stato precedente dell'istanza, se presente.
     */
    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        bancarellaViewModel = new ViewModelProvider(this).get(BancarellaViewModel.class);
    }

    /**
     * Metodo richiamato per creare e visualizzare l'interfaccia utente del fragment.
     *
     * @param inflater           il layout inflater che può essere utilizzato per inflare qualsiasi layout XML.
     * @param container          il parent view che il fragment UI dovrebbe essere agganciato, se presente.
     * @param savedInstanceState lo stato precedente dell'istanza, se presente.
     * @return la vista radice del layout del fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStandBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Codice per gestire il click del pulsante per aggiungere un nuovo prodotto
        Button addButton = view.findViewById(R.id.buttonAddProduct);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.buttonAddProduct) {
                    Intent intent = new Intent(requireContext(), InserimentoProdottoActivity.class);
                    startActivity(intent);
                }
            }
        });


        /* TODO
        da implementare con la scrollview
         */
        /*
        binding.scrollViewInfo.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY)
            {
                if (scrollY > oldScrollY + 5 && binding.addToListExtButton.isShown())
                    binding.addToListExtButton.shrink();
                else
                if (scrollY < oldScrollY - 20)
                    binding.addToListExtButton.extend();
                else
                if (scrollY == 0)
                    binding.addToListExtButton.extend();
            }
        });
        */

        // Aggiornamento del database locale e recupero dei prodotti
        bancarellaViewModel.updateDB();
        Log.d(TAG, "onCreate: " + bancarellaViewModel.getProdotti());
        // Ritorna la vista del fragment
        return view;
    }
}

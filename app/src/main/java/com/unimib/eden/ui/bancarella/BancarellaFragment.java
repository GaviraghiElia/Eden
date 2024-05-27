package com.unimib.eden.ui.bancarella;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unimib.eden.R;

import com.unimib.eden.adapter.ProdottoAdapter;
import com.unimib.eden.databinding.FragmentStandBinding;
import com.unimib.eden.model.Prodotto;
import com.unimib.eden.ui.authentication.AuthenticationActivity;
import com.unimib.eden.ui.inserimentoProdotto.InserimentoProdottoActivity;
import com.unimib.eden.ui.prodottoDetails.ProdottoDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Un semplice {@link Fragment} per la visualizzazione della bancarella, con possibilità di aggiunta di nuovi prodotti.
 */
public class BancarellaFragment extends Fragment {
    private static final String TAG = "BancarellaFragment";
    private FragmentStandBinding binding;
    private List<Prodotto> mProdotti = new ArrayList<Prodotto>();
    private BancarellaViewModel bancarellaViewModel;
    private ProdottoAdapter mProdottoAdapter;
    private FirebaseAuth mAuth;

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
        mAuth = FirebaseAuth.getInstance();

        final Observer<List<Prodotto>> allProdottiObserver = new Observer<List<Prodotto>>() {
            @Override
            public void onChanged(List<Prodotto> prodotti) {

                Log.d(TAG, "onChanged: ");
                mProdotti = prodotti;

                mProdottoAdapter.update(mProdotti);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        bancarellaViewModel.getProdotti().observe(this, allProdottiObserver);


        // Recupera le colture dal ViewModel
        //mColture = homeViewModel.getColture();
        //TODO: CAMBIARE CURRENT USER
        bancarellaViewModel.updateDB("g.colombo147@campus.unimib.it");
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

        // Imposta RecyclerView con LinearLayoutManager
        binding.bancarellaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inizializza l'adapter con l'elenco delle colture e il listener del clic sull'elemento
        mProdottoAdapter = new ProdottoAdapter(mProdotti, new ProdottoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Prodotto prodotto) {
                // Gestisce l'evento di clic sull'elemento
                Log.d(TAG, "OnItemClick " + prodotto.toString());

                // Naviga verso ProdottoDetailsFragment con la prodotto selezionata
                Intent intent = new Intent(getActivity(), ProdottoDetailsActivity.class);
                intent.putExtra("prodotto", prodotto);
                startActivity(intent);
            }
        }, R.layout.prodotto_item, getActivity().getApplication());

        // Imposta l'adapter su RecyclerView
        binding.bancarellaRecyclerView.setAdapter(mProdottoAdapter);


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

        Log.d(TAG, "onCreateView: " + bancarellaViewModel.getProdotti());
        // Ritorna la vista del fragment
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Listener for the items in the custom menu
        if(item.getItemId() == R.id.action_logout){
            mAuth.signOut();
            startActivity(new Intent(getActivity().getApplicationContext(), AuthenticationActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean checkSession()
    {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null)
            return true;

        return false;
    }
}

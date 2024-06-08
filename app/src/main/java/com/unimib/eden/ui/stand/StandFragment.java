package com.unimib.eden.ui.stand;

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
import com.unimib.eden.ui.insertProduct.InsertProductActivity;
import com.unimib.eden.ui.productDetails.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} for displaying the stand with the ability to add new products.
 */
public class StandFragment extends Fragment {
    private static final String TAG = "StandFragment";
    private FragmentStandBinding binding;
    private List<Prodotto> mProducts = new ArrayList<>();
    private StandViewModel standViewModel;
    private ProdottoAdapter mProductAdapter;
    private FirebaseAuth mAuth;

    /**
     * Default constructor for StandFragment.
     */
    public StandFragment() {
        // Required empty public constructor
    }

    /**
     * Method called upon fragment creation.
     *
     * @param savedInstanceState the previously saved instance state, if any.
     */
    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        standViewModel = new ViewModelProvider(this).get(StandViewModel.class);
        mAuth = FirebaseAuth.getInstance();

        final Observer<List<Prodotto>> allProdottiObserver = new Observer<List<Prodotto>>() {
            @Override
            public void onChanged(List<Prodotto> prodotti) {
                mProducts = prodotti;
                mProductAdapter.update(mProducts);
            }
        };

        // Observe the LiveData, passing in this fragment as the LifecycleOwner and the observer.
        standViewModel.getProducts().observe(this, allProdottiObserver);

        standViewModel.updateDB(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
    }

    /**
     * Method called to create and display the fragment's user interface.
     *
     * @param inflater           the LayoutInflater used to inflate any XML layout.
     * @param container          the parent view that the fragment UI should attach to, if any.
     * @param savedInstanceState the previously saved instance state, if any.
     * @return the root view of the fragment's layout.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStandBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Code to handle the add new product button click
        Button addButton = view.findViewById(R.id.buttonAddProduct);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.buttonAddProduct) {
                    Intent intent = new Intent(requireContext(), InsertProductActivity.class);
                    startActivity(intent);
                }
            }
        });

        // Set up RecyclerView with LinearLayoutManager
        binding.standRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the adapter with the product list and item click listener
        mProductAdapter = new ProdottoAdapter(mProducts, new ProdottoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Prodotto prodotto) {
                // Navigate to ProdottoDetailsActivity with the selected product
                Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                intent.putExtra("prodotto", prodotto);
                startActivity(intent);
            }
        }, R.layout.prodotto_item, getActivity().getApplication());

        // Set the adapter to the RecyclerView
        binding.standRecyclerView.setAdapter(mProductAdapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.standRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY > oldScrollY + 5 && binding.buttonAddProduct.isShown())
                        binding.buttonAddProduct.shrink();
                    else if (scrollY < oldScrollY - 20)
                        binding.buttonAddProduct.extend();
                    else if (scrollY == 0)
                        binding.buttonAddProduct.extend();
                }
            });
        }

        // Return the fragment's view
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home_menu, menu);
        menu.getItem(0).setVisible(false);
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
        return currentUser != null;
    }
}

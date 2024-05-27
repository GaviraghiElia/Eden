package com.unimib.eden.ui.home;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.adapter.ColturaAdapter;
import com.unimib.eden.databinding.FragmentHomeBinding;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.R;
import com.unimib.eden.ui.authentication.AuthenticationActivity;
import com.unimib.eden.ui.colturaDetails.ColturaDetailsActivity;
import com.unimib.eden.ui.searchPianta. SearchPiantaActivity;
import com.unimib.eden.ui.inserimentoColtura.InserimentoColturaActivity;
import com.unimib.eden.ui.inserimentoProdotto.InserimentoProdottoActivity;
import com.unimib.eden.ui.main.MainActivity;
import com.unimib.eden.ui.piantaDetails.PiantaDetailsActivity;
import com.unimib.eden.ui.searchPianta.SearchPiantaActivity;
import com.unimib.eden.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Una semplice sottoclasse di {@link Fragment} per la schermata Home.
 * Questo frammento visualizza un elenco di colture.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private FragmentHomeBinding mBinding;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Pianta> piante = new ArrayList<Pianta>();
    private List<Coltura> colture = new ArrayList<>();
    private NavController navController;

    private List<Coltura> mColture = new ArrayList<>();
    public HomeViewModel homeViewModel;
    private ColturaAdapter mColturaAdapter;
    private FirebaseAuth firebaseAuth;
    /**
     * Costruttore predefinito per HomeFragment.
     */
    public HomeFragment() {
        // Costruttore pubblico vuoto richiesto
    }

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle(requireActivity().getResources().getText(R.string.exitApp))
                        .setMessage(requireActivity().getResources().getText(R.string.exitAppInfo))
                        .setPositiveButton("Si",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        requireActivity().finish();
                                    }
                                })
                        .setNegativeButton(requireActivity().getResources().getText(R.string.cancel),
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                }).show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mAuth = FirebaseAuth.getInstance();

        final Observer<List<Coltura>> allColtureObserver = new Observer<List<Coltura>>() {
            @Override
            public void onChanged(List<Coltura> coltura) {

                Log.d(TAG, "onChanged: ");
                mColture = coltura;

                mColturaAdapter.update(mColture);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        homeViewModel.getColture().observe(this, allColtureObserver);


        // Recupera le colture dal ViewModel
        //mColture = homeViewModel.getColture();

        homeViewModel.updateDB("g.colombo147@campus.unimib.it");
        Log.d(TAG, "onCreate: COLTURE: " + mColture.toString());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate il layout per questo frammento
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        // Codice per gestire il click del pulsante per aggiungere una nuova coltura
        Button addButton = view.findViewById(R.id.buttonAddColtura);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.buttonAddColtura) {
                    Intent intent = new Intent(requireContext(), InserimentoColturaActivity.class);
                    startActivity(intent);
                }
            }
        });

        navController = NavHostFragment.findNavController(this);
        if(checkSession())
        {
            Log.d("mAuth", "home fragment - user sign in");
            Log.d("mAuth", "home fragment " + requireActivity());

        }else
        {
            Log.d("mAuth", "home fragment - user not auth");
            Log.d("mAuth", "home fragment - this activity is" + requireActivity());
            navController.navigate(R.id.action_navigation_home_to_registerFragment);
        }

        BottomNavigationView navBar = requireActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);

        // Imposta RecyclerView con LinearLayoutManager
        mBinding.homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inizializza l'adapter con l'elenco delle colture e il listener del clic sull'elemento
        mColturaAdapter = new ColturaAdapter(mColture, new ColturaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Coltura coltura) {
                // Gestisce l'evento di clic sull'elemento
                Log.d(TAG, "OnItemClick " + coltura.toString());

                // Naviga verso ColturaDetailsFragment con la coltura selezionata
                //HomeFragmentDirections.ActionNavigationHomeToColturaDetailsFragment action = HomeFragmentDirections.actionNavigationHomeToColturaDetailsFragment(coltura);
                //Navigation.findNavController(view).navigate(action);
                Intent intent = new Intent(getActivity(), ColturaDetailsActivity.class);
                intent.putExtra("coltura", coltura);
                startActivity(intent);
            }
        }, R.layout.coltura_item, getActivity().getApplication());

        // Imposta l'adapter su RecyclerView
        mBinding.homeRecyclerView.setAdapter(mColturaAdapter);

        Log.d(TAG, "onCreateView: mFasi " + homeViewModel.getFasi().toString());
        Log.d(TAG, "onCreateView: mPiante " + homeViewModel.getPiante().toString());
        Log.d(TAG, "onCreateView: mColture " + homeViewModel.getColture().getValue());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBinding.homeRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY > oldScrollY + 5 && mBinding.buttonAddColtura.isShown())
                        mBinding.buttonAddColtura.shrink();
                    else if (scrollY < oldScrollY - 20)
                        mBinding.buttonAddColtura.extend();
                    else if (scrollY == 0)
                        mBinding.buttonAddColtura.extend();
                }
            });
        }
        //homeViewModel.updateDB("g.colombo147@campus.unimib.it");

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
        if (item.getItemId() == R.id.app_bar_search) {
            Intent intent = new Intent(getActivity(), SearchPiantaActivity.class);
            intent.putExtra("operationCode", Constants.SEARCH_PIANTA_OPERATION_CODE);
            startActivity(intent);
            return true;
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
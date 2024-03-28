package com.unimib.eden.ui.colturaDetails;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unimib.eden.R;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.utils.Converters;


public class ColturaDetailsFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ColturaDetailsFragment";

    private Coltura coltura;

    //private Button pianta;

    private ColturaDetailsViewModel colturaDetailsViewModel;

    private NavController navController;

    public ColturaDetailsFragment() {
    }

    public static ColturaDetailsFragment newInstance() {
        ColturaDetailsFragment fragment = new ColturaDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coltura = ColturaDetailsFragmentArgs.fromBundle(getArguments()).getColtura();

        colturaDetailsViewModel = new ViewModelProvider(this).get(ColturaDetailsViewModel.class);
        colturaDetailsViewModel.initialize(coltura);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coltura_details, container, false);

        // Enable the back button in the ActionBar
        if (getActivity() != null) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(colturaDetailsViewModel.getNomePianta(coltura));
        }

        TextView textViewColturaPianta = view.findViewById(R.id.textViewPiantaFull);
        textViewColturaPianta.setText(colturaDetailsViewModel.getNomePianta(coltura));
        TextView textViewGiorniInnaffiamento = view.findViewById(R.id.textViewDaysNumberFull);
        textViewGiorniInnaffiamento.setText(String.valueOf(colturaDetailsViewModel.getGiorniInnaffiamento(coltura)));
        TextView textViewDataInserimento = view.findViewById(R.id.textViewDateFull);
        textViewDataInserimento.setText(Converters.dateToString(coltura.getDataInserimento()));
        TextView textViewNote = view.findViewById(R.id.textViewNoteFull);
        textViewNote.setText(coltura.getNote());

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle back button click
        if (item.getItemId() == android.R.id.home) {
            Log.d(TAG, "credici");
            // Handle the back button click here
            // For example, you can navigate back or perform any other action
            return true; // Indicate that the click event has been handled
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
            /*case R.id.button_addFriend:
                if(loggedUser.getFriends().contains(coltura.getEmail())) {
                    buttonAddFriend.setText(getString(R.string.aggiungi_amico));
                    colturaDetailsViewModel.removeFriend(coltura);
                }
                else {
                    buttonAddFriend.setText(buttonAddFriend.getText() + " ✓");
                    colturaDetailsViewModel.addFriend(coltura);
                }
                break;*/
    }
}
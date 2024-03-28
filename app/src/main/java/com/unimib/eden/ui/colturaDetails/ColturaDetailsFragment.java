package com.unimib.eden.ui.colturaDetails;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
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

        TextView textViewColturaPianta = view.findViewById(R.id.textViewPiantaFull);
        textViewColturaPianta.setText(coltura.getIdPianta());
        TextView textViewGiorniInnaffiamento = view.findViewById(R.id.textViewDaysNumberFull);
        textViewGiorniInnaffiamento.setText(Converters.dateToString(coltura.getUltimoInnaffiamento()));
        TextView textViewDataInserimento = view.findViewById(R.id.textViewDateFull);
        textViewDataInserimento.setText(Converters.dateToString(coltura.getDataInserimento()));
        TextView textViewNote = view.findViewById(R.id.textViewNoteFull);
        textViewNote.setText(coltura.getNote());

        return view;
    }

    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.button_addFriend:
                if(loggedUser.getFriends().contains(coltura.getEmail())) {
                    buttonAddFriend.setText(getString(R.string.aggiungi_amico));
                    colturaDetailsViewModel.removeFriend(coltura);
                }
                else {
                    buttonAddFriend.setText(buttonAddFriend.getText() + " ✓");
                    colturaDetailsViewModel.addFriend(coltura);
                }
                break;
        }*/
    }
}
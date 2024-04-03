package com.unimib.eden.ui.colturaDetails;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unimib.eden.databinding.FragmentColturaDetailsBinding;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.utils.Converters;

/**
 * Un semplice {@link Fragment} per visualizzare i dettagli di una coltura.
 */
public class ColturaDetailsFragment extends Fragment {

    private static final String TAG = "ColturaDetailsFragment";

    private Coltura coltura;
    private ColturaDetailsViewModel colturaDetailsViewModel;
    private FragmentColturaDetailsBinding mBinding;

    /**
     * Costruttore predefinito per ColturaDetailsFragment.
     */
    public ColturaDetailsFragment() {
        // Costruttore pubblico vuoto richiesto
    }

    /**
     * Metodo factory per creare una nuova istanza di questo fragment.
     *
     * @return Una nuova istanza di ColturaDetailsFragment.
     */
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

        // Inizializza il ViewModel e passa la coltura selezionata
        colturaDetailsViewModel = new ViewModelProvider(this).get(ColturaDetailsViewModel.class);
        colturaDetailsViewModel.initialize(coltura);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla il layout per questo fragment
        mBinding = FragmentColturaDetailsBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        // Imposta il titolo nella action bar
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(colturaDetailsViewModel.getNomePianta(coltura));
        }

        // Popola gli elementi UI con i dettagli della coltura
        mBinding.textViewUltimoInnaffiamentoFull.setText(colturaDetailsViewModel.getProssimoInnaffiamento(getContext(), coltura));
        mBinding.textViewDataInserimentoFull.setText(Converters.dateToString(coltura.getDataInserimento()));
        //TODO: fase pianta
        mBinding.textViewFaseAttualeFull.setText("fase fase");
        if (coltura.getNote().isEmpty()) {
            mBinding.imageViewNoteFull.setVisibility(View.GONE);
            mBinding.textViewNoteFull.setVisibility(View.GONE);
        } else {
            mBinding.textViewNoteFull.setText(coltura.getNote());
        }
        mBinding.textViewQuantityFull.setText(String.valueOf(coltura.getQuantita()));
        return view;
    }

    //TODO: aggiungere onclick del bottone per aprire dettagli pianta
    /*@Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonViewAllDetails:
                Log.d(TAG, "onClick pianta details");
                break;
        }
    }*/

}
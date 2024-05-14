package com.unimib.eden.ui.inserimentoProdotto;

import static com.unimib.eden.utils.Constants.PRODOTTO_ALTRE_INFORMAZIONI;
import static com.unimib.eden.utils.Constants.PRODOTTO_FASE_ATTUALE;
import static com.unimib.eden.utils.Constants.PRODOTTO_OFFERTE;
import static com.unimib.eden.utils.Constants.PRODOTTO_PIANTA;
import static com.unimib.eden.utils.Constants.PRODOTTO_PREZZO;
import static com.unimib.eden.utils.Constants.PRODOTTO_QUANTITA;
import static com.unimib.eden.utils.Constants.PRODOTTO_SCAMBIO_DISPONIBILE;
import static com.unimib.eden.utils.Constants.PRODOTTO_TIPO;
import static com.unimib.eden.utils.Constants.PRODOTTO_VENDITORE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.unimib.eden.databinding.ActivityInserimentoProdottoBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.unimib.eden.R;
import com.unimib.eden.model.Fase;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.ui.searchPianta.SearchPiantaActivity;
import com.unimib.eden.utils.Constants;

/**
 * Activity per l'inserimento di un nuovo prodotto.
 * Questa activity consente all'utente di inserire i dettagli di un nuovo prodotto e di aggiungerlo al database.
 */
public class InserimentoProdottoActivity extends AppCompatActivity {
    private static final String TAG = "InserimentoProdotto";
    private InserimentoProdottoViewModel inserimentoProdottoViewModel;
    private ActivityInserimentoProdottoBinding mBinding;
    private String pomodoroId = "beVITqkLHWCerI1XLRxj";
    private Pianta pianta;
    private String ultimaFase = "";

    //per prendere current user
    private FirebaseAuth firebaseAuth;

    ArrayList<String> nomeFasi = new ArrayList<String>();

    List<Fase> fasiList;
    ArrayAdapter<String> adapter;


    /**
     * Metodo chiamato quando l'activity viene creata. Qui vengono inizializzati i componenti
     * dell'interfaccia utente, impostati i listener e ottenuti i dati necessari dal ViewModel.
     *
     * @param savedInstanceState Oggetto Bundle contenente lo stato precedente dell'activity,
     *                           se disponibile.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inserimentoProdottoViewModel = new ViewModelProvider(this).get(InserimentoProdottoViewModel.class);

        firebaseAuth = FirebaseAuth.getInstance();
        mBinding = ActivityInserimentoProdottoBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.toolbarInsProd.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24_white);
        mBinding.toolbarInsProd.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mBinding.prezzo.addTextChangedListener(prodottoTextWatcher);
        mBinding.quantita.addTextChangedListener(prodottoTextWatcher);
        mBinding.pianta.addTextChangedListener(prodottoTextWatcher);
        mBinding.autoCompleteTextViewFasi.addTextChangedListener(prodottoTextWatcher);


        ActivityResultLauncher<Intent> searchPiantaActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode()== Activity.RESULT_OK){
                            Intent data = o.getData();
                            pianta = (Pianta) data.getSerializableExtra("pianta");
                            Log.d(TAG, "onActivityResult: PIANTA " + pianta.toString());
                            mBinding.pianta.setText(pianta.getNome());
                            mBinding.toolbarInsProd.setTitle("Inserisci " + pianta.getNome());
                            try {
                                fasiList = inserimentoProdottoViewModel.getFasiList(pianta.getFasi());
                                if(!nomeFasi.isEmpty()) {
                                    nomeFasi.clear();
                                }
                                for(Fase f: fasiList) {
                                    nomeFasi.add(f.getNomeFase());
                                }

                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            ultimaFase = nomeFasi.get(nomeFasi.size()-1);
                            adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, nomeFasi);
                        }
                    }
                });
        mBinding.pianta.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Intent intent = new Intent(getApplicationContext(), SearchPiantaActivity.class);
                    intent.putExtra("operationCode", Constants.CREATE_PRODOTTO_OPERATION_CODE);
                    searchPiantaActivityResultLauncher.launch(intent);
                    mBinding.pianta.clearFocus();
                }
            }
        });



        //TODO: forse android.R.layout è da cambiare
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nomeFasi);
        mBinding.autoCompleteTextViewFasi.setAdapter(adapter);
        //binding.autoCompleteTextViewFasi.setText(nomeFasi.get(0), false);

        mBinding.buttonSubmit.setOnClickListener(v -> {
            aggiungiProdotto();
        });


        mBinding.autoCompleteTextViewFasi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == nomeFasi.size() - 1) {
                    mBinding.textViewQuantitaUnita.setText("grammi");
                    //textViewQuantitaUnita.setText("grammi");
                } else {
                    mBinding.textViewQuantitaUnita.setText("piante");
                    //textViewQuantitaUnita.setText("piante");
                }
            }
        });
    }

    private TextWatcher prodottoTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String prezzo = mBinding.prezzo.getText().toString();
            String quantita = mBinding.quantita.getText().toString();
            String piantaText = mBinding.pianta.getText().toString();
            String fasiText = mBinding.autoCompleteTextViewFasi.getText().toString();
            mBinding.buttonSubmit.setEnabled(!prezzo.isEmpty() && !quantita.isEmpty() && !piantaText.isEmpty() && !fasiText.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * Metodo chiamato quando l'utente preme il pulsante "Invia" per aggiungere un nuovo prodotto.
     * Raccoglie i dati inseriti dall'utente dall'interfaccia utente e li invia al ViewModel
     * per l'aggiunta del prodotto al database.
     */
    private void aggiungiProdotto() {
        double prezzo = Double.parseDouble(mBinding.prezzo.getText().toString());
        int quantita = Integer.parseInt(mBinding.quantita.getText().toString());
        String altreInformazioni = mBinding.altreInformazioni.getText().toString();
        Boolean scambioDisponibile = mBinding.checkBoxDisponibileAScambi.isChecked();
        //String pianta = binding.pianta.getText().toString();
        String faseAttuale = mBinding.autoCompleteTextViewFasi.getText().toString();

        String tipo;
        //controllo se ultima fase .equals() quella scelta
        if(faseAttuale.equals(ultimaFase)){
            tipo="eccedenza";
        }else{
            tipo="coltura";
        }

        Map<String, Object> prodotto = new HashMap<>();
        prodotto.put(PRODOTTO_TIPO, tipo);
        prodotto.put(PRODOTTO_PREZZO, prezzo);
        prodotto.put(PRODOTTO_PIANTA, pomodoroId);
        prodotto.put(PRODOTTO_QUANTITA, quantita);
        prodotto.put(PRODOTTO_FASE_ATTUALE, faseAttuale);
        prodotto.put(PRODOTTO_ALTRE_INFORMAZIONI, altreInformazioni);

        String utente = firebaseAuth.getCurrentUser().getUid();
        prodotto.put(PRODOTTO_VENDITORE, utente);
        prodotto.put(PRODOTTO_OFFERTE, null);
        prodotto.put(PRODOTTO_SCAMBIO_DISPONIBILE, scambioDisponibile);
        inserimentoProdottoViewModel.aggiungiProdotto(prodotto);
        finish();
    }
}

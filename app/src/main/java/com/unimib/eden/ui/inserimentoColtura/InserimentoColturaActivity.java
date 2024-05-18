package com.unimib.eden.ui.inserimentoColtura;

import static com.unimib.eden.utils.Constants.COLTURA_DATA_INSERIMENTO;
import static com.unimib.eden.utils.Constants.COLTURA_FASE_ATTUALE;
import static com.unimib.eden.utils.Constants.COLTURA_FREQUENZA_INNAFFIAMENTO;
import static com.unimib.eden.utils.Constants.COLTURA_NOTE;
import static com.unimib.eden.utils.Constants.COLTURA_PIANTA;
import static com.unimib.eden.utils.Constants.COLTURA_PROPRIETARIO;
import static com.unimib.eden.utils.Constants.COLTURA_QUANTITA;
import static com.unimib.eden.utils.Constants.COLTURA_ULTIMO_INNAFFIAMENTO;
import static com.unimib.eden.utils.Constants.PIANTA_NOME;

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

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.unimib.eden.databinding.ActivityInserimentoColturaBinding;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.unimib.eden.R;
import com.unimib.eden.model.Fase;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.ui.inserimentoColtura.InserimentoColturaViewModel;
import com.unimib.eden.ui.searchPianta.SearchPiantaActivity;
import com.unimib.eden.utils.Constants;

/**
 * Activity per l'inserimento di un nuovo prodotto.
 * Questa activity consente all'utente di inserire i dettagli di un nuovo prodotto e di aggiungerlo al database.
 */
public class InserimentoColturaActivity extends AppCompatActivity {
    private static final String TAG = "InserimentoColtura";
    private InserimentoColturaViewModel inserimentoColturaViewModel;
    private ActivityInserimentoColturaBinding mBinding;
    private String piantaId = "";
    private String piantaNome = "";
    private Pianta pianta;
    private int fase = -1;
    private String ultimaFase = "";

    //per prendere current user
    private FirebaseAuth firebaseAuth;

    ArrayList<String> nomeFasi = new ArrayList<String>();

    ArrayList<Fase> fasiList;
    ArrayList<Integer> frequenze;
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
        inserimentoColturaViewModel = new ViewModelProvider(this).get(InserimentoColturaViewModel.class);

        firebaseAuth = FirebaseAuth.getInstance();
        mBinding = ActivityInserimentoColturaBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.toolbarInsProd.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24_white);
        mBinding.toolbarInsProd.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mBinding.quantita.addTextChangedListener(colturaTextWatcher);
        mBinding.pianta.addTextChangedListener(colturaTextWatcher);
        mBinding.autoCompleteTextViewFasi.addTextChangedListener(colturaTextWatcher);


        ActivityResultLauncher<Intent> searchPiantaActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {

                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode()== Activity.RESULT_OK){
                            Intent data = o.getData();
                            pianta = (Pianta) data.getSerializableExtra("pianta");
                            Log.d(TAG, "onActivityResult: PIANTA " + pianta.toString());
                            piantaId = pianta.getId();
                            piantaNome = pianta.getNome();
                            mBinding.pianta.setText(pianta.getNome());
                            mBinding.toolbarInsProd.setTitle("Inserisci " + pianta.getNome());
                            try {
                                fasiList = inserimentoColturaViewModel.getFasiList(pianta.getFasi());
                                frequenze = inserimentoColturaViewModel.getFrequenzeInnaffiamento(pianta.getFasi());
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
                    //TODO: aggiungere CREATE_COLTURA_OPERATION_CODE
                    intent.putExtra("operationCode", Constants.CREATE_PRODOTTO_OPERATION_CODE);
                    searchPiantaActivityResultLauncher.launch(intent);
                    mBinding.pianta.clearFocus();
                }
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nomeFasi);
        mBinding.autoCompleteTextViewFasi.setAdapter(adapter);
        //binding.autoCompleteTextViewFasi.setText(nomeFasi.get(0), false);

        mBinding.buttonSubmit.setOnClickListener(v -> {
            aggiungiColtura();
        });


        mBinding.autoCompleteTextViewFasi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fase=position;
                /*if (position == nomeFasi.size() - 1) {
                    //mBinding.textViewQuantitaUnita.setText("grammi");
                } else {
                    //mBinding.textViewQuantitaUnita.setText("piante");
                }*/
            }
        });
    }

    private TextWatcher colturaTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String quantita = mBinding.quantita.getText().toString();
            String piantaText = mBinding.pianta.getText().toString();
            String fasiText = mBinding.autoCompleteTextViewFasi.getText().toString();
            mBinding.buttonSubmit.setEnabled(!quantita.isEmpty() && !piantaText.isEmpty() && !fasiText.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onStart() {
        super.onStart();
    }

    //TODO: da cambiare tutti i commenti
    /**
     * Metodo chiamato quando l'utente preme il pulsante "Invia" per aggiungere un nuovo prodotto.
     * Raccoglie i dati inseriti dall'utente dall'interfaccia utente e li invia al ViewModel
     * per l'aggiunta del prodotto al database.
     */
    private void aggiungiColtura() {
        int quantita = Integer.parseInt(mBinding.quantita.getText().toString());
        String note = mBinding.note.getText().toString();
        Log.d(TAG, frequenze.toString());

        Map<String, Object> coltura = new HashMap<>();
        String utente = firebaseAuth.getCurrentUser().getUid();
        coltura.put(COLTURA_PROPRIETARIO, utente);
        coltura.put(COLTURA_PIANTA, piantaId);
        coltura.put(COLTURA_QUANTITA, quantita);
        coltura.put(COLTURA_NOTE, note);

        Date now = new Date();
        Log.d(TAG, "ora attuale:" + now.toString());
        Timestamp timestamp = new Timestamp(now);
        coltura.put(COLTURA_DATA_INSERIMENTO, timestamp);
        coltura.put(COLTURA_ULTIMO_INNAFFIAMENTO, timestamp);

        coltura.put(COLTURA_FASE_ATTUALE, fase);
        coltura.put(PIANTA_NOME, piantaNome);

        //TODO: da sistemare l'ordinamento
        coltura.put(COLTURA_FREQUENZA_INNAFFIAMENTO, frequenze);

        Log.d(TAG, "coltura creata: " + coltura.toString());
        //inserimentoColturaViewModel.aggiungiColtura(coltura);
        finish();
    }
}
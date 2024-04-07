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

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.unimib.eden.databinding.ActivityInserimentoProdottoBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.unimib.eden.R;

/**
 * Activity per l'inserimento di un nuovo prodotto.
 * Questa activity consente all'utente di inserire i dettagli di un nuovo prodotto e di aggiungerlo al database.
 */
public class InserimentoProdottoActivity extends AppCompatActivity {
    private static final String TAG = "InserimentoProdotto";
    private InserimentoProdottoViewModel inserimentoProdottoViewModel;
    private ActivityInserimentoProdottoBinding binding;
    private String pomodoroId = "beVITqkLHWCerI1XLRxj";
    private String ultimaFase = "";

    //per prendere current user
    private FirebaseAuth firebaseAuth;

    String[] pomodoroFasi = {};
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
        binding = ActivityInserimentoProdottoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.prezzo.addTextChangedListener(prodottoTextWatcher);
        binding.quantita.addTextChangedListener(prodottoTextWatcher);

        pomodoroFasi = inserimentoProdottoViewModel.getFasiDaId(pomodoroId).toArray(new String[0]);
        ultimaFase = pomodoroFasi[pomodoroFasi.length-1];

        //TODO: forse android.R.layout è da cambiare
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pomodoroFasi);
        binding.autoCompleteTextViewFasi.setAdapter(adapter);
        binding.autoCompleteTextViewFasi.setText(pomodoroFasi[0], false);

        binding.buttonSubmit.setOnClickListener(v -> {
            aggiungiProdotto();
        });

        //codice per aggiornare l'unità di misura
        TextView textViewQuantitaUnita = findViewById(R.id.textViewQuantitaUnita);
        binding.autoCompleteTextViewFasi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == pomodoroFasi.length - 1) {
                    textViewQuantitaUnita.setText("grammi");
                } else {
                    textViewQuantitaUnita.setText("piante");
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
            String prezzo = binding.prezzo.getText().toString();
            String quantita = binding.quantita.getText().toString();
            binding.buttonSubmit.setEnabled(!prezzo.isEmpty() && !quantita.isEmpty());
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
        double prezzo = Double.parseDouble(binding.prezzo.getText().toString());
        int quantita = Integer.parseInt(binding.quantita.getText().toString());
        String altreInformazioni = binding.altreInformazioni.getText().toString();
        Boolean scambioDisponibile = binding.checkBoxDisponibileAScambi.isChecked();
        //String pianta = binding.pianta.getText().toString();
        String faseAttuale = binding.autoCompleteTextViewFasi.getText().toString();

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
        //String utente = firebaseAuth.getCurrentUser().getUid();
        prodotto.put(PRODOTTO_VENDITORE, "s.erba9@campus.unimib.it"); //TODO: prendere id venditore
        prodotto.put(PRODOTTO_OFFERTE, null);
        prodotto.put(PRODOTTO_SCAMBIO_DISPONIBILE, scambioDisponibile);
        Log.d(TAG, "1: " + prodotto.toString());
        inserimentoProdottoViewModel.aggiungiProdotto(prodotto);
        Log.d(TAG, "2: " + prodotto.toString());
    }
}

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
import android.util.Log;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.unimib.eden.databinding.ActivityInserimentoProdottoBinding;
import java.util.HashMap;
import java.util.Map;
import com.unimib.eden.R;

public class InserimentoProdottoActivity extends AppCompatActivity {
    private static final String TAG = "InserimentoProdotto";
    private InserimentoProdottoViewModel inserimentoProdottoViewModel;
    private ActivityInserimentoProdottoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inserimentoProdottoViewModel = new ViewModelProvider(this).get(InserimentoProdottoViewModel.class);

        binding = ActivityInserimentoProdottoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonSubmit.setOnClickListener(v -> {
            Log.d(TAG, "sei dentro onClick");
            aggiungiProdotto();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void aggiungiProdotto() {
        double prezzo = Double.parseDouble(binding.editTextPrezzo.getText().toString());
        String pianta = binding.editTextPianta.getText().toString();
        int quantita = Integer.parseInt(binding.editTextQuantita.getText().toString());
        String altreInformazioni = binding.editTextAltreInformazioni.getText().toString();
        Boolean scambioDisponibile = binding.switchScambioDisponibile.isChecked();

        //TODO: spinner varia in base alla fase scelta
        //Spinner spinnerQuantitaUnita = binding.spinnerQuantitaUnita;
        //int selectedIndex = spinnerQuantitaUnita.getSelectedItemPosition();
        //String selectedValue = getResources().getStringArray(R.array.quantita_unita_options)[selectedIndex];

        Spinner spinnerFaseAttuale = binding.spinnerFaseAttuale;
        int faseAttuale = spinnerFaseAttuale.getSelectedItemPosition()+1;

        String tipo;
        if(faseAttuale==4){
            tipo="eccedenza";
        }else if(faseAttuale<4){
            tipo="coltura";
        }
        else{
            tipo="errore";
        }
        Map<String, Object> prodotto = new HashMap<>();
        prodotto.put(PRODOTTO_TIPO, tipo);
        prodotto.put(PRODOTTO_PREZZO, prezzo);
        prodotto.put(PRODOTTO_PIANTA, pianta);
        prodotto.put(PRODOTTO_QUANTITA, quantita);
        prodotto.put(PRODOTTO_FASE_ATTUALE, faseAttuale);
        prodotto.put(PRODOTTO_ALTRE_INFORMAZIONI, altreInformazioni);
        prodotto.put(PRODOTTO_VENDITORE, "s.erba9@campus.unimib.it"); //TODO: prendere id venditore
        prodotto.put(PRODOTTO_OFFERTE, null);
        prodotto.put(PRODOTTO_SCAMBIO_DISPONIBILE, scambioDisponibile);
        inserimentoProdottoViewModel.aggiungiProdotto(prodotto);
    }
}

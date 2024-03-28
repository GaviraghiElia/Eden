package com.unimib.eden.ui.inserimentoProdotto;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.unimib.eden.R;
import com.unimib.eden.model.Prodotto;
import com.unimib.eden.repository.ProdottoRepository;

public class InserimentoProdottoActivity extends AppCompatActivity {
    private static final String TAG = "InserimentoProdotto";
    private ProdottoRepository prodottoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserimento_prodotto);
        prodottoRepository = new ProdottoRepository(this.getApplication());

        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "sei dentro onClick");
                aggiungiProdotto();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void aggiungiProdotto() {
        EditText editTextTipo = findViewById(R.id.editTextTipo);
        EditText editTextPrezzo = findViewById(R.id.editTextPrezzo);
        EditText editTextPianta = findViewById(R.id.editTextPianta);
        EditText editTextQuantita = findViewById(R.id.editTextQuantita);
        EditText editTextFaseAttuale = findViewById(R.id.editTextFaseAttuale);
        EditText editTextAltreInformazioni = findViewById(R.id.editTextAltreInformazioni);

        String tipo = editTextTipo.getText().toString();
        double prezzo = Double.parseDouble(editTextPrezzo.getText().toString());
        String pianta = editTextPianta.getText().toString();
        int quantita = Integer.parseInt(editTextQuantita.getText().toString());
        int faseAttuale = Integer.parseInt(editTextFaseAttuale.getText().toString());
        String altreInformazioni = editTextAltreInformazioni.getText().toString();
        Log.d(TAG, "Tipo: " + tipo);
        Log.d(TAG, "Prezzo: " + prezzo);
        Log.d(TAG, "Pianta: " + pianta);
        Log.d(TAG, "Quantità: " + quantita);
        Log.d(TAG, "Fase Attuale: " + faseAttuale);
        Log.d(TAG, "Altre Informazioni: " + altreInformazioni);
        //inserimento nel db
        Prodotto nuovoProdotto = new Prodotto("id_prova", tipo, "venditore_prova", prezzo, pianta, null, quantita, faseAttuale, altreInformazioni);
        prodottoRepository.aggiungiProdotto(nuovoProdotto);

    }
}

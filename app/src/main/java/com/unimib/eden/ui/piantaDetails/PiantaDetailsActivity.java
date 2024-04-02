package com.unimib.eden.ui.piantaDetails;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.unimib.eden.databinding.ActivityPiantaDetailsBinding;
import com.unimib.eden.model.Fase;
import com.unimib.eden.model.Pianta;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PiantaDetailsActivity extends AppCompatActivity {

    private static final String TAG = "PiantaDetailsActivity";
    private ActivityPiantaDetailsBinding binding;
    private PiantaDetailsViewModel piantaDetailsViewModel;
    private LiveData<Pianta> game = new LiveData<Pianta>() {};
    String idNotification = null;
    Pianta pianta = null;
    List<Fase> fasi = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPiantaDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        piantaDetailsViewModel = new ViewModelProvider(this).get(PiantaDetailsViewModel.class);

        Intent intent = getIntent();
        pianta = (Pianta) intent.getSerializableExtra("pianta");

        binding.piantaNomeDetails.setText(pianta.getNome());
        binding.famigliaBotanicaDetails.setText(pianta.getFamigliaBotanica());
        binding.frequenzaInnaffiamentoDetails.setText(String.valueOf(pianta.getFrequenzaInnaffiamento()) + " al giorno");
        binding.minTemperaturaDetails.setText(String.valueOf(pianta.getMinTemperatura()) + "°");
        binding.maxTemperaturaDetails.setText(String.valueOf(pianta.getMaxTemperatura())+ "°");
        binding.spazioNecessarioDetails.setText(String.valueOf(pianta.getSpazioNecessario()) + " cm");
        binding.altezzaMaxPrevistaDetails.setText(String.valueOf(pianta.getAltezzaMaxPrevista()) + " cm");
        binding.tipoTerrenoDetails.setText(pianta.getTipoTerreno());
        binding.esposizioneSoleDetails.setText(pianta.getEsposizioneSole());
        binding.inizioSeminaDetails.setText(getMese(pianta.getInizioSemina()));
        binding.fineSeminaDetails.setText(getMese(pianta.getFineSemina()));
        binding.descriptionDetails.setText(pianta.getDescrizione());


        try {
            fasi = piantaDetailsViewModel.getFasiList(pianta.getFasi());
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        Log.d(TAG, "onCreate: FASI_LIST " + fasi.get(0).getNomeFase());



    }

    public String getMese(int mese) {
        String nomeMese = "";
        switch (mese) {
            case 1:
                nomeMese = "Gennaio";
                break;
            case 2:
                nomeMese = "Febbraio";
                break;
            case 3:
                nomeMese = "Marzo";
                break;
            case 4:
                nomeMese = "Aprile";
                break;
            case 5:
                nomeMese = "Maggio";
                break;
            case 6:
                nomeMese = "Giugno";
                break;
            case 7:
                nomeMese = "Luglio";
                break;
            case 8:
                nomeMese = "Agosto";
                break;
            case 9:
                nomeMese = "Settembre";
                break;
            case 10:
                nomeMese = "Ottobre";
                break;
            case 11:
                nomeMese = "Novembre";
                break;
            case 12:
                nomeMese = "Dicembre";
                break;
        }
        return nomeMese;
    }
}

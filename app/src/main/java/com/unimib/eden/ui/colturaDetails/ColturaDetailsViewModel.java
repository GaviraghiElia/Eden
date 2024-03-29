package com.unimib.eden.ui.colturaDetails;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.PiantaRepository;
import com.unimib.eden.utils.Transformer;

import java.util.ArrayList;

public class ColturaDetailsViewModel extends AndroidViewModel {
    private PiantaRepository piantaRepository;
    public ColturaDetailsViewModel(@NonNull Application application) {
        super(application);

        piantaRepository = new PiantaRepository(application);
    }

    public String getProssimoInnaffiamento(Context context, Coltura coltura) {
        //TODO: sistemare quando scaricherà le piante nel db
        //return Converters.daysTo(coltura, getPiantaById(coltura.getIdPianta()).getFrequenzaInnaffiamento());
        return Transformer.formatProssimoInnaffiamento(context, coltura, new Pianta("", "", "","", 2, 5, 2, new ArrayList<>(), 1.0, "", "", 0, 0, 1));
    }

    public String getNomePianta(Coltura coltura) {
        //TODO: sistemare quando scaricherà le piante nel db
        //return getPiantaById(coltura.getIdPianta()).getNome();
        return "Pomodoro";
    }

    private Pianta getPiantaById(String piantaId) {
        return piantaRepository.getPiantaById(piantaId);
    }

    public void initialize(Coltura coltura) {
    }
}

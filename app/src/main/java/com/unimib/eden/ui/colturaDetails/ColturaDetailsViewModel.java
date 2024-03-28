package com.unimib.eden.ui.colturaDetails;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.unimib.eden.model.Coltura;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.repository.PiantaRepository;
import com.unimib.eden.utils.Converters;

import java.util.List;

public class ColturaDetailsViewModel extends AndroidViewModel {
    private PiantaRepository piantaRepository;
    public ColturaDetailsViewModel(@NonNull Application application) {
        super(application);

        piantaRepository = new PiantaRepository(application);
    }

    public int getGiorniInnaffiamento(Coltura coltura) {
        //TODO: sistemare quando scaricherà le piante nel db
        //return Converters.daysTo(coltura, getPiantaById(coltura.getIdPianta()).getFrequenzaInnaffiamento());
        return Converters.daysTo(coltura, 2);
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

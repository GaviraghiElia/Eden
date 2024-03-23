package com.unimib.eden.ui.bancarella;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.unimib.eden.model.Prodotto;
import com.unimib.eden.repository.ProdottoRepository;
import java.util.List;

public class BancarellaViewModel extends AndroidViewModel {
    //comunica con ProdottoRepository
    private static final String TAG = "BancarellaViewModel";
    private ProdottoRepository prodottoRepository;
    private LiveData<List<Prodotto>> prodottiLiveData;

    public BancarellaViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG,"sei dentro BancarellaViewModel PRIMA di prodottoRepository");
        prodottoRepository = new ProdottoRepository(application);
        //prodottiLiveData = prodottoRepository.getProdotti();
        Log.d(TAG,"sei dentro BancarellaViewModel DOPO di getProdotti");
    }

    public LiveData<List<Prodotto>> getProdottiLiveData() {
        return prodottiLiveData;
    }
}

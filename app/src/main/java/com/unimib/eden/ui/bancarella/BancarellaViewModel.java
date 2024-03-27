package com.unimib.eden.ui.bancarella;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.model.Prodotto;
import com.unimib.eden.repository.ProdottoRepository;
import java.util.List;

public class BancarellaViewModel extends AndroidViewModel {
    private static final String TAG = "BancarellaViewModel";
    private List<Prodotto> mProdotti;
    private ProdottoRepository prodottoRepository;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public BancarellaViewModel(Application application) {
        super(application);

        prodottoRepository = new ProdottoRepository(application);
        mProdotti = prodottoRepository.getAllProdotti();
    }

    public List<Prodotto> getProdotti() {
        Log.d(TAG, "lunghezza mProdotti: " + mProdotti.size());
        return mProdotti;
    }

    public void updateDB() {
        prodottoRepository.updateLocalDB();
    }
}

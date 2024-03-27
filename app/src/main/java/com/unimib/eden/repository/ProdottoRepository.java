package com.unimib.eden.repository;

import static com.unimib.eden.utils.Constants.PRODOTTO_VENDITORE;

import android.app.Application;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unimib.eden.database.PiantaDao;
import com.unimib.eden.database.PiantaRoomDatabase;
import com.unimib.eden.database.ProdottoDao;
import com.unimib.eden.database.ProdottoRoomDatabase;
import com.unimib.eden.model.Pianta;
import com.unimib.eden.model.Prodotto;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.ServiceLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//collegato al ProdottoDao e ProdottoRoomDatabase
public class ProdottoRepository implements IProdottoRepository {
    private static final String TAG = "ProdottoRepository";
    private final ProdottoDao mProdottoDao;
    List<String> prodotti = new ArrayList<String>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Prodotto> allProdotti;

    public ProdottoRepository(Application application) {
        ProdottoRoomDatabase prodottoRoomDatabase = ServiceLocator.getInstance().getProdottoDao(application);
        this.mProdottoDao = prodottoRoomDatabase.prodottoDao();
        allProdotti = mProdottoDao.getAll();
    }

    @Override
    public List<Prodotto> getAllProdotti() {
        Log.d(TAG, "lunghezza allProdotti: " + allProdotti.size());
        return allProdotti;
    }

    @Override
    public void deleteProdotto(Prodotto prodotto) {
        new ProdottoRepository.DeleteProdottoAsyncTask(mProdottoDao).execute(prodotto);
    }

    @Override
    public void insert(Prodotto prodotto) {
        new ProdottoRepository.InsertProdottoAsyncTask(mProdottoDao).execute(prodotto);
    }

    public void updateLocalDB() {
        if(allProdotti.isEmpty()){
            Log.d(TAG, "Scaricamento prodotti personali...");
            db.collection(Constants.FIRESTORE_COLLECTION_PRODOTTI)
                    .whereEqualTo(PRODOTTO_VENDITORE, "s.erba9@campus.unimib.it") //TODO: currentUser
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    insert(new Prodotto(document));
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });
        } else {
            Log.d(TAG, "Prodotti già presenti nel db");
        }
    }

    private static class DeleteProdottoAsyncTask extends AsyncTask<Prodotto, Void, Void> {
        private ProdottoDao prodottoDao;

        private DeleteProdottoAsyncTask(ProdottoDao prodottoDao) {
            this.prodottoDao = prodottoDao;
        }

        @Override
        protected Void doInBackground(Prodotto... prodotti) {
            prodottoDao.delete(prodotti[0]);
            return null;
        }
    }

    private static class InsertProdottoAsyncTask extends AsyncTask<Prodotto, Void, Void> {
        private ProdottoDao mProdottoDao;

        private InsertProdottoAsyncTask(ProdottoDao prodottoDao) {
            this.mProdottoDao = prodottoDao;
        }

        @Override
        protected Void doInBackground(Prodotto... prodotti) {
            mProdottoDao.insert(prodotti[0]);
            return null;
        }
    }
}



package com.unimib.eden.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unimib.eden.database.ProdottoDao;
import com.unimib.eden.database.ProdottoRoomDatabase;
import com.unimib.eden.model.Prodotto;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.ServiceLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe repository per la gestione delle entità Prodotto, fornendo operazioni di accesso ai dati e sincronizzazione con Firestore.
 */
public class ProdottoRepository implements IProdottoRepository {
    private static final String TAG = "ProdottoRepository";
    private final ProdottoDao mProdottoDao;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Prodotto> allProdotti;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    /**
     * Costruisce un'istanza di ProdottoRepository.
     *
     * @param application il contesto dell'applicazione.
     */
    public ProdottoRepository(Application application) {
        ProdottoRoomDatabase prodottoRoomDatabase = ServiceLocator.getInstance().getProdottoDao(application);
        this.mProdottoDao = prodottoRoomDatabase.prodottoDao();
        allProdotti = mProdottoDao.getAll();
    }

    /**
     * Ottiene tutti gli oggetti Prodotto.
     *
     * @return una lista di tutti gli oggetti Prodotto.
     */
    @Override
    public List<Prodotto> getAllProdotti() {
        Log.d(TAG, "lunghezza allProdotti: " + allProdotti.size());
        return allProdotti;
    }

    /**
     * Elimina un oggetto Prodotto.
     *
     * @param prodotto l'oggetto Prodotto da eliminare.
     */
    @Override
    public void deleteProdotto(Prodotto prodotto) {
        new ProdottoRepository.DeleteProdottoAsyncTask(mProdottoDao).execute(prodotto);
    }

    /**
     * Inserisce un nuovo oggetto Prodotto.
     *
     * @param prodotto il nuovo oggetto Prodotto da inserire.
     */
    @Override
    public void insert(Prodotto prodotto) {
        new ProdottoRepository.InsertProdottoAsyncTask(mProdottoDao).execute(prodotto);
    }

    /**
     * Aggiunge un nuovo prodotto al Firestore e al database locale.
     *
     * @param prodottoMap mappa contenente i dati del prodotto da aggiungere.
     */
    public void aggiungiProdotto(Map<String, Object> prodottoMap) {
        db.collection(Constants.FIRESTORE_COLLECTION_PRODOTTI)
                .add(prodottoMap)
                .addOnSuccessListener(documentReference -> {
                    String prodottoId = documentReference.getId();
                    Log.d(TAG, "Prodotto aggiunto con ID: " + prodottoId);
                    // Aggiungi l'ID al prodottoMap
                    prodottoMap.put(Constants.PRODOTTO_ID, prodottoId);
                    Prodotto prodotto = new Prodotto(prodottoMap);
                    Log.d(TAG, "prodotto: " + prodotto.toString());
                    insert(prodotto);
                })
                .addOnFailureListener(e -> Log.w(TAG, "Errore durante l'aggiunta del prodotto", e));
    }

    /**
     * Aggiorna il database locale con i prodotti da Firestore.
     * Se il database locale è vuoto, scarica i prodotti da Firestore.
     */
    public void updateLocalDB() {
        if(allProdotti.isEmpty()){
            Log.d(TAG, "Scaricamento prodotti personali...");
            String utente = firebaseAuth.getCurrentUser().getUid();

            db.collection(Constants.FIRESTORE_COLLECTION_PRODOTTI)
                    .whereEqualTo(Constants.PRODOTTO_VENDITORE, utente)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, "updateLocalDB: " + document.toString());
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

    // Classi AsyncTask interne

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

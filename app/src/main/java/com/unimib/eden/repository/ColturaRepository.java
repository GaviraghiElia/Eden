package com.unimib.eden.repository;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unimib.eden.database.ColturaDao;
import com.unimib.eden.database.ColturaRoomDatabase;
import com.unimib.eden.model.Coltura;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.ServiceLocator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Classe repository per la gestione delle entità Coltura, fornendo operazioni di accesso ai dati e sincronizzazione con Firestore.
 */
public class ColturaRepository implements IColturaRepository {
    // Campi della classe
    private static final String TAG = "ColturaRepository";
    private final ColturaDao mColturaDao;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private LiveData<List<Coltura>> allColture;

    /**
     * Costruisce un'istanza di ColturaRepository.
     *
     * @param application il contesto dell'applicazione.
     */
    public ColturaRepository(Application application) {
        ColturaRoomDatabase colturaRoomDatabase = ServiceLocator.getInstance().getColturaDao(application);
        this.mColturaDao = colturaRoomDatabase.colturaDao();
        allColture = mColturaDao.getAll();
        Log.d(TAG, "ColturaRepository: allColture " + allColture.getValue());
    }

    /**
     * Recupera tutte le entità Coltura.
     *
     * @return una lista di tutte le entità Coltura.
     */
    @Override
    public LiveData<List<Coltura>> getAllColture() {
        return allColture;
    }

    /**
     * Elimina un'entità Coltura.
     *
     * @param coltura l'entità Coltura da eliminare.
     */
    @Override
    public void deleteColtura(Coltura coltura) {
        new DeleteColturaAsyncTask(mColturaDao).execute(coltura);
    }

    /**
     * Inserisce una nuova entità Coltura.
     *
     * @param coltura la nuova entità Coltura da inserire.
     */
    @Override
    public void insert(Coltura coltura) {
        new InsertColturaAsyncTask(mColturaDao).execute(coltura);
    }

    /**
     * Ottiene un'entità Coltura dal repository basandosi sull'ID specificato.
     *
     * @param colturaId L'ID dell'entità Coltura da cercare nel repository.
     * @return L'entità Coltura corrispondente all'ID specificato, se presente nel repository, altrimenti null.
     */
    @Override
    public Coltura getColturaById(String colturaId) {
        return mColturaDao.getById(colturaId);
    }

    @Override
    public void updateDataInnaffiamentoColtura(Coltura coltura) {
        db.collection(Constants.FIRESTORE_COLLECTION_COLTURE)
                .document(coltura.getId())
                .update("ultimo_innaffiamento", new Date());
        deleteColtura(coltura);
        coltura.setUltimoInnaffiamento(new Date());
        insert(coltura);
    }


    /**
     * Aggiorna il database locale con le entità Coltura da Firestore.
     * Se il database locale è vuoto, scarica le entità Coltura da Firestore.
     */
    public void updateLocalDB(String currentUserId) {
        db.collection(Constants.FIRESTORE_COLLECTION_COLTURE)
                .whereEqualTo(Constants.COLTURA_PROPRIETARIO, currentUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(QueryDocumentSnapshot document: task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Log.d(TAG, "onComplete: allColture " + allColture.getValue());
                                Log.d(TAG, "onComplete: allColture2 " + mColturaDao.getAll().getValue());
                                List<Coltura> tempColtura = allColture.getValue();
                                boolean isColturaPresent = false;
                                boolean isColturaChanged = false;
                                Coltura oldColtura = null;
                                Coltura newColtura = null;
                                //assert tempColtura != null;
                                if (tempColtura != null) {
                                    for (Coltura c : tempColtura) {
                                        if (c.getId().equals(document.getId())) {
                                            isColturaPresent = true;
                                        }
                                        if (c.getId().equals(document.getId()) && c.getFaseAttuale() != Math.toIntExact(Long.valueOf(String.valueOf(document.getData().get("fase_attuale"))))) {
                                            oldColtura = c;
                                            isColturaChanged = true;
                                        }

                                        boolean colturaFireBaseDBNotPresent = false;
                                        for (QueryDocumentSnapshot d : task.getResult()) {
                                            if (c.getId().equals(d.getId())) {
                                                colturaFireBaseDBNotPresent = true;
                                            }
                                        }
                                        if (!colturaFireBaseDBNotPresent) {
                                            deleteColtura(c);
                                        }
                                    }
                                    if (!isColturaPresent) {
                                        newColtura = new Coltura(document);
                                        insert(newColtura);
                                    }
                                    if (isColturaChanged) {
                                        deleteColtura(oldColtura);
                                        newColtura = new Coltura(document);
                                        insert(newColtura);

                                    }
                                } else { //db locale vuoto
                                    newColtura = new Coltura(document);
                                    insert(newColtura);
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        /*
        if(allColture.isEmpty()) {
            Log.d(TAG, "Download delle colture personali...");
            db.collection(Constants.FIRESTORE_COLLECTION_COLTURE)
                    .whereEqualTo(Constants.COLTURA_PROPRIETARIO, currentUserId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    insert(new Coltura(document));
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                }
                            } else {
                                Log.w(TAG, "Errore durante il recupero dei documenti.", task.getException());
                            }
                        }
                    });
        }
        else {
            Log.d(TAG, "Colture già presenti nel database");
        }

         */
    }

    // Classi AsyncTask interne

    private static class DeleteColturaAsyncTask extends AsyncTask<Coltura, Void, Void> {
        private ColturaDao colturaDao;

        private DeleteColturaAsyncTask(ColturaDao colturaDao) {
            this.colturaDao = colturaDao;
        }

        @Override
        protected Void doInBackground(Coltura... colture) {
            colturaDao.delete(colture[0]);
            return null;
        }
    }

    private static class InsertColturaAsyncTask extends AsyncTask<Coltura, Void, Void> {
        private ColturaDao mColturaDao;

        private InsertColturaAsyncTask(ColturaDao colturaDao) {
            this.mColturaDao = colturaDao;
        }

        @Override
        protected Void doInBackground(Coltura... colture) {
            mColturaDao.insert(colture[0]);
            return null;
        }
    }
}

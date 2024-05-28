package com.unimib.eden.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.unimib.eden.R;
import com.unimib.eden.model.FirebaseResponse;
import com.unimib.eden.model.Utente;
import com.unimib.eden.utils.SharedPreferencesProvider;

/**
 * Classe per gestire l'interazione con Firebase in merito alla gestione degli utenti
 *
 * @author Gaviraghi Elia
 */
public class UtenteRepository implements IUtenteRepository{

    /**
     * Istanza di FirebaseAuth.
     */
    private final FirebaseAuth mAuth;

    /**
     * Istanza di Application.
     */
    private final Application mApplication;

    /**
     * Fornitore di SharedPreferences.
     */
    private final SharedPreferencesProvider mSharedPreferencesProvider;

    /**
     * LiveData per la risposta di autenticazione da Firebase.
     */
    private final MutableLiveData<FirebaseResponse> mAuthenticationResponseLiveData;

    /**
     * Istanza di FirebaseDatabase.
     */
    private FirebaseDatabase fDB;

    /**
     * Riferimento al database di Firebase.
     */
    private DatabaseReference reference;

    /**
     * Costruttore per UtenteRepository.
     *
     * @param application l'istanza di Application
     */
    public UtenteRepository(Application application)
    {
        mAuth = FirebaseAuth.getInstance();
        Log.d("FIREBASE", "mauth ok");
        fDB = FirebaseDatabase.getInstance();
        Log.d("FIREBASE", "getIstance ok");
        reference = fDB.getReference("users");
        mApplication = application;
        mAuthenticationResponseLiveData = new MutableLiveData<>();
        mSharedPreferencesProvider = new SharedPreferencesProvider(application);
    }

    /**
     * Metodo per effettuare l'accesso con email e password.
     *
     * @param email l'email dell'utente
     * @param password la password dell'utente
     * @return LiveData con la risposta di Firebase
     */
    @Override
    public MutableLiveData<FirebaseResponse> signInWithEmail(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(ContextCompat.getMainExecutor(mApplication), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        FirebaseResponse firebaseResponse = new FirebaseResponse();
                        if (task.isSuccessful())
                        {
                            FirebaseUser user = mAuth.getCurrentUser();
                            firebaseResponse.setSuccess(true);
                            Log.d("FIREBASE", "signInWithEmail:success", task.getException());
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Log.d("TAG", "signInWithEmail:failure", task.getException());

                            firebaseResponse.setSuccess(false);
                            firebaseResponse.setMessage(task.getException().getMessage());
                        }

                        mAuthenticationResponseLiveData.postValue(firebaseResponse);
                    }
                });
        return mAuthenticationResponseLiveData;
    }

    /**
     * Metodo per creare un utente con email e password.
     *
     * @param email l'email dell'utente
     * @param password la password dell'utente
     * @return LiveData con la risposta di Firebase
     */
    @Override
    public MutableLiveData<FirebaseResponse> createUserWithEmail(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseResponse firebaseResponse = new FirebaseResponse();
                        if (task.isComplete()) {
                            if (task.isSuccessful())
                                firebaseResponse.setSuccess(true);
                            else {
                                firebaseResponse.setSuccess(false);
                                if (task.getException() != null)
                                    firebaseResponse.setMessage(task.getException().getLocalizedMessage());
                                else
                                    firebaseResponse.setMessage(mApplication.getString(R.string.registration_failure));
                            }
                        }
                        mAuthenticationResponseLiveData.postValue(firebaseResponse);
                    }
                });

        return mAuthenticationResponseLiveData;
    }

    /**
     * Metodo per ri-autenticare un utente.
     *
     * @param utente l'utente da ri-autenticare
     * @param email l'email dell'utente
     * @param password la password dell'utente
     * @return LiveData con la risposta di Firebase
     */
    @Override
    public MutableLiveData<FirebaseResponse> reauthenticateUser(Utente utente, String email, String password)
    {
        return null;
    }

    /**
     * Metodo per aggiornare l'email di un utente.
     *
     * @param email la nuova email dell'utente
     * @return LiveData con la risposta di Firebase
     */
    @Override
    public MutableLiveData<FirebaseResponse> updateEmail(String email)
    {
        return null;
    }

    /**
     * Metodo per inviare un link di reset della password.
     *
     * @param email l'email dell'utente
     * @return LiveData con la risposta di Firebase
     */
    @Override
    public MutableLiveData<FirebaseResponse> resetPasswordLink(String email)
    {
        return null;
    }
}

package com.unimib.eden.ui.authentication;

import android.app.Application;
import androidx.annotation.NonNull;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.unimib.eden.model.FirebaseResponse;
import com.unimib.eden.model.Utente;
import com.unimib.eden.repository.IUtenteRepository;
import com.unimib.eden.repository.UtenteRepository;

public class UserViewModel extends AndroidViewModel {

    /**
     * LiveData per la risposta di autenticazione da Firebase.
     */
    private MutableLiveData<FirebaseResponse> mAuthenticationResponseLiveData;
    /**
     * Repository per l'utente.
     */
    private final IUtenteRepository mUserRepository;

    /**
     * Costruttore per UtenteViewModel.
     *
     * @param application l'istanza di Application
     */
    public UserViewModel(@NonNull Application application)
    {
        super(application);
        this.mUserRepository = new UtenteRepository(application);
    }

    /**
     * Metodo per effettuare l'accesso con email e password.
     *
     * @param email l'email dell'utente
     * @param password la password dell'utente
     * @return LiveData con la risposta di Firebase
     */
    public MutableLiveData<FirebaseResponse> signInWithEmail(String email, String password)
    {
        mAuthenticationResponseLiveData = mUserRepository.signInWithEmail(email, password);

        return mAuthenticationResponseLiveData;
    }

    /**
     * Metodo per creare un utente con email e password.
     *
     * @param email l'email dell'utente
     * @param password la password dell'utente
     * @return LiveData con la risposta di Firebase
     */
    public MutableLiveData<FirebaseResponse> createUserWithEmail(String email, String password)
    {
        mAuthenticationResponseLiveData = mUserRepository.createUserWithEmail(email, password);

        return mAuthenticationResponseLiveData;
    }

    /**
     * Metodo per inviare un link di reset della password.
     *
     * @param email l'email dell'utente
     * @return LiveData con la risposta di Firebase
     */
    public MutableLiveData<FirebaseResponse> resetPasswordLink(String email)
    {
        mAuthenticationResponseLiveData = mUserRepository.resetPasswordLink(email);

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
    public MutableLiveData<FirebaseResponse> reauthenticateUser(Utente utente, String email, String password)
    {
        mAuthenticationResponseLiveData = mUserRepository.reauthenticateUser(utente, email, password);

        return mAuthenticationResponseLiveData;
    }

    /**
     * Metodo per aggiornare l'email di un utente.
     *
     * @param email la nuova email dell'utente
     * @return LiveData con la risposta di Firebase
     */
    public MutableLiveData<FirebaseResponse> updateEmail(String email)
    {
        mAuthenticationResponseLiveData = mUserRepository.updateEmail(email);

        return mAuthenticationResponseLiveData;
    }

    /**
     * Metodo per cancellare la risposta di autenticazione.
     */
    public void clear()
    {
        if(mAuthenticationResponseLiveData != null)
        {
            mAuthenticationResponseLiveData.postValue(null);
        }
    }
}

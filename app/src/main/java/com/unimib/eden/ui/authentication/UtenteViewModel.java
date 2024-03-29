package com.unimib.eden.ui.authentication;

import android.app.Application;
import androidx.annotation.NonNull;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.unimib.eden.model.FirebaseResponse;
import com.unimib.eden.model.Utente;
import com.unimib.eden.repository.IUtenteRepository;
import com.unimib.eden.repository.UtenteRepository;

public class UtenteViewModel extends AndroidViewModel {
    private MutableLiveData<FirebaseResponse> mAuthenticationResponseLiveData;
    private final IUtenteRepository mUserRepository;

    public UtenteViewModel(@NonNull Application application)
    {
        super(application);
        this.mUserRepository = new UtenteRepository(application);
    }

    public MutableLiveData<FirebaseResponse> signInWithEmail(String email, String password)
    {
        mAuthenticationResponseLiveData = mUserRepository.signInWithEmail(email, password);

        return mAuthenticationResponseLiveData;
    }

    public MutableLiveData<FirebaseResponse> createUserWithEmail(String email, String password)
    {
        mAuthenticationResponseLiveData = mUserRepository.createUserWithEmail(email, password);

        return mAuthenticationResponseLiveData;
    }

    public MutableLiveData<FirebaseResponse> resetPasswordLink(String email)
    {
        mAuthenticationResponseLiveData = mUserRepository.resetPasswordLink(email);

        return mAuthenticationResponseLiveData;
    }

    public MutableLiveData<FirebaseResponse> reauthenticateUser(Utente utente, String email, String password)
    {
        mAuthenticationResponseLiveData = mUserRepository.reauthenticateUser(utente, email, password);

        return mAuthenticationResponseLiveData;
    }

    public MutableLiveData<FirebaseResponse> updateEmail(String email)
    {
        mAuthenticationResponseLiveData = mUserRepository.updateEmail(email);

        return mAuthenticationResponseLiveData;
    }


    public void clear()
    {
        if(mAuthenticationResponseLiveData != null)
        {
            mAuthenticationResponseLiveData.postValue(null);
        }
    }
}

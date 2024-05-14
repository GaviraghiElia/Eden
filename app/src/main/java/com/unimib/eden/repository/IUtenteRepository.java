package com.unimib.eden.repository;

import androidx.lifecycle.MutableLiveData;

import com.unimib.eden.model.FirebaseResponse;
import com.unimib.eden.model.Utente;

public interface IUtenteRepository {
    MutableLiveData<FirebaseResponse> signInWithEmail(String email, String password);
    MutableLiveData<FirebaseResponse> createUserWithEmail(String email, String password);
    MutableLiveData<FirebaseResponse> reauthenticateUser(Utente utente, String email, String password);
    MutableLiveData<FirebaseResponse> updateEmail(String email);
    MutableLiveData<FirebaseResponse> resetPasswordLink(String email);
}

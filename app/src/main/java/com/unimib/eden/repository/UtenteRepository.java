package com.unimib.eden.repository;

import static com.unimib.eden.utils.Constants.FIREBASE_DATABASE_URL;

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

public class UtenteRepository implements IUtenteRepository{

    private final FirebaseAuth mAuth;
    private final Application mApplication;
    private final SharedPreferencesProvider mSharedPreferencesProvider;
    private final MutableLiveData<FirebaseResponse> mAuthenticationResponseLiveData;
    private FirebaseDatabase fDB;
    private DatabaseReference reference;

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

    @Override
    public MutableLiveData<FirebaseResponse> reauthenticateUser(Utente utente, String email, String password)
    {
        return null;
    }

    @Override
    public MutableLiveData<FirebaseResponse> updateEmail(String email)
    {
        return null;
    }

    @Override
    public MutableLiveData<FirebaseResponse> resetPasswordLink(String email)
    {
        return null;
    }
}

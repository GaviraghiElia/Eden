package com.unimib.eden.repository;

import android.app.Application;

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
 * Class to manage interaction with Firebase regarding user management
 */
public class UserRepository implements IUserRepository{

    private final FirebaseAuth mAuth;

    /**
     * Instance of Application.
     */
    private final Application mApplication;

    /**
     * SharedPreferences provider.
     */
    private final SharedPreferencesProvider mSharedPreferencesProvider;

    /**
     * LiveData for Firebase authentication response.
     */
    private final MutableLiveData<FirebaseResponse> mAuthenticationResponseLiveData;

    /**
     * Instance of FirebaseDatabase.
     */
    private final FirebaseDatabase fDB;

    /**
     * Reference to the Firebase database.
     */
    private final DatabaseReference reference;

    /**
     * Constructor for UserRepository.
     *
     * @param application the instance of Application
     */
    public UserRepository(Application application)
    {
        mAuth = FirebaseAuth.getInstance();
        fDB = FirebaseDatabase.getInstance();
        reference = fDB.getReference("users");
        mApplication = application;
        mAuthenticationResponseLiveData = new MutableLiveData<>();
        mSharedPreferencesProvider = new SharedPreferencesProvider(application);
    }

    /**
     * Method to sign in with email and password.
     *
     * @param email the user's email
     * @param password the user's password
     * @return LiveData with the Firebase response
     */
    @Override
    public MutableLiveData<FirebaseResponse> signInWithEmail(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(ContextCompat.getMainExecutor(mApplication), new OnCompleteListener<>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseResponse firebaseResponse = new FirebaseResponse();
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            firebaseResponse.setSuccess(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            firebaseResponse.setSuccess(false);
                            firebaseResponse.setMessage(task.getException().getMessage());
                        }

                        mAuthenticationResponseLiveData.postValue(firebaseResponse);
                    }
                });
        return mAuthenticationResponseLiveData;
    }

    /**
     * Method to create a user with email and password.
     *
     * @param email the user's email
     * @param password the user's password
     * @return LiveData with the Firebase response
     */
    @Override
    public MutableLiveData<FirebaseResponse> createUserWithEmail(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<>() {
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
     * Method to re-authenticate a user.
     *
     * @param user the user to re-authenticate
     * @param email the user's email
     * @param password the user's password
     * @return LiveData with the Firebase response
     */
    @Override
    public MutableLiveData<FirebaseResponse> reauthenticateUser(Utente user, String email, String password)
    {
        return null;
    }

    /**
     * Method to update a user's email.
     *
     * @param email the new email of the user
     * @return LiveData with the Firebase response
     */
    @Override
    public MutableLiveData<FirebaseResponse> updateEmail(String email)
    {
        return null;
    }

    /**
     * Method to send a password reset link.
     *
     * @param email the user's email
     * @return LiveData with the Firebase response
     */
    @Override
    public MutableLiveData<FirebaseResponse> resetPasswordLink(String email)
    {
        return null;
    }
}

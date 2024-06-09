package com.unimib.eden.repository;

import androidx.lifecycle.MutableLiveData;

import com.unimib.eden.model.FirebaseResponse;
import com.unimib.eden.model.Utente;

/**
 * Interface for user repository to handle user authentication and management operations with Firebase.
 */
public interface IUserRepository {

    /**
     * Signs in a user with email and password.
     *
     * @param email the user's email
     * @param password the user's password
     * @return LiveData containing the Firebase response
     */
    MutableLiveData<FirebaseResponse> signInWithEmail(String email, String password);

    /**
     * Creates a new user with email and password.
     *
     * @param email the user's email
     * @param password the user's password
     * @return LiveData containing the Firebase response
     */
    MutableLiveData<FirebaseResponse> createUserWithEmail(String email, String password);

    /**
     * Re-authenticates a user.
     *
     * @param user the user to re-authenticate
     * @param email the user's email
     * @param password the user's password
     * @return LiveData containing the Firebase response
     */
    MutableLiveData<FirebaseResponse> reauthenticateUser(Utente user, String email, String password);

    /**
     * Updates a user's email.
     *
     * @param email the new email of the user
     * @return LiveData containing the Firebase response
     */
    MutableLiveData<FirebaseResponse> updateEmail(String email);

    /**
     * Sends a password reset link to the user's email.
     *
     * @param email the user's email
     * @return LiveData containing the Firebase response
     */
    MutableLiveData<FirebaseResponse> resetPasswordLink(String email);
}

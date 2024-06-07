package com.unimib.eden.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.unimib.eden.R;
import com.unimib.eden.databinding.FragmentLoginBinding;
import com.unimib.eden.ui.main.MainActivity;

/**
 * Manages the login process for the application.
 * <p>
 * This class extends the Fragment and overrides the lifecycle methods
 * to handle the creation, start, and view creation for the login fragment.
 */
public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    private NavController navController;

    private FragmentLoginBinding mBinding;
    private UserViewModel mUtenteViewModel;
    private String email;
    private String password;

    /**
     * Called when the fragment is created.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state,
     *                           this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUtenteViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    /**
     * Called when the fragment is visible to the user.
     */
    @Override
    public void onStart() {
        super.onStart();
        if(!checkSession())
        {
            Log.d("mAuth", "login fragment - user not signed");
        }else
        {
            Log.d("mAuth", "login fragment - user auth");
            startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
        }
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to. The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from
     *                           a previous saved state as given here.
     * @return The View for the fragment's UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        navController = NavHostFragment.findNavController(this);
        mBinding.loginEmail.addTextChangedListener(loginTextWatcher);
        mBinding.loginPassword.addTextChangedListener(loginTextWatcher);
        mAuth = FirebaseAuth.getInstance();


        mBinding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mBinding.loginEmail.getText().toString();
                password = mBinding.loginPassword.getText().toString();

                if (email.isEmpty()) {
                    mBinding.loginEmail.setError(getString(R.string.email_not_empty));
                    mBinding.loginEmail.requestFocus();
                } else if (!isValidEmail(email)) {
                    mBinding.loginEmail.setError(getString(R.string.    bad_email));
                    mBinding.loginEmail.requestFocus();
                } else if (password.isEmpty()) {
                    mBinding.loginPassword.setError(getString(R.string.password_not_empty));
                    mBinding.loginPassword.requestFocus();
                } else {
                    mUtenteViewModel.signInWithEmail(email, password).observe(getViewLifecycleOwner(), firebaseResponse -> {
                        if (firebaseResponse != null) {
                            if (firebaseResponse.isSuccess()) {
                                NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_mainActivity);
                            } else
                                makeMessage(firebaseResponse.getMessage());
                        }
                    });
                }
            }
        });

        mBinding.buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        return view;
    }


    /**
     * Handles the text change events for login input fields and enables/disables the login button accordingly.
     */
    private final TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String emailInput = mBinding.loginEmail.getText().toString();
            String passwordInput = mBinding.loginPassword.getText().toString();
            mBinding.buttonLogin.setEnabled(!emailInput.isEmpty() && !passwordInput.isEmpty() && isValidEmail(emailInput));
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    /**
     * Validates the given email address.
     *
     * @param email The email address to validate.
     * @return true if the email address is valid, false otherwise.
     */
    public boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Displays a message using a Toast and clears the user data in the ViewModel.
     *
     * @param message The message to be displayed.
     */
    private void makeMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        mUtenteViewModel.clear();
    }

    /**
     * Checks if the user is already authenticated.
     *
     * @return True if the user is authenticated, false otherwise.
     */

    private boolean checkSession()
    {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        return currentUser != null;
    }
}
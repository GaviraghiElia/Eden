package com.unimib.eden.ui.authentication;

import static com.unimib.eden.utils.Constants.EMAIL_PATTERN;
import static com.unimib.eden.utils.Constants.PASSWORD_PATTERN;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.unimib.eden.R;
import com.unimib.eden.databinding.FragmentRegisterBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegisterFragment class to handle the user registration UI.
 */
public class RegisterFragment extends Fragment
{
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase fDB;
    private NavController navController;
    public FragmentRegisterBinding mBinding;
    private UserViewModel mUserViewModel;

    /**
     * This class handles the registration fragment.
     */
    public RegisterFragment() {}

    /**
     * Method called when the Fragment is created.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mUserViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    /**
     * Method to create the view of the Fragment.
     *
     * @param inflater the layout inflater
     * @param container the view group container
     * @param savedInstanceState the saved instance state
     * @return the created view
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mBinding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        navController = NavHostFragment.findNavController(this);

        // TextWatcher to enable the register button
        mBinding.registerEmail.addTextChangedListener(registerTextWatcher);
        mBinding.registerPassword.addTextChangedListener(registerTextWatcher);

        firebaseAuth = FirebaseAuth.getInstance();
        fDB = FirebaseDatabase.getInstance();

        mBinding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = mBinding.registerEmail.getText().toString();
                String password = mBinding.registerPassword.getText().toString();
                String response = isValidCredential(email, password);
                if(response.equals("success"))
                {
                    mUserViewModel.clear();
                    createUserWithEmailAndPassword(email, password);
                } else {
                    if(response.equals(getString(R.string.bad_email)))
                    {
                        mBinding.registerEmail.setError(getString(R.string.bad_email));
                        mBinding.registerEmail.requestFocus();
                    }
                    else
                    {
                        mBinding.registerPassword.setError(getString(R.string.incorrect_password));
                        Toast.makeText(getContext(), getString(R.string.password_requirements), Toast.LENGTH_LONG).show();
                        mBinding.registerPassword.requestFocus();
                    }
                }
            }
        });

        mBinding.buttonSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

        return view;
    }

    /**
     * TextWatcher to enable the register button when email and password fields are filled.
     */
    private final TextWatcher registerTextWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            String emailInput = mBinding.registerEmail.getText().toString();
            String passwordInput = mBinding.registerPassword.getText().toString();
            mBinding.buttonRegister.setEnabled((!emailInput.isEmpty()) && (!passwordInput.isEmpty()) && isValidEmail(emailInput) && isValidPassword(passwordInput));
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    /**
     * Method to create a user with email and password.
     *
     * @param email the email provided for registration
     * @param password the password provided for registration
     */
    public void createUserWithEmailAndPassword(String email, String password)
    {
        mUserViewModel.createUserWithEmail(email, password).observe(getViewLifecycleOwner(), firebaseResponse -> {
            if (firebaseResponse != null)
            {
                if (firebaseResponse.isSuccess())
                {
                    makeMessage(getString(R.string.successful_registration));
                    firebaseAuth = FirebaseAuth.getInstance();
                    navController.navigate(R.id.action_registerFragment_to_mainActivity);
                    requireActivity().finish();
                    // startActivity(new Intent(requireContext(), MainActivity.class));
                }
                else
                {
                    makeMessage(firebaseResponse.getMessage());
                }
            }
        });
    }

    /**
     * Method to validate the provided credentials.
     *
     * @param email the email to validate
     * @param password the password to validate
     * @return "success" if both are valid, "bad email" or "incorrect password" if either is invalid
     */
    public String isValidCredential(String email, String password)
    {
        if(!isValidEmail(email))
        {
            return getString(R.string.bad_email);
        }
        else if(!isValidPassword(password))
        {
            return getString(R.string.incorrect_password);
        }

        return "success";
    }

    /**
     * Method to check if a password is valid.
     *
     * @param password the password to check
     * @return true if the password matches the password pattern, false otherwise
     */
    public boolean isValidPassword(String password)
    {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * Method to check if an email is valid.
     *
     * @param email the email to check
     * @return true if the email matches the email pattern, false otherwise
     */
    public boolean isValidEmail(String email)
    {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Method to display a message.
     *
     * @param message the message to display
     */
    private void makeMessage(String message)
    {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        mUserViewModel.clear();
    }
}

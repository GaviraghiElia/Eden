package com.unimib.eden.ui.authentication;

import static com.unimib.eden.utils.Constants.EMAIL_PATTERN;
import static com.unimib.eden.utils.Constants.PASSWORD_PATTERN;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.unimib.eden.R;
import com.unimib.eden.databinding.FragmentRegisterBinding;
import com.unimib.eden.model.FirebaseResponse;

public class RegisterFragment extends Fragment
{
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase fDB;
    private NavController navController;
    public FragmentRegisterBinding mBinding;
    private UtenteViewModel mUserViewModel;

    public RegisterFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mUserViewModel = new ViewModelProvider(requireActivity()).get(UtenteViewModel.class);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mBinding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        navController = NavHostFragment.findNavController(this);
        backButtonPressed(view);

        // Text Watcher per abilitare il bottone di registrazione
        mBinding.registerName.addTextChangedListener(loginTextWatcher);
        mBinding.registerEmail.addTextChangedListener(loginTextWatcher);
        mBinding.registerPassword.addTextChangedListener(loginTextWatcher);

        firebaseAuth = FirebaseAuth.getInstance();
        fDB = FirebaseDatabase.getInstance();

        mBinding.buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String name = mBinding.registerName.getText().toString();
                String email = mBinding.registerEmail.getText().toString();
                String password = mBinding.registerPassword.getText().toString();
                String response = isValidCredential(name, email, password);
                if(response.equals("success"))
                {
                    mUserViewModel.clear();
                    createUserWithEmailAndPassword(email, password);
                }else{
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
                Log.d("mAuthRegister", "onclick register");
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

        return view;
    }

    // Watcher of text change
    private TextWatcher loginTextWatcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            String nameInput = mBinding.registerName.getText().toString();
            String emailInput = mBinding.registerEmail.getText().toString();
            String passwordInput = mBinding.registerPassword.getText().toString();
            mBinding.buttonRegister.setEnabled((!nameInput.isEmpty()) && (!emailInput.isEmpty()) && (!passwordInput.isEmpty()) && isValidEmail(emailInput));
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    public void createUserWithEmailAndPassword(String email, String password)
    {
        mUserViewModel.createUserWithEmail(email, password).observe(getViewLifecycleOwner(), firebaseResponse -> {
            if (firebaseResponse != null)
            {
                if (firebaseResponse.isSuccess())
                {
                    makeMessage(getString(R.string.successfull_registration));
                    Log.d("mAuthRegister", "registrazione effettuata");
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
                    navController.navigate(R.id.action_registerFragment_to_loginFragment);
                }
                else
                {
                    makeMessage(firebaseResponse.getMessage());
                    Log.d("mAuthRegister", "registrazione fallita");
                }
            }
        });
    }

    public String isValidCredential(String name, String email, String password)
    {
        if(!isValidEmail(email))
        {
            return getString(R.string.bad_email);
        }
        else
        if(!isValidPassword(password))
        {
            return getString(R.string.incorrect_password);
        }

        return "success";
    }

    // Check pattern password
    public boolean isValidPassword(String password)
    {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    // Check pattern email
    public boolean isValidEmail(String email)
    {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void makeMessage(String message)
    {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        mUserViewModel.clear();
    }

    public void backButtonPressed(View view)
    {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                {
                    navController.navigate(R.id.action_registerFragment_to_loginFragment);

                    return true;
                }

                return false;
            }
        });
    }
}
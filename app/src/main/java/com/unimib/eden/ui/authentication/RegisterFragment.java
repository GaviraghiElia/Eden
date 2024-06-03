package com.unimib.eden.ui.authentication;

import static com.unimib.eden.utils.Constants.EMAIL_PATTERN;
import static com.unimib.eden.utils.Constants.PASSWORD_PATTERN;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.unimib.eden.R;
import com.unimib.eden.databinding.FragmentRegisterBinding;
import com.unimib.eden.ui.main.MainActivity;

/*
 * RegisterFrament Classe per gestire la UI della registrazione utente
 *
 * @author Gaviraghi Elia
 * @version 1.0
 */
public class RegisterFragment extends Fragment
{
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase fDB;
    private NavController navController;
    public FragmentRegisterBinding mBinding;
    private UtenteViewModel mUserViewModel;

    /**
     * Questa classe gestisce il fragment di registrazione
     *
     * @author Gaviraghi Elia
     */
    public RegisterFragment() {}

    /**
     * Metodo chiamato alla creazione del Fragment.
     *
     * @param savedInstanceState lo stato salvato dell'istanza
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mUserViewModel = new ViewModelProvider(requireActivity()).get(UtenteViewModel.class);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    /**
     * Metodo per creare la vista del Fragment.
     *
     * @param inflater l'Inflater per il layout
     * @param container il contenitore per il gruppo di viste
     * @param savedInstanceState lo stato salvato dell'istanza
     * @return la vista creata
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mBinding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        navController = NavHostFragment.findNavController(this);

        // Text Watcher per abilitare il bottone di registrazione
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
                navController.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });

        return view;
    }

    /**
     * TextWatcher per abilitare il tasto di login quando i campi email e password vengono riempiti
     */
    private TextWatcher registerTextWatcher = new TextWatcher()
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
     * Metodo per la creazione di un utente con email e password
     *
     * @param email la email fornita per la registrazione
     * @param password la password inserita per la registrazione
     *
     */
    public void createUserWithEmailAndPassword(String email, String password)
    {
        mUserViewModel.createUserWithEmail(email, password).observe(getViewLifecycleOwner(), firebaseResponse -> {
            if (firebaseResponse != null)
            {
                if (firebaseResponse.isSuccess())
                {
                    makeMessage(getString(R.string.successfull_registration));
                    firebaseAuth = FirebaseAuth.getInstance();
                    navController.navigate(R.id.action_registerFragment_to_mainActivity);
                    requireActivity().finish();
                    //startActivity(new Intent(requireContext(), MainActivity.class));
                }
                else
                {
                    makeMessage(firebaseResponse.getMessage());
                }
            }
        });
    }


    /**
     * Metodo per la validazione delle credenziali inserite
     *
     * @param email la email da verificare
     * @param password la password da verificare
     * @return stringa "success" se ha successo, "email non valida" oppure "password errata" se almeno una delle due non è valida
     */
    public String isValidCredential(String email, String password)
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

    /**
     * Metodo per verificare se una password è valida.
     *
     * @param password la password da verificare
     * @return true se la password rispetta il pattern password, false altrimenti
     */
    public boolean isValidPassword(String password)
    {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * Metodo per verificare se un'email è valida.
     *
     * @param email l'email da verificare
     * @return true se l'email rispetta il pattern email, false altrimenti
     */
    public boolean isValidEmail(String email)
    {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Metodo per mostrare un messaggio.
     *
     * @param message il messaggio da mostrare
     */
    private void makeMessage(String message)
    {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        mUserViewModel.clear();
    }
}
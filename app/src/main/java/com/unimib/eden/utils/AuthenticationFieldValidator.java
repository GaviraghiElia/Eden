package com.unimib.eden.utils;

import static com.unimib.eden.utils.Constants.EMAIL_PATTERN;
import static com.unimib.eden.utils.Constants.PASSWORD_PATTERN;

import android.content.res.Resources;

import com.unimib.eden.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationFieldValidator {

    public AuthenticationFieldValidator() {}

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
            return "Email non valida";
        }
        else
        if(!isValidPassword(password))
        {
            return "Password errata";
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
}

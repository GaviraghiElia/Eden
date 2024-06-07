package com.unimib.eden.utils;

import static com.unimib.eden.utils.Constants.EMAIL_PATTERN;
import static com.unimib.eden.utils.Constants.PASSWORD_PATTERN;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * AuthenticationFieldValidator class for validating text fields for authentication in the application
 *
 * @author Elia Gaviraghi
 */
public class AuthenticationFieldValidator {

    public AuthenticationFieldValidator() {}

    /**
     * Method for validating the entered credentials
     *
     * @param email the email to be verified
     * @param password the password to be verified
     * @return string "success" if successful, "invalid email" or "incorrect password" if either one is invalid
     */
    public String isValidCredential(String email, String password)
    {
        if(!isValidEmail(email))
        {
            return "Invalid email";
        }
        else if(!isValidPassword(password))
        {
            return "Incorrect password";
        }

        return "success";
    }

    /**
     * Method to check if a password is valid.
     *
     * @param password the password to be verified
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
     * @param email the email to be verified
     * @return true if the email matches the email pattern, false otherwise
     */
    public boolean isValidEmail(String email)
    {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

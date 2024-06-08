package com.unimib.eden.model;

/**
 * Classe FirebaseResponse che rappresenta una risposta da Firebase.
 */
public class FirebaseResponse
{
    /**
     * Indica se l'operazione è stata eseguita con successo.
     */
    private boolean success;

    /**
     * Il messaggio di risposta.
     */
    private String message;

    /**
     * Costruttore vuoto per FirebaseResponse.
     */
    public FirebaseResponse() {}

    /**
     * Restituisce il successo dell'operazione.
     *
     * @return true se l'operazione è stata eseguita con successo, false altrimenti
     */
    public boolean isSuccess()
    {
        return success;
    }

    /**
     * Imposta il successo dell'operazione.
     *
     * @param success il nuovo stato dell'operazione
     */
    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    /**
     * Restituisce il messaggio di risposta.
     *
     * @return il messaggio di risposta
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Imposta il messaggio di risposta.
     *
     * @param message il nuovo messaggio di risposta
     */
    public void setMessage(String message)
    {
        this.message = message;
    }
}
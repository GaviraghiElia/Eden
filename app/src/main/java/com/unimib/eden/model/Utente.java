package com.unimib.eden.model;

/**
 * Classe per modellare gli utenti
 *
 * @author Gaviraghi Elia
 */
public class Utente {
    /**
     * Il nome dell'utente.
     */
    private String nome;

    /**
     * L'email dell'utente.
     */
    private String email;

    /**
     * L'ID dell'utente.
     */
    private String id;

    /**
     * Restituisce il nome dell'utente.
     *
     * @return il nome dell'utente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il nome dell'utente.
     *
     * @param nome il nuovo nome dell'utente
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce l'email dell'utente.
     *
     * @return l'email dell'utente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Imposta l'email dell'utente.
     *
     * @param email la nuova email dell'utente
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Restituisce l'ID dell'utente.
     *
     * @return l'ID dell'utente
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta l'ID dell'utente.
     *
     * @param id il nuovo ID dell'utente
     */
    public void setId(String id) {
        this.id = id;
    }
}

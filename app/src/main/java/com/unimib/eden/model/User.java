package com.unimib.eden.model;

/**
 * Class to model users.
 */
public class User {
    /**
     * The user's name.
     */
    private String name;

    /**
     * The user's email.
     */
    private String email;

    /**
     * The user's ID.
     */
    private String id;

    /**
     * Returns the user's name.
     *
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     *
     * @param name the new user's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the user's email.
     *
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email.
     *
     * @param email the new user's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the user's ID.
     *
     * @return the user's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the user's ID.
     *
     * @param id the new user's ID
     */
    public void setId(String id) {
        this.id = id;
    }
}

package com.unimib.eden.model;

/**
 * FirebaseResponse class representing a response from Firebase.
 */
public class FirebaseResponse {
    /**
     * Indicates whether the operation was successful.
     */
    private boolean success;

    /**
     * The response message.
     */
    private String message;

    /**
     * Empty constructor for FirebaseResponse.
     */
    public FirebaseResponse() {}

    /**
     * Returns the success of the operation.
     *
     * @return true if the operation was successful, false otherwise
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success of the operation.
     *
     * @param success the new state of the operation
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Returns the response message.
     *
     * @return the response message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the response message.
     *
     * @param message the new response message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}

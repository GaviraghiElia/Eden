package com.unimib.eden.utils;

/**
 * Utility class containing an enum for proposal status.
 */
public class Enum {

    /**
     * Enum representing the status of a proposal.
     */
    public enum StatoProposta {
        /**
         * The proposal has been viewed.
         */
        VIEWED,

        /**
         * The proposal is in negotiation.
         */
        IN_NEGOTIATION,

        /**
         * The proposal has been rejected.
         */
        REJECTED,

        /**
         * The proposal has been accepted.
         */
        ACCEPTED,

        /**
         * The proposal has been concluded.
         */
        CONCLUDED
    }
}

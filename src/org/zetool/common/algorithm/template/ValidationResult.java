/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.zetool.common.algorithm.template;

/**
 * A wrapper class the contains the results of a validation. This consists of a Boolean value whether a new value
 * has been accepted and a String storing an error message in case something went wrong.
 */
public class ValidationResult {

    /**
     * A constant representing a successful validation.
     */
    public static final ValidationResult SUCCESS = new ValidationResult(true, "");
    /**
     * A constant representing a failed validation.
     */
    public static final ValidationResult FAILURE = new ValidationResult(false, "");
    /**
     * The error message of the validation.
     */
    private String message;
    /**
     * Whether the validation was successful.
     */
    private boolean successful;

    /**
     * Creates a new ValidationResult with the specified error message and success flag.
     *
     * @param successful whether the validation was successful.
     * @param message the error message of this validation result.
     */
    public ValidationResult(boolean successful, String message) {
        this.message = message;
        this.successful = successful;
    }

    /**
     * Combines this result with the given one. This concatenates the messages and performs a logical AND of their
     * success flags. The result is then stored in this object.
     *
     * @param result the result this result is to be combined with.
     */
    public void combine(ValidationResult result) {
        message = (message + " " + result.getMessage()).trim();
        successful = successful && result.isSuccessful();
    }

    /**
     * Returns the error message of this validation result. This is an empty string if the validation was
     * successful.
     *
     *
     * @return the error message of this validation result.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message of this validation result. Should be empty if and only if the validation was a
     * success.
     *
     * @param message the error message of this validation result.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns whether the validation was successful.
     *
     * @return whether the validation was successful.
     */
    public boolean isSuccessful() {
        return successful;
    }

    /**
     * Sets whether the validation was successful.
     *
     * @param successful specifies whether the validation was successful.
     */
    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
    
}

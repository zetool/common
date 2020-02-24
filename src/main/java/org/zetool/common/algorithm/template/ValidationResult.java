package org.zetool.common.algorithm.template;

/**
 * A wrapper class the contains the results of a validation. This consists of a Boolean value whether a new value
 * has been accepted and a String storing an error message in case something went wrong.
 */
public class ValidationResult {

    /** A constant representing a successful validation. */
    public static final ValidationResult SUCCESS = new ValidationResult(true, "") {
        @Override
        public String toString() {
            return "SUCCESS";
        }
    };
    /** A constant representing a failed validation. */
    public static final ValidationResult FAILURE = new ValidationResult(false, "") {
        @Override
        public String toString() {
            return "FAILURE";
        }
    };

    /** The error message of the validation. */
    private final String message;
    /** Whether the validation was successful. */
    private final boolean successful;

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
     * success flags. The result is then stored in a new object.
     *
     * @param result the result this result is to be combined with.
     * @return the new validation result
     */
    public ValidationResult combine(ValidationResult result) {
        String newMessage = (message + " " + result.getMessage()).trim();
        boolean newSuccessful = successful && result.isSuccessful();
        return new ValidationResult(newSuccessful, newMessage);
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
     * Returns whether the validation was successful.
     *
     * @return whether the validation was successful.
     */
    public boolean isSuccessful() {
        return successful;
    }
}

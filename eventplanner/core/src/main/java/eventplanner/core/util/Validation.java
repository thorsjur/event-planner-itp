package eventplanner.core.util;

/**
 * Static validation methods to be used for validating input.
 */
public class Validation {

    private Validation() {
        throw new IllegalStateException("You cannot instantiate an utility class!");
    }

    public static final String EMAIL_REGEX = 
            "^(([A-Za-z\\d]|[\\.\\-_\\+][A-Za-z\\d]){1,100}@[A-Za-z\\d]{1,100}(\\.[A-Za-z]{2,10}){1,2})$";
    public static final int MIN_PASSWORD_LENGTH = 5;
    public static final int MAX_PASSWORD_LENGTH = 100;

    /**
     * Static method for validating an email address using the class' regex pattern.
     * 
     * @param email the email to be validated
     * @return true if valid, false else
     */
    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    /**
     * Static method for validating a password using the class' regex pattern.
     * 
     * @param password the password to be validated
     * @return true if valid, false else
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH && password.length() <= MAX_PASSWORD_LENGTH;
    }
}

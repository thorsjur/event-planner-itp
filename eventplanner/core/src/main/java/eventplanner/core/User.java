package eventplanner.core;

import eventplanner.core.util.Validation;

/**
 * A User data record that represents a user.
 * The data is unmodifiable, and can be retrieved with
 * standard getters.
 */
public record User(String email, String password, boolean above18) {

    /**
     * Takes a username that is not null and not blank.
     * 
     * @param email    the username of the user
     * @param password the password of the user
     * @param above18  user above 18 (true) or not (false)
     */
    public User(String email, String password, boolean above18) {

        if (!Validation.isValidEmail(email)) {
            throw new IllegalArgumentException("Email is invalid");
        }
        if (!Validation.isValidPassword(password)) {
            throw new IllegalArgumentException("Password is invalid");
        }

        this.email = email;
        this.password = password;
        this.above18 = above18;
    }
}
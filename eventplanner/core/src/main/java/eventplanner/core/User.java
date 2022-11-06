package eventplanner.core;

import eventplanner.core.util.Validation;

/**
 * A User data record that represents a user. The data is unmodifiable, and can
 * be retrieved with getters.
 */
public record User(String email, String password, boolean above18) {

    /**
     * Constructor for instantiating a new user object.
     * 
     * @param email    the username of the user
     * @param password the password of the user
     * @param above18  is user above 18 (true) or not (false)
     */
    public User(String email, String password, boolean above18) {

        if (!Validation.isValidEmail(email)) {
            throw new IllegalArgumentException("The provided email is not valid");
        }
        if (!Validation.isValidPassword(password)) {
            throw new IllegalArgumentException("The provided password is not valid");
        }

        this.email = email;
        this.password = password;
        this.above18 = above18;
    }
}
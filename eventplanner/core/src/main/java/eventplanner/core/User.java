package eventplanner.core;

/**
 * A User data record that represents a user.
 * The data is unmodifiable, and can be retrieved with
 * standard getters.
 */
public record User(String username) {

    /**
     * Takes a username that is not null and not blank.
     * 
     * @param username  the username of the user
     */
    public User(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("User name is null or blank");
        }
        this.username = username;
    }
}
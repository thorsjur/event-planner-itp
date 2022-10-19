package eventplanner.core;

/**
 * A User data record that represents a user.
 * The data is unmodifiable, and can be retrieved with
 * standard getters.
 */
public record User(String email, String password, Boolean above18) {

    /**
     * Takes a username that is not null and not blank.
     * 
     * @param email     the username of the user
     * @param password  the password of the user
     * @param above18   user above 18 (true) or not (false)
     */
    public User(String email, String password, Boolean above18) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is null or blank");
        } else if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is null or blank");
        } else if (above18 == null) {
            throw new IllegalArgumentException("above18 is null");
        }

        this.email = email;
        this.password = passwordHash(password);
        this.above18 = above18;
    }

    private String passwordHash(String password) {
        return password;
    }

    //Getters.
    public String getEmail() {
        return this.email;
    }
    public String getPassword () {
        return this.password;
    }
    public boolean getAbove18() {
        return this.above18;
    }

    public static User findUser(String email) { //TODO - denne funksjonen skal finne og returne en user basert på email.
        return new User("test@test.test", "password", true);
    }
}
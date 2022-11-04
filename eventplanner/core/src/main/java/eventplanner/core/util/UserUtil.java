package eventplanner.core.util;

/**
 * Static utility methods used in User class.
 */
public class UserUtil {

    public static String passwordHash(String password) {
        return password + "h";
    }

    public static String deHash(String password) {
        return (password == null || password.isEmpty())
                ? null
                : (password.substring(0, password.length() - 1));
    }
}

package eventplanner.json.util;

/**
 * Class to provide basic encryption and decryption of password strings.
 * Note this is meant to serve as an example, and will not provide proper
 * security features.
 */
public class CryptoUtil {

    private CryptoUtil() {
        throw new IllegalStateException("You cannot instantiate an utility class!");
    }

    /**
     * 100 char offset key, varying offset depending on the index of the character.
     */
    private static final String OFFSET_KEY = "8122020840947072013953077" 
            + "4869870862690623818597470"
            + "2112402695046236643195067"
            + "5993573294179658112592316";

    /**
     * Encrypts the given password string using a modified Vignère cipher, also known
     * as a Vignère shift. Provides only an encryption example, and is not expected
     * to be secure.
     * 
     * @param password password to be encrypted
     * @return the encrypted password
     */
    public static String encrypt(String password) {
        validatePassword(password);
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            sb.appendCodePoint(password.charAt(i) + (OFFSET_KEY.charAt(i * 2 + 1) - '0'));
        }
        return sb.toString();
    }

    /**
     * Decrypts the given encrypted string. Can only decrypt passwords encrypted
     * with the encryption method provided in this class.
     * 
     * @param password password to be encrypted
     * @return the encrypted password
     */
    public static String decrypt(String password) {
        validatePassword(password);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            sb.appendCodePoint(password.charAt(i) - (OFFSET_KEY.charAt(i * 2 + 1) - '0'));
        }
        return sb.toString();
    }

    private static void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Cannot encrypt empty or null password ...");
        }
    }
}

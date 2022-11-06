package eventplanner.json.util;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CryptoUtilTest {
    
    @ParameterizedTest
    @NullAndEmptySource
    void testEncrypt_throwsExceptionOnNullOrEmpty(String password) {
        assertThrows(IllegalArgumentException.class, () -> CryptoUtil.encrypt(password));
    }

    @ParameterizedTest
    @ValueSource(strings = { "password", " ", "test", "\n", "'--.", "@@" })
    void testEncrypt_doesNotReturnUnchangedString(String password) {
        assertNotEquals(password, CryptoUtil.encrypt(password));
    }
    
    @ParameterizedTest
    @NullAndEmptySource
    void testDecrypt_throwsExceptionOnNullOrEmpty(String password) {
        assertThrows(IllegalArgumentException.class, () -> CryptoUtil.decrypt(password));
    }
    
    @ParameterizedTest
    @ValueSource(strings = { "keyword", "   fr", "10=)\"&%(", "\n", "'--.", "@@", "\t", "awyd(%&))(/hhY/44" })
    void testEncryptAndDecrypt_returnSamePasswordAfterDecryption(String password) {
        String encrypted = CryptoUtil.encrypt(password);
        String decrypted = CryptoUtil.decrypt(encrypted);
        assertEquals(password, decrypted);
    }
}

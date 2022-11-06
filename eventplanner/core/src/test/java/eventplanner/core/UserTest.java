package eventplanner.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class UserTest {

    @Test
    void testConstructor_setsValidValues() {
        User user = new User("User1@test.test", "password", true);
        assertEquals("User1@test.test", user.email());
        assertEquals("password", user.password());

        assertNotEquals("user1@test.test", user.email());
    }

    @ParameterizedTest
    @NullSource
    void testConstructor_throwsIllegalArgumentExceptionOnNullInput(String input) {
        assertThrows(IllegalArgumentException.class, () -> new User(input, "password", false));
        assertThrows(IllegalArgumentException.class, () -> new User("valid@email.com", input, true));
    }

    @ParameterizedTest
    @ValueSource(strings = { "invalid_email", "test_@test.com", "", "\n", " "})
    void testConstructor_throwsIllegalArgumentExceptionOnInvalidEmail(String email) {
        assertThrows(IllegalArgumentException.class, () -> new User(email, "password", true));
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "\n", " ", "abcd", "999"})
    void testConstructor_throwsIllegalArgumentExceptionOnInvalidPassword(String password) {
        assertThrows(IllegalArgumentException.class, () -> new User("valid@email.com", password, true));
    }

}

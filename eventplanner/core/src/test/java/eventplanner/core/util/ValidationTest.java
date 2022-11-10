package eventplanner.core.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

class ValidationTest {

    private static final int MIN_PASSWORD_LENGTH = 5;
    private static final int MAX_PASSWORD_LENGTH = 100;

    @ParameterizedTest
    @MethodSource("provideValidEmails")
    void testIsValidEmail_returnsTrueOnValidInput(String email) {
        assertTrue(Validation.isValidEmail(email));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidEmails")
    void testIsValidEmail_returnsFalseOnInvalidInput(String email) {
        assertFalse(Validation.isValidEmail(email));
    }

    @ParameterizedTest
    @NullSource
    void testIsValidPassword_returnsFalseOnNullInput(String password) {
        assertFalse(Validation.isValidPassword(password));
    }

    @ParameterizedTest
    @MethodSource("provideValidPasswords")
    void testIsValidPassword_returnsTrueOnValidInput(String password) {
        assertTrue(Validation.isValidPassword(password));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPasswords")
    void testIsValidPassword_returnsFalseOnInvalidInput(String password) {
        assertFalse(Validation.isValidPassword(password));
    }

    private static Stream<String> provideValidEmails() {
        return Arrays.stream(new String[] {
            "test@example.com",
            "example@business.co.uk",
            "test.user@example2.com",
            "a2@b2.com",
            "test-example-user@n.no",
            "ex4mp7e@em41l.ddddw",
            ".very_valid-email@example.co.com",
            "_w.y-e_d@ex2.co",
            "disposable.email+example@test.example",
            "a@b.co"
        });
    }

    private static Stream<String> provideInvalidEmails() {
        return Arrays.stream(new String[] {
            "test_@example.com",
            "example+@business.co.uk",
            "test.user@example2.com2",
            "a2@b2.co2m",
            "test-example-user@n.overtenchars",
            "ex4mp7e@em41l.ddd_dw",
            "very_invalid-email@@example.co.com",
            "w.y-e_d@ex2.co.uk.baloo",
            "",
            "@",
            "a@b.c"
        });
    }

    private static Stream<String> provideValidPasswords() {
        Stream<String> validLoremIpsumStrings = getLorumIpsumStream(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH, 10);
        Stream<String> validStrings = Stream.of(new String[] {
                "@@£@£€@£€@£€@£$@£$€@£€@£",
                "´´ééá´p´d@@--24242",
                "aaaaa",
                "@£$€{[]{[[[\n\t",
                "password"
        });

        return Stream.concat(validLoremIpsumStrings, validStrings);
    }

    private static Stream<String> provideInvalidPasswords() {
        Stream<String> overMaxLengthStream = getLorumIpsumStream(MAX_PASSWORD_LENGTH + 1, 200, 10);
        Stream<String> underMinLengthStream = getLorumIpsumStream(0, MIN_PASSWORD_LENGTH, 10);

        return Stream.concat(overMaxLengthStream, underMinLengthStream);
    }

    private static Stream<String> getLorumIpsumStream(int minLength, int maxLength, int streamLimit) {
        Random random = new Random();
        final String testStr = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sodales non ligula "
                + "scelerisque elementum. Praesent ac condimentum neque, ut interdum massa. Nunc "
                + " eget urna eleifend, hendrerit ipsum vitae, venenatis leo. Nullam faucibus est"
                + " massa, vitae elementum elit facilisis sed. Pellentesque non facilisis magna. "
                + " Maecenas quis dignissim lorem. Integer auctor, sapien quis cursus ultrices, "
                + "elit magna porta turpis, id maximus velit lacus id dolor. Cras pretium iaculis"
                + " erat, hendrerit laoreet odio fringilla fringilla. Sed hendrerit quam feugiat "
                + "felis iaculis, eget accumsan neque fringilla.";
        return random.ints(minLength, maxLength).mapToObj(i -> {
            int start = random.nextInt(testStr.length() - i - 1);
            return testStr.substring(start, start + i);
        }).limit(streamLimit);
    }
}

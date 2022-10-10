package eventplanner.fxui.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidationTest {

    private static final int MIN_NAME_LENGTH = Validation.MIN_NAME_LENGTH;
    private static final int MIN_DESCRIPTION_LENGTH = Validation.MIN_DESCRIPTION_LENGTH;
    private static final int MIN_LOCATION_LENGTH = Validation.MIN_LOCATION_LENGTH;

    private static final int NUMBER_OF_STRINGS = 20;
    
    @ParameterizedTest
    @NullSource
    public void testIsValidTextInput_throwsOnNullInput(String input) {
        assertThrows(IllegalArgumentException.class, () -> Validation.isValidTextInput(input, InputType.NAME));
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "123", "..//\n\n", "</b>" })
    public void testIsValidTextInput_descShouldAcceptsAllStringInputs(String input) {
        assertTrue(Validation.isValidTextInput(input, InputType.DESCRIPION));
    }

    @ParameterizedTest
    @MethodSource("provideValidLocationStrings")
    public void testIsValidTextInput_locationReturnsTrueOnStringsOverLimit(String input) {
        assertTrue(Validation.isValidTextInput(input, InputType.LOCATION));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidLocationStrings")
    public void testIsValidTextInput_locationReturnsFalseOnStringsUnderLimit(String input) {
        assertFalse(Validation.isValidTextInput(input, InputType.LOCATION));
    }

    @ParameterizedTest
    @MethodSource("provideValidNameStrings")
    public void testIsValidTextInput_nameReturnsTrueOnStringsOverLimit(String input) {
        assertTrue(Validation.isValidTextInput(input, InputType.NAME));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidNameStrings")
    public void testIsValidTextInput_nameReturnsFalseOnStringsUnderLimit(String input) {
        assertFalse(Validation.isValidTextInput(input, InputType.NAME));
    }

    @ParameterizedTest
    @ValueSource(strings = { "23:59", "00:00", "12:34", "17:19", "18:18", "01:02" })
    public void testIsValidTextInput_returnsTrueOnValidTimeStrings(String input) {
        assertTrue(Validation.isValidTextInput(input, InputType.TIME));
    }

    @ParameterizedTest
    @ValueSource(strings = { "12:60", "00;00", "24:00", "31:00", "59:12", ":", "1:2", "233:2", "ac:dc" })
    public void testIsValidTextInput_returnsFalseOnInvalidTimeStrings(String input) {
        assertFalse(Validation.isValidTextInput(input, InputType.TIME));
    }

    @Test
    public void isStartBeforeEnd_shouldReturnFalse() {
        LocalDate start = LocalDate.of(2020, 9, 15);
        LocalDate end = LocalDate.of(2020, 9, 15);
        String startTime = "12:24";
        String endTime = "12:23";
        assertFalse(Validation.isStartBeforeEnd(start, startTime, end, endTime));

        LocalDate start2 = LocalDate.of(2024, 11, 12);
        LocalDate end2 = LocalDate.of(2024, 10, 17);
        String startTime2 = "12:24";
        String endTime2 = "13:59";
        assertFalse(Validation.isStartBeforeEnd(start2, startTime2, end2, endTime2));
    }

    @Test
    public void isStartBeforeEnd_shouldReturnTrue() {
        LocalDate start = LocalDate.of(2020, 8, 14);
        LocalDate end = LocalDate.of(2020, 8, 15);
        String startTime = "12:24";
        String endTime = "12:23";
        assertTrue(Validation.isStartBeforeEnd(start, startTime, end, endTime));

        LocalDate start2 = LocalDate.of(2023, 10, 17);
        LocalDate end2 = LocalDate.of(2023, 10, 17);
        String startTime2 = "12:24";
        String endTime2 = "13:59";
        assertTrue(Validation.isStartBeforeEnd(start2, startTime2, end2, endTime2));
    }


    private static Stream<String> provideValidLocationStrings() {
        return provideStrings(MIN_LOCATION_LENGTH, 100, NUMBER_OF_STRINGS);
    }

    private static Stream<String> provideInvalidLocationStrings() {
        return provideStrings(0, MIN_LOCATION_LENGTH - 1, NUMBER_OF_STRINGS);
    }

    private static Stream<String> provideValidNameStrings() {
        return provideStrings(MIN_NAME_LENGTH, 100, NUMBER_OF_STRINGS);
    }

    private static Stream<String> provideInvalidNameStrings() {
        return provideStrings(0, MIN_NAME_LENGTH - 1, NUMBER_OF_STRINGS);
    }

    private static Stream<String> provideStrings(int minLength, int maxLength, int n) {
        return Stream.generate(() -> {
            return getRandomString(minLength, maxLength);
        }).limit(n);
    }

    private static String getRandomString(int minLength, int maxLength) {
        Random random = new Random();
        int length = random.nextInt(minLength, maxLength + 1);

        return random.ints(0, 256)
            .limit(length)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

}

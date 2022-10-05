package eventplanner.fxui.util;

import java.time.LocalDate;
import java.util.stream.Stream;

import eventplanner.core.EventType;

public class Validation {

    private static final LocalDate EARLIEST_VALID_DATE = LocalDate.of(2000, 1, 1);
    private static final LocalDate LATEST_VALID_DATE = LocalDate.of(2030, 12, 30);

    public static boolean isValidTextInput(String input, InputType inputType) {
        switch (inputType) {
            case DESCRIPION:
                return isValidDescription(input);
            case LOCATION:
                return isValidLocation(input);
            case NAME:
                return isValidName(input);
            case TIME:
                return isValidTimeString(input);
            default:
                throw new IllegalArgumentException("Invalid input type ...");
        }
    }

    public static boolean isValidDateInput(LocalDate date) {
        return date != null
                && date.isAfter(EARLIEST_VALID_DATE)
                && date.isBefore(LATEST_VALID_DATE);
    }

    public static boolean areValidDateInputs(LocalDate startDate, LocalDate endDate) {
        return isValidDateInput(startDate)
                && isValidDateInput(endDate)
                && startDate.isBefore(endDate);

    }

    public static boolean isValidEventType(String comboBoxInput) {
        return Stream.of(EventType.values())
                .map(e -> e.toString())
                .anyMatch(e -> e.equals(comboBoxInput));
    }

    private static boolean isValidTimeString(String timeString) {
        if (timeString == null
                || timeString.length() != 5
                || !timeString.contains(":")) {
            return false;
        }

        String[] split = timeString.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);

        return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
    }

    private static boolean isValidName(String name) {
        return name.length() > 2;
    }

    private static boolean isValidLocation(String location) {
        return location.length() > 3;
    }

    private static boolean isValidDescription(String desc) {
        return true;
    }

}

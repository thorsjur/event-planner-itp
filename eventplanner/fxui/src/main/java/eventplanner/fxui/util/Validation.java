package eventplanner.fxui.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import eventplanner.core.EventType;

/**
 * A utility class for validating user inputs.
 * 
 * The class defines the requirements for valid inputs.
 */
public class Validation {

    public static final LocalDate EARLIEST_VALID_DATE = LocalDate.of(2000, 1, 1);
    public static final LocalDate LATEST_VALID_DATE = LocalDate.of(2030, 12, 30);
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MIN_DESCRIPTION_LENGTH = 0;
    public static final int MIN_LOCATION_LENGTH = 2;

    /**
     *  Error types based on invalid inputs, with their respective error messages
     *  saved in the public field err_message.
     */
    public enum ErrorType {
        INVALID_DATE("Invalid date entered, must be before " + LATEST_VALID_DATE.toString()
                + " and after " + EARLIEST_VALID_DATE.toString() + "."),
        INVALID_TIME("Invalid time entered, must be in format HH:MM."),
        INVALID_NAME("Name must have at least " + MIN_NAME_LENGTH + " characters."),
        INVALID_LOCATION("Location must have at least " + MIN_LOCATION_LENGTH + " characters."),
        INVALID_TYPE("No event type entered."),
        INVALID_TIME_RELATIONSHIP("Start time must be before end time.");

        public final String err_message;

        private ErrorType(String err_message) {
            this.err_message = err_message;
        }
    }

    /**
     * Returns the validity of the user input based on the given input type.
     * @param   input       a textual user input
     * @param   inputType   InputType specifying the type of input
     * @return              boolean value indicating validity of the input
     */
    public static boolean isValidTextInput(String input, InputType inputType) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null.");
        }
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

    /**
     * Uses local requirements to validate the input date.
     * @param   date    to be validated
     * @return          boolean value indicating validity of the date
     */
    public static boolean isValidDateInput(LocalDate date) {
        return date != null
                && date.isAfter(EARLIEST_VALID_DATE)
                && date.isBefore(LATEST_VALID_DATE);
    }

    /**
     * Convenience method for validating two dates.
     * @param   startDate   the start date
     * @param   endDate     the end date
     * @return              validity of both dates
     */
    public static boolean areValidDateInputs(LocalDate startDate, LocalDate endDate) {
        return isValidDateInput(startDate)
                && isValidDateInput(endDate);
    }
    
    /**
     * Returns the validity of the given string.
     * Returns true if the string is equal to a EventType,
     * else it returns false.
     * @param   comboBoxInput   the input from a comboBox containing EventTypes
     * @return                  validity of the input string
     */
    public static boolean isValidEventType(String comboBoxInput) {
        if (comboBoxInput == null) {
            return false;
        }
        return Stream.of(EventType.values())
                .map(e -> e.toString())
                .anyMatch(e -> e.equals(comboBoxInput));
    }

    public static boolean isStartBeforeEnd(LocalDate startDate, String startTime, LocalDate endDate, String endTime) {
        if (!isValidTimeString(startTime) || !isValidTimeString(endTime)) {
            return true;
        }
        if (!areValidDateInputs(startDate, endDate)) {
            return true;
        }
        int[] startTimeArr = getTimeArray(startTime);
        LocalDateTime startDateTime = startDate.atTime(startTimeArr[0], startTimeArr[1]);

        int[] endTimeArr = getTimeArray(endTime);
        LocalDateTime endDateTime = endDate.atTime(endTimeArr[0], endTimeArr[1]);

        return startDateTime.isBefore(endDateTime);
    }

    private static boolean isValidTimeString(String timeString) {
        if (timeString == null
                || timeString.length() != 5
                || !timeString.contains(":")) {
            return false;
        }

        int[] time = getTimeArray(timeString);
        int hour = time[0];
        int minute = time[1];

        return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
    }

    private static boolean isValidName(String name) {
        return name.length() >= MIN_NAME_LENGTH;
    }

    private static boolean isValidLocation(String location) {
        return location.length() >= MIN_LOCATION_LENGTH;
    }

    private static boolean isValidDescription(String desc) {
        return desc.length() >= MIN_DESCRIPTION_LENGTH;
    }

    private static int[] getTimeArray(String timeString) {
        String[] split = timeString.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        return new int[] { hour, minute };
    }

}

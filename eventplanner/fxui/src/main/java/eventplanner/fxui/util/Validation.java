package eventplanner.fxui.util;

import eventplanner.core.EventType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

/**
 * A utility class for validating user inputs.
 * 
 * <p>
 * The class defines the requirements for valid inputs.
 * </p>
 */
public class Validation {

    public static final LocalDate EARLIEST_VALID_DATE = LocalDate.of(2000, 1, 1);
    public static final LocalDate LATEST_VALID_DATE = LocalDate.of(2030, 12, 30);
    public static final LocalDate LATEST_VALID_BIRTH_DATE = LocalDate.now().minusYears(6);
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MIN_DESCRIPTION_LENGTH = 0;
    public static final int MIN_LOCATION_LENGTH = 2;
    public static final int MIN_PASSWORD_LENGTH = eventplanner.core.util.Validation.MIN_PASSWORD_LENGTH;
    public static final int MAX_PASSWORD_LENGTH = eventplanner.core.util.Validation.MAX_PASSWORD_LENGTH;

    private Validation() {
        throw new IllegalStateException("You cannot instantiate an utility class!");
    }

    /**
     * Error types based on invalid inputs, with their respective error messages
     * saved in the public field err_message.
     */
    public enum ErrorType {
        INVALID_DATE("Invalid date entered, must be before " + LATEST_VALID_DATE.toString()
                + " and after " + EARLIEST_VALID_DATE.toString() + "."),
        INVALID_TIME("Invalid time entered, must be in format HH:MM."),
        INVALID_NAME("Name must have at least " + MIN_NAME_LENGTH + " characters."),
        INVALID_LOCATION("Location must have at least " + MIN_LOCATION_LENGTH + " characters."),
        INVALID_TYPE("No event type entered."),
        INVALID_TIME_RELATIONSHIP("Start time must be before end time."),
        INVALID_EMAIL("Invalid email address entered."),
        INVALID_PASSWORD("Invalid password entered. Must be longer than "
                + MIN_PASSWORD_LENGTH + " characters, and shorter than "
                + MAX_PASSWORD_LENGTH + " characters."),
        INVALID_BIRTH_DATE("Invalid birth date. You have to be at least 6 years old.");

        public final String errMessage;

        private ErrorType(String errMessage) {
            this.errMessage = errMessage;
        }
    }

    /**
     * Returns the validity of the user input based on the given input type.
     * 
     * @param input     a textual user input
     * @param inputType InputType specifying the type of input
     * @return boolean value indicating validity of the input
     */
    public static boolean isValidTextInput(String input, InputType inputType) {
        if (input == null) {
            return false;
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
            case PASSWORD:
                return isValidPassword(input);
            case EMAIL:
                return isValidEmail(input);
            default:
                throw new IllegalArgumentException("Invalid input type ...");
        }
    }

    /**
     * Uses local requirements to validate the input date.
     * 
     * @param date  to be validated
     * @return      boolean value indicating validity of the date
     */
    public static boolean isValidDateInput(LocalDate date, InputType dateType) {
        if (!(dateType == InputType.DATE || dateType == InputType.BIRTH_DATE)) {
            throw new IllegalArgumentException("Not a date input type.");
        }
        return date != null
                && (dateType == InputType.BIRTH_DATE && (date.isBefore(LATEST_VALID_BIRTH_DATE))
                        || (dateType == InputType.DATE
                                && date.isBefore(LATEST_VALID_DATE)
                                && date.isAfter(EARLIEST_VALID_DATE)));
    }

    /**
     * Convenience method for validating two dates.
     * 
     * @param startDate the start date
     * @param endDate   the end date
     * @return validity of both dates
     */
    public static boolean areValidDateInputs(LocalDate startDate, LocalDate endDate) {
        return isValidDateInput(startDate, InputType.DATE)
                && isValidDateInput(endDate, InputType.DATE);
    }

    /**
     * Returns the validity of the given string.
     * Returns true if the string is equal to a EventType,
     * else it returns false.
     * 
     * @param comboBoxInput the input from a comboBox containing EventTypes
     * @return validity of the input string
     */
    public static boolean isValidEventType(String comboBoxInput) {
        if (comboBoxInput == null) {
            return false;
        }
        return Stream.of(EventType.values())
                .map(e -> e.toString())
                .anyMatch(e -> e.equals(comboBoxInput));
    }

    /**
     * Check if start date is before end date.
     * 
     * @param startDate start date
     * @param startTime start time
     * @param endDate   end date
     * @param endTime   end time
     * @return return
     */
    public static boolean isStartBeforeEnd(
            LocalDate startDate,
            String startTime,
            LocalDate endDate,
            String endTime) {
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
        if (timeString == null || !timeString.matches("^\\d{2}:\\d{2}$")) {
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

    private static boolean isValidEmail(String email) {
        return eventplanner.core.util.Validation.isValidEmail(email);
    }

    private static boolean isValidPassword(String password) {
        return eventplanner.core.util.Validation.isValidPassword(password);
    }

    private static int[] getTimeArray(String timeString) {
        String[] split = timeString.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        
        return new int[] { hour, minute };
    }
}

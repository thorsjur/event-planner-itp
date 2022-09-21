package eventplanner.util;

public class Validation {
    
    public static boolean isValidTimeString(String timeString) {
        if (timeString == null
            || timeString.length() != 5
            || !timeString.contains(":")) return false;

        String[] split = timeString.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);

        return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
    }
}

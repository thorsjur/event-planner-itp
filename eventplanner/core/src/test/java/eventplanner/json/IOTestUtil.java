package eventplanner.json;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import eventplanner.core.Event;
import eventplanner.core.EventType;
import eventplanner.core.User;

/**
 * Utility class with static methods to be used in IO testing.
 */
public class IOTestUtil {

    private IOTestUtil() {
        throw new IllegalStateException("This class cannot be instantiated!");
    }

    private static final Random RANDOM = new Random(10101);

    /**
     * Generates n pseudo-random events, to be used for testing purposes.
     * 
     * @param n the number of events to generate
     * @return a list of pseudo-random events
     */
    public static List<Event> getPseudoRandomEvents(int n) {
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            EventType type = List.of(EventType.values()).get(RANDOM.nextInt(4));
            String name = "name" + String.valueOf(RANDOM.nextInt(10000));
            LocalDateTime startDateTime = LocalDateTime.of(randIntWithBound(1990, 2031), randIntWithBound(1, 13),
                    randIntWithBound(1, 29), randIntWithBound(1, 24), randIntWithBound(1, 60));
            LocalDateTime endDateTime = startDateTime.plusMinutes(randIntWithBound(100, 10000));
            String location = "location" + String.valueOf(RANDOM.nextInt(10000));
            List<User> users = getPseudoRandomUsers(randIntWithBound(1,10));

            Event event = new Event(null, type, name, startDateTime, endDateTime, location, users, null, null);
            events.add(event);
        }
        return events;
    }

    public static Event getUniqueEvent() { 
        EventType type = EventType.CONCERT;
        String name = "myName" + System.currentTimeMillis();
        LocalDateTime startDateTime = LocalDateTime.of(2001,10,10,10,10);
        LocalDateTime endDateTime = startDateTime.plusMinutes(100);
        String location = "myLocation";
        
        return new Event(null, type, name, startDateTime, endDateTime, location);
    }

    public static User getUniqueUser() {
        return new User("example" + System.currentTimeMillis() + "@example.com", "12345", false);
    }

    /**
     * Generates n pseudo-random users, to be used for testing purposes.
     * 
     * @param n the number of users to generate
     * @return a list of pseudo-random users
     */
    public static List<User> getPseudoRandomUsers(int n) {
        String[] validEmails = {
            "test@example.com",
            "test.example@test.co.uk",
            "10test_test@example.test.com",
            "test@gmail.com",
            "ok@com.com" 
        };
        String[] validPasswords = {
            "password",
            "123468890",
            "awdawfgawf",
            "awd@@.com,",
            "awd998wm" 
        };
        return RANDOM.ints(n).mapToObj(i -> {
            return new User(pickRandomStringFromArray(validEmails), pickRandomStringFromArray(validPasswords),
                    RANDOM.nextBoolean());
        }).collect(Collectors.toList());
    }

    /**
     * Compares two events, and returns a boolean indicating whether they are equal.
     * Note this method does not compare users registered.
     * 
     * @param expected the expected event
     * @param actual   the actual event
     * @return if events are logistically equivalent, with the exception of the
     *         users.
     */
    public static boolean compareEventsUsersExcluded(Event expected, Event actual) {
        return expected.getName().equals(actual.getName()) && expected.getLocation().equals(actual.getLocation())
                && expected.getType().equals(actual.getType()) && expected.getEndDate().equals(actual.getEndDate())
                && expected.getStartDate().equals(actual.getStartDate())
                && expected.getAuthorEmail().equals(actual.getAuthorEmail())
                && expected.getDescription().equals(actual.getDescription());
    }

    private static String pickRandomStringFromArray(String[] array) {
        return array[RANDOM.nextInt(array.length)];
    }

    private static int randIntWithBound(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }
}

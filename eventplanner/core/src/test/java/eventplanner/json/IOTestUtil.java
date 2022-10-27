package eventplanner.json;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import eventplanner.core.Event;
import eventplanner.core.EventType;
import eventplanner.core.User;

public class IOTestUtil {

    private static final Random RANDOM = new Random(10101);
    
    public static List<Event> getPseudoRandomEvents(int n) {
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            EventType type = List.of(EventType.values()).get(RANDOM.nextInt(4));
            String name = "name" + String.valueOf(RANDOM.nextInt(10000));
            LocalDateTime startDateTime = LocalDateTime.of(
                    RANDOM.nextInt(1990, 2031),
                    RANDOM.nextInt(1, 13),
                    RANDOM.nextInt(1, 29),
                    RANDOM.nextInt(1, 24),
                    RANDOM.nextInt(1, 60));
            LocalDateTime endDateTime = startDateTime.plusMinutes(RANDOM.nextLong(100, 10000));
            String location = "location" + String.valueOf(RANDOM.nextInt(10000));
            List<User> users = getPseudoRandomUsers(RANDOM.nextInt(10));

            Event event = new Event(null, type, name, startDateTime, endDateTime, location, users, null, null);
            events.add(event);
        }
        return events;
    }

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
        return RANDOM.ints(n)
                .mapToObj(i -> {
                    return new User(
                            pickRandomStringFromArray(validEmails),
                            pickRandomStringFromArray(validPasswords),
                            RANDOM.nextBoolean());
                })
                .collect(Collectors.toList());
    }

    private static String pickRandomStringFromArray(String[] array) {
        return array[RANDOM.nextInt(array.length)];
    }

    public static boolean compareEventsUsersExcluded(Event expected, Event actual) {
        return expected.getName().equals(actual.getName())
                && expected.getLocation().equals(actual.getLocation())
                && expected.getType().equals(actual.getType())
                && expected.getEndDate().equals(actual.getEndDate())
                && expected.getStartDate().equals(actual.getStartDate())
                && expected.getAuthorEmail().equals(actual.getAuthorEmail())
                && expected.getDescription().equals(actual.getDescription());
    }
}

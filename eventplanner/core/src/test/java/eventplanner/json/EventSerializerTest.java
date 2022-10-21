package eventplanner.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import eventplanner.core.User;
import eventplanner.core.Event;
import eventplanner.core.EventType;

public class EventSerializerTest {

    private static final CustomObjectMapper OBJECT_MAPPER = new CustomObjectMapper();
    private static final String REGEX_PATTERN = "[^A-Za-z0-9\\-\\{\\}:\",@\\.]";
    private Event event;
    private Event event2;


    @BeforeEach
    public void setup() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 8, 20, 16, 20);
        List<User> users = new ArrayList<>();
        users.add(new User("christian@test.com", "password", true)); //TODO - pass på følgefeil
        users.add(new User("palina@test.co.uk", "password", true)); //TODO - pass på følgefeil
        users.add(new User("david@example.no", "password", true)); //TODO - pass på følgefeil
        users.add(new User("thor@google.com", "password", true)); //TODO - pass på følgefeil
        event = new Event(EventType.PARTY, "Toga-party", localDateTime, localDateTime.plus(3, ChronoUnit.HOURS), "Samfundet", users);

        LocalDateTime localDateTime2 = LocalDateTime.of(1990, 1, 1, 01, 01);
        List<User> users2 = new ArrayList<>();
        event2 = new Event(EventType.CONCERT, "NEON", localDateTime2, localDateTime2.plus(3, ChronoUnit.HOURS), "Festningen", users2);
    }
    
    @Test
    public void testEventSerialization() throws JsonProcessingException {
        String result = OBJECT_MAPPER.writeValueAsString(this.event).replaceAll(REGEX_PATTERN, "");
        String expected = "{\"type\":\"PARTY\",\"name\":\"Toga-party\",\"start-time\":\"2022-08-20T16:20\",\"end-time\":\"2022-08-20T19:20\",\"location\":\"Samfundet\",\"users\":\"christian@test.com\",\"palina@test.co.uk\",\"david@example.no\",\"thor@google.com\"}";
        assertEquals(expected, result);

        String result2 = OBJECT_MAPPER.writeValueAsString(this.event2).replaceAll(REGEX_PATTERN, "");
        String expected2 = "{\"type\":\"CONCERT\",\"name\":\"NEON\",\"start-time\":\"1990-01-01T01:01\",\"end-time\":\"1990-01-01T04:01\",\"location\":\"Festningen\",\"users\":}";
        assertEquals(expected2, result2, "Empty user list in event not displaying as expected");
    }
}

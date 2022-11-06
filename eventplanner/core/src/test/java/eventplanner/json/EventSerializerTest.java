package eventplanner.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import eventplanner.core.Event;
import eventplanner.core.EventType;
import eventplanner.core.User;

class EventSerializerTest {

    private static final CustomObjectMapper OBJECT_MAPPER = new CustomObjectMapper();

    @Test
    void testEventSerialization() throws JsonProcessingException {
        Event event = getTestEvent();

        // The json writer uses \r\n instead of \n for line breaks, and so must be
        // replaced in the expected string.
        String expectedString = getExpectedTestEventJsonRepresentation().replaceAll("\n", "\r\n");

        String actualString = OBJECT_MAPPER.writeValueAsString(event).replaceAll("\n", "\r\n");
        assertEquals(expectedString, actualString);

    }

    private static Event getTestEvent() {
        Event event = new Event(
                UUID.fromString("4a575ea8-0211-4814-9e1d-042ecc75a557"),
                EventType.QUIZ,
                "Name",
                getTestLocalDateTimeObject(),
                getTestLocalDateTimeObject().plusHours(10),
                "Location");
        getTestUsers().forEach(user -> event.addUser(user));

        return event;
    }

    private static LocalDateTime getTestLocalDateTimeObject() {
        return LocalDateTime.of(2023, 7, 23, 12, 20);
    }

    private static String getExpectedTestEventJsonRepresentation() {
        return """
                {
                  \"id\" : \"4a575ea8-0211-4814-9e1d-042ecc75a557\",
                  \"type\" : \"QUIZ\",
                  \"name\" : \"Name\",
                  \"start-time\" : \"2023-07-23T12:20\",
                  \"end-time\" : \"2023-07-23T22:20\",
                  \"location\" : \"Location\",
                  \"author\" : \"admin@samfundet.no\",
                  \"description\" : \"No description available\",
                  \"users\" : [ \"test1@example.com\", \"test2@example.com\", \"test3@example.com\" ]
                }""";
    }

    private static Collection<User> getTestUsers() {
        User user1 = new User("test1@example.com", "password1", false);
        User user2 = new User("test2@example.com", "password2", true);
        User user3 = new User("test3@example.com", "password3", false);

        return List.of(user1, user2, user3);
    }

}

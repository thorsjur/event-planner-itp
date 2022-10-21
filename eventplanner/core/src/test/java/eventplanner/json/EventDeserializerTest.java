package eventplanner.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import eventplanner.core.User;
import eventplanner.core.Event;
import eventplanner.core.EventType;

public class EventDeserializerTest {
    
    private static final CustomObjectMapper OBJECT_MAPPER = new CustomObjectMapper();
    private Event event;
    private Event event2;
    
    @BeforeEach
    public void setup() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 8, 20, 16, 20);
        List<User> users = new ArrayList<>();
        users.add(new User("christian@test.test", "password", true)); //TODO - pass på følgefeil
        users.add(new User("palina@test.test", "password", true)); //TODO - pass på følgefeil
        users.add(new User("david@test.test", "password", true)); //TODO - pass på følgefeil
        users.add(new User("thor@test.test", "password", true)); //TODO - pass på følgefeil
        event = new Event(EventType.PARTY, "Thors bursdag", localDateTime, localDateTime.plus(3, ChronoUnit.HOURS), "Norway", users);

        List<User> users2 = new ArrayList<>();
        LocalDateTime localDateTime2 = LocalDateTime.of(2022, 8, 20, 16, 20);
        event2 = new Event(EventType.PARTY, "LAN", localDateTime2, localDateTime2.plus(3, ChronoUnit.HOURS), "Norway", users2);
    }

    @Test
    public void testEventDeserialization() throws JsonMappingException, JsonProcessingException {
        Event result = OBJECT_MAPPER.readValue(OBJECT_MAPPER.writeValueAsString(event), Event.class);
        Event result2 = OBJECT_MAPPER.readValue(OBJECT_MAPPER.writeValueAsString(event2), Event.class);
        assertEquals(event.getType(), result.getType());
        assertEquals(event.getName(), result.getName());
        assertEquals(event.getLocation(), result.getLocation());
        assertEquals(event.getStartDate(), result.getStartDate());
        assertEquals(event.getEndDate(), result.getEndDate());
        //assertEquals(event.getUsers(), result.getUsers()); TODO
        //assertEquals(event2.getUsers(), result2.getUsers()); TODO
    }
}

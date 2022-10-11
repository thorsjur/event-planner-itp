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
    Event event;
    
    @BeforeEach
    public void setup() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 8, 20, 16, 20);
        List<User> users = new ArrayList<>();
        users.add(new User("christian"));
        users.add(new User("palina"));
        event = new Event(EventType.PARTY, "Thor", localDateTime, localDateTime.plus(3, ChronoUnit.HOURS), "Norway", users);
    }

    @Test
    public void testEventDeserialization() throws JsonMappingException, JsonProcessingException {
        Event result = OBJECT_MAPPER.readValue(OBJECT_MAPPER.writeValueAsString(event), Event.class);
        assertEquals(event.getType(), result.getType());
        assertEquals(event.getName(), result.getName());
        assertEquals(event.getLocation(), result.getLocation());
        assertEquals(event.getStartDate(), result.getStartDate());
        assertEquals(event.getEndDate(), result.getEndDate());
        assertEquals(event.getUsers(), result.getUsers());
    }
}

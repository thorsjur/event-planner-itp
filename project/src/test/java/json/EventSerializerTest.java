package json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import project.Event;
import project.EventType;

public class EventSerializerTest {

    private static final CustomObjectMapper OBJECT_MAPPER = new CustomObjectMapper();
    private Event event;

    @BeforeEach
    public void setup() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, 8, 20, 16, 20);
        event = new Event(EventType.PARTY, "Toga-party", localDateTime, localDateTime.plus(3, ChronoUnit.HOURS), "Samfundet");
    }
    
    @Test
    public void testEventSerialization() throws JsonProcessingException {
        String result = OBJECT_MAPPER.writeValueAsString(this.event).replaceAll("[^A-Za-z0-9\\-\\{\\}:\",]", "");
        String expected = "{\"type\":\"PARTY\",\"name\":\"Toga-party\",\"start-time\":\"2022-08-20T16:20\",\"end-time\":\"2022-08-20T19:20\",\"location\":\"Samfundet\"}";
        System.out.println(OBJECT_MAPPER.writeValueAsString(this.event));
        assertEquals(expected, result);
    }
}

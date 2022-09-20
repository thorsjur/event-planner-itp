package json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import project.Event;

public class EventDeserializerTest {
    
    private static final CustomObjectMapper OBJECT_MAPPER = new CustomObjectMapper();
    Event event;
    
    @BeforeEach
    public void setup() {
        event = new Event("Thor", "0", "0", "Norway");
    }

    @Test
    public void testEventDeserialization() throws JsonMappingException, JsonProcessingException {
        Event result = OBJECT_MAPPER.readValue(OBJECT_MAPPER.writeValueAsString(event), Event.class);
        assertEquals(event.getName(), result.getName());
        assertEquals(event.getLocation(), result.getLocation());
    }
}

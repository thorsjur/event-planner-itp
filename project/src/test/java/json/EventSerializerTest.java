package json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import project.Event;

public class EventSerializerTest {

    private static final CustomObjectMapper MAPPER = new CustomObjectMapper();
    private Event event;

    @BeforeEach
    public void setup() {
        event = new Event("Thor", "0", "0", "Norway");
    }
    
    @Test
    public void testEventSerialization() throws JsonProcessingException {
        String result = MAPPER.writeValueAsString(this.event).replaceAll("[^A-Za-z\\{\\}:\",]", "");
        String expected = "{\"name\":\"Thor\",\"location\":\"Norway\"}";

        System.out.println(result);

        assertEquals(expected, result);
    }
}

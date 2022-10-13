package eventplanner.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import eventplanner.core.Event;

public class EventCollectionJsonReaderTest {

    Collection<Event> events;

    @BeforeEach
    public void setup() throws IOException {
        EventCollectionJsonReader reader = new EventCollectionJsonReader();
        events = reader.load(new File("src/main/java/resources/data/event.json"));
    }

    @Test
    public void testJsonReader() throws IOException {
        setup();
        Class<? extends Event> result = events.stream().findAny().get().getClass();
        assertEquals(Event.class, result);
    }
    
}

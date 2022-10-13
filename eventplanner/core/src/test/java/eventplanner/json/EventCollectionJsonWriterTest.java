package eventplanner.json;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import eventplanner.core.Event;
import eventplanner.core.EventType;

public class EventCollectionJsonWriterTest {

    Event event;

    @BeforeEach
    public void setup() throws IOException {
        LocalDateTime localDateTime = LocalDateTime.of(1990, 1, 1, 01, 01);
        event = new Event(EventType.CONCERT, "randomName" + System.currentTimeMillis(), localDateTime, localDateTime.plus(3, ChronoUnit.HOURS), "Festningen", new ArrayList<>());
        EventCollectionJsonWriter writer = new EventCollectionJsonWriter();
        Collection<Event> eventCollection = new ArrayList<Event>(List.of(event));
        writer.save(eventCollection, new File("src/main/java/resources/data/event.json"));
    }

    @Test
    public void testJsonWriterAndReader() throws IOException {
        EventCollectionJsonReader reader = new EventCollectionJsonReader();
        assertTrue( () -> {
            try {
                for (Event event : reader.load(new File("src/main/java/resources/data/event.json"))) {
                    if (event.getName().equals(this.event.getName())) {
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        });;
    }
    
}

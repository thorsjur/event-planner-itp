package eventplanner.json.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import eventplanner.core.Event;
import eventplanner.json.EventCollectionJsonReader;
import eventplanner.json.EventCollectionJsonWriter;

public class IOUtil {

    public static void appendEventsToFile(final Collection<Event> events, final File file) throws IOException {
        EventCollectionJsonReader reader = new EventCollectionJsonReader();
        Collection<Event> loadEvents = reader.load(file);
        loadEvents.addAll(events);

        EventCollectionJsonWriter writer = new EventCollectionJsonWriter();
        writer.save(loadEvents, file);
    }

    public static void appendEventToFile(final Event event, final File file) throws IOException {
        appendEventsToFile(List.of(event), file);
    }
}
package eventplanner.json.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import eventplanner.core.Event;
import eventplanner.json.EventCollectionJsonReader;
import eventplanner.json.EventCollectionJsonWriter;

/**
 * Static utility methods to be used in io classes.
 */
public class IOUtil {

    /**
     * method that reads the current saved events, and appends the new events, then
     * writes back to file.
     * 
     * @param events the collection of events to append to file
     * @param file   the file to append to
     * @throws IOException on I/O error
     */
    public static void appendEventsToFile(final Collection<Event> events, final File file)
            throws IOException {

        EventCollectionJsonReader reader = new EventCollectionJsonReader();
        Collection<Event> loadEvents = reader.load(file);
        loadEvents.addAll(events);

        EventCollectionJsonWriter writer = new EventCollectionJsonWriter();
        writer.save(loadEvents, file);
    }

    /**
     * Takes a single event and a specific file and appends the event to file.
     * 
     * @see IOUtil#appendEventsToFile(Collection events, File file)
     */
    public static void appendEventToFile(final Event event, final File file) throws IOException {
        appendEventsToFile(List.of(event), file);
    }

    /**
     * Checks if the provided file has the provided file extension.
     * 
     * @param file          the provided file
     * @param fileExtension the provided file extension
     * @return boolean value indicating whether the file has the provided file
     *         extension
     */
    public static boolean hasFileExtension(final File file, String fileExtension) {
        return file.getName().endsWith(fileExtension);
    }
}
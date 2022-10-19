package eventplanner.json.util;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.json.EventCollectionJsonReader;
import eventplanner.json.EventCollectionJsonWriter;

/**
 * Static utility methods to be used in io classes.
 */
public class IOUtil {

    /**
     * method that reads the current saved events, and appends the new events, then
     * writes back to file. If file is null, the file defaults to the resources
     * directory.
     * 
     * @param events the collection of events to append to file
     * @param file   the file to append to
     * @throws IOException on I/O error
     */
    public static void appendEventsToFile(final Collection<Event> events, final File file)
            throws IOException {

        Consumer<Collection<Event>> consumer = (eventCollection) -> eventCollection.addAll(events);
        alterEvents(consumer, file);
    }

    /**
     * Takes a single event and a specific file and appends the event to file.
     * If file is null, the file defaults to the resources directory.
     * 
     * @see IOUtil#appendEventsToFile(Collection events, File file)
     */
    public static void appendEventToFile(final Event event, final File file) throws IOException {
        appendEventsToFile(List.of(event), file);
    }

    /**
     * Checks if the provided file has the provided file extension.
     * If file is null, the file defaults to the resources directory.
     * 
     * @param file          the provided file
     * @param fileExtension the provided file extension
     * @return boolean value indicating whether the file has the provided file
     *         extension
     */
    public static boolean hasFileExtension(final File file, String fileExtension) {
        return file.getName().endsWith(fileExtension);
    }

    /**
     * Adds the specified user to a list of events in the provided file.
     * If file is null, the file defaults to the resources directory.
     * 
     * @param events the collection of events to add user
     * @param user   the user to be added to the events
     * @param file   the provided file
     * @throws IOException on I/O error
     */
    public static void addUserToEvents(final Collection<Event> events, final User user, final File file)
            throws IOException {

        Consumer<Collection<Event>> consumer = (eventCollection) -> {
            eventCollection.stream()
                    .filter(event -> events.contains(event))
                    .forEach(event -> event.addUser(user));
        };

        alterEvents(consumer, file);
    }

    /**
     * Adds the specified user to a single event in the provided file.
     * If file is null, the file defaults to the resources directory.
     * 
     * @param event the events you want to add the user to
     * @param user  the user to be added to the event
     * @param file  the provided file
     * @throws IOException on I/O error
     */
    public static void addUserToEvent(final Event event, final User user, final File file)
            throws IOException {

        addUserToEvents(List.of(event), user, file);
    }

    /**
     * Removes the specified user from a list of events in the provided file.
     * If file is null, the file defaults to the resources directory.
     * 
     * @param events the collection of events to remove user from
     * @param user   the user to be removed from the events
     * @param file   the provided file
     * @throws IOException on I/O error
     */
    public static void removeUserFromEvents(final Collection<Event> events, final User user, final File file)
            throws IOException {

        Consumer<Collection<Event>> consumer = (eventCollection) -> {
            eventCollection.stream()
                    .filter(event -> events.contains(event))
                    .forEach(event -> event.removeUser(user));
        };

        alterEvents(consumer, file);
    }

    /**
     * Removes the specified user from a single event in the provided file.
     * If file is null, the file defaults to the resources directory.
     * 
     * @param event the events you want to remove the user from
     * @param user  the user to be removed from the event
     * @param file  the provided file
     * @throws IOException on I/O error
     */
    public static void removeUserFromEvent(final Event event, final User user, final File file)
            throws IOException {

        removeUserFromEvents(List.of(event), user, file);
    }

    /**
     * Changes the saved events on the basis of the specified consumer.
     * If file is null, the file defaults to the resources directory.
     * 
     * @param consumer a consumer that alters the collection of events in some way
     * @param file     the provided file
     * @throws IOException on I/O error
     */
    private static void alterEvents(Consumer<Collection<Event>> consumer, final File file) throws IOException {
        EventCollectionJsonReader reader = new EventCollectionJsonReader();
        Collection<Event> loadEvents = reader.load(file);

        consumer.accept(loadEvents);

        EventCollectionJsonWriter writer = new EventCollectionJsonWriter();
        writer.save(loadEvents, file);
    }
}
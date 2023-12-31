package eventplanner.json.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.json.EventCollectionJsonReader;
import eventplanner.json.EventCollectionJsonWriter;
import eventplanner.json.UserCollectionJsonReader;
import eventplanner.json.UserCollectionJsonWriter;

/**
 * Static utility methods to be used for IO operations.
 */
public class IOUtil {

    private static final EventCollectionJsonWriter EVENT_WRITER = new EventCollectionJsonWriter();
    private static final EventCollectionJsonReader EVENT_READER = new EventCollectionJsonReader();
    private static final UserCollectionJsonWriter USER_WRITER = new UserCollectionJsonWriter();
    private static final UserCollectionJsonReader USER_READER = new UserCollectionJsonReader();

    private IOUtil() {
        throw new IllegalStateException("You cannot instantiate an utility class!");
    }

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

        Consumer<Collection<Event>> consumer = eventCollection -> eventCollection.addAll(events);
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
     * method that reads the current saved users, and appends the new users, then
     * writes users to file.
     * 
     * @param users         the collection of users to append to file
     * @param file          the file to append to
     * @throws IOException  on I/O error
     */
    public static void appendUsersToFile(final Collection<User> users, final File file)
            throws IOException {

        Consumer<Collection<User>> consumer = uc -> uc.addAll(users);
        alterUsers(consumer, file);
    }

    /**
     * Takes a user and a specific file and appends the user to json file.
     * 
     * @see IOUtil#appendUsersToFile(users, file)
     */
    public static void appendUserToFile(final User user, final File file) throws IOException {
        appendUsersToFile(List.of(user), file);
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
     * Loads users from file, matching the provided email addresses.
     * If file is null, the file defaults to the resources directory.
     * 
     * @param emails the collection of emails
     * @param file   the provided file
     * @return list of users matching email addresses
     * @throws IOException on I/O error
     */
    public static List<User> loadUsersMatchingEmail(final List<String> emails, final File file) throws IOException {
        return USER_READER.load(file).stream()
                .filter(user -> emails.contains(user.email()))
                .collect(Collectors.toList());
    }

    /**
     * Loads user from file, matching the provided email addresses.
     * If file is null, the file defaults to the resources directory.
     * 
     * @param email the email to match
     * @param file  the provided file
     * @return the user matching the email address or null if no such user exists
     * @throws IOException on I/O error
     */
    public static User loadUserMatchingEmail(final String email, final File file) throws IOException {
        List<User> users = loadUsersMatchingEmail(List.of(email), file);
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * Method for loading/returning all users from a file.
     * 
     * @param file the file to read from
     * @return collection of users
     * @throws IOException on I/O error  
     */
    public static Collection<User> loadAllUsers(final File file) throws IOException {
        return USER_READER.load(file);
    }

    /**
     * Method for overwriting userfile.
     * 
     * @param users the users that overwrite the previous file
     * @param file the file to overwrite
     * @throws IOException on I/O error
     */
    public static void overwriteUsers(List<User> users, File file) throws IOException {
        USER_WRITER.save(users, file);
    }

    /**
     * Method for overwriting eventsfile.
     * 
     * @param events the events that overwrites the previous file
     * @param file the file to overwrite
     * @throws IOException on I/O error
     */
    public static void overwriteEvents(List<Event> events, File file) throws IOException {
        EVENT_WRITER.save(events, file);
    }

    /**
     * Loads events from file, matching the provided IDs.
     * If file is null, the file defaults to the resources directory.
     * 
     * @param ids the collection of IDs
     * @param file the provided file
     * @return list of events matching IDs
     * @throws IOException on I/O error
     */
    public static List<Event> loadEventsMatchingId(final List<String> ids, final File file) throws IOException {
        return EVENT_READER.load(file).stream()
                .filter(event -> ids.contains(event.getId().toString()))
                .collect(Collectors.toList());
    }

    /**
     * Loads event from file, matching the provided ID.
     * If file is null, the file defaults to the resources directory.
     * 
     * @param id the id to match
     * @param file the provided file
     * @return the event matching the id or null if no such user exists
     * @throws IOException on I/O error
     */
    public static Event loadEventMatchingId(final String id, final File file) throws IOException {
        List<Event> events = loadEventsMatchingId(List.of(id), file);
        return events.isEmpty() ? null : events.get(0);
    }

    /**
     * Method for loading and then returning all events from a file.
     * 
     * @param file  the file to read from
     * @return      collection of events
     * @throws IOException on I/O error  
     */
    public static Collection<Event> loadAllEvents(final File file) throws IOException {
        return EVENT_READER.load(file);
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

        Consumer<Collection<Event>> consumer = eventCollection -> {
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
     * @param event the event you want to add the user to
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

        Consumer<Collection<Event>> consumer = eventCollection -> {
            eventCollection.stream()
                    .filter(event -> events.contains(event))
                    .forEach(event -> event.removeUser(user));
        };

        alterEvents(consumer, file);
    }

    /**
     * Removes the specified user from a event in the provided file.
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
     * Updates a event.
     * Note this method only works for updating users, and not other fields.
     * 
     * @param event the event to update
     * @param file the provided file
     * @throws IOException on I/O error
     */
    public static void updateEvent(Event event, File file) throws IOException {
        deleteEventFromFile(event, file);
        appendEventToFile(event, file);
    }

    /**
     * Removes the provided event from provided data file.
     *
     * @param event Event to be removed from file
     * @param file the provided file
     * @throws IOException on I/O error
     */
    public static void deleteEventFromFile(Event event, File file) throws IOException {
        Consumer<Collection<Event>> consumer = ec -> ec.remove(event);
        alterEvents(consumer, file);
    }

    /**
     * Removes the event with matching id from the provided data file, if the event exists.
     *
     * @param id the id of the event to delete
     * @param file the provided file
     * @throws IOException on I/O error
     * @throws IllegalStateException if no such event exists
     */
    public static void deleteEventFromFile(String id, File file) throws IOException {
        Consumer<Collection<Event>> consumer = ec -> {
            ec.remove(ec.stream()
                    .filter(e -> e.getId().equals(UUID.fromString(id)))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No such event is saved!")));
        };
        alterEvents(consumer, file);
    }

    /**
     * Removes the user with provided email from file.
     *
     * @param email email of the user to be removed from event
     * @param file the provided file
     * @throws IOException on I/O error
     * @throws IllegalStateException if no such user exists
     */
    public static void deleteUserFromFile(String email, File file) throws IOException {
        Consumer<Collection<User>> consumer = uc -> {
            uc.remove(uc.stream()
                    .filter(u -> u.email().equals(email))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No such user is saved!")));
        };
        alterUsers(consumer, file);
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
        Collection<Event> loadEvents = EVENT_READER.load(file);

        consumer.accept(loadEvents);

        EVENT_WRITER.save(loadEvents, file);
    }

    /**
     * Changes the saved users on the basis of the specified consumer.
     * If file is null, the file defaults to the resources directory.
     * 
     * @param consumer a consumer that alters the collection of users in some way
     * @param file     the provided file
     * @throws IOException on I/O error
     */
    private static void alterUsers(Consumer<Collection<User>> consumer, final File file) throws IOException {
        Collection<User> loadUsers = USER_READER.load(file);

        consumer.accept(loadUsers);

        USER_WRITER.save(loadUsers, file);
    }

    /**
     * Returns a path relative to project root directory.
     * Each segment given is resolved to root.
     * 
     * @param segments the pathing segments from project root
     * @return the path relative to project root directory
     */
    public static Path getPathRelativeToProjectRoot(String... segments) {
        Path path = getProjectRoot();
        for (String segment : segments) {
            if (path != null) {
                path = path.resolve(segment);
            }
        }
        return path;
    }

    private static Path getProjectRoot() {
        Path path = Paths.get(".").normalize().toAbsolutePath();
        while (path != null && !path.endsWith("gr2225")) {
            path = path.getParent();
        }
        return path;
    }
}
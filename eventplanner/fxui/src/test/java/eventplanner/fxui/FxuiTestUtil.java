package eventplanner.fxui;

import java.util.Collection;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.dataaccess.LocalDataAccess;

/**
 * Utility methods to be used for testing the fx ui.
 */
public class FxuiTestUtil {

    private FxuiTestUtil() {
        throw new IllegalStateException("Cannot instantiate this utility class!");
    }

    private static final LocalDataAccess localDataAccess = new LocalDataAccess();

    /**
     * Method for comparing two events. In addition to the standard comparison, the
     * method also compares all users.
     * 
     * @param ev1 the event
     * @param ev2 the other event
     * @return true if they are logistically equal, included users
     */
    public static boolean areEventsEqual(Event ev1, Event ev2) {
        return ev1.equals(ev2) && ev1.getUsers().equals(ev2.getUsers());
    }

    /**
     * A convenience method for cleaning up the provided test events.
     * 
     * @param events    the events to be deleted
     */
    public static void cleanUpEvents(Collection<Event> events) {
        events.forEach(event -> localDataAccess.deleteEvent(event));
    }

    /**
     * A convenience method for cleaning up the provided test users.
     * 
     * @param users    the users to be deleted
     */
    public static void cleanUpUsers(Collection<User> users) {
        users.forEach(user -> localDataAccess.deleteUser(user));
    }

    /**
     * Convenience method for retrieving all saved events.
     * 
     * @return a collection of all saved events
     */
    public static Collection<Event> getAllEvents() {
        return localDataAccess.getAllEvents();
    }

    /**
     * Convenience method for retrieving all saved users.
     * 
     * @return a collection of all saved users
     */
    public static Collection<User> getAllUsers() {
        return localDataAccess.getAllUsers();
    }


    /**
     * Convenience method for restoring saved events to the state of the provided collection.
     * 
     * @param originalEvents the original events you want to restore to
     */
    public static void restoreEvents(Collection<Event> originalEvents) {
        getAllEvents().forEach(localDataAccess::deleteEvent);
        originalEvents.forEach(localDataAccess::createEvent);
    }

    /**
     * Convenience method for restoring saved users to the state of the provided collection.
     * 
     * @param originalUsers the original users you want to restore to
     */
    public static void restoreUsers(Collection<User> originalUsers) {
        getAllUsers().forEach(localDataAccess::deleteUser);
        originalUsers.forEach(localDataAccess::createUser);
    }
    

}

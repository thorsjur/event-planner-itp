package eventplanner.fxui;

import java.util.Collection;

import eventplanner.core.Event;
import eventplanner.core.User;

/**
 * Interface for accessing data. Implementations of this interface can be used
 * to retrieve or alter user and events.
 */
public interface DataAccess {

    /**
     * Method for retrieving a user based on the specified email address.
     * 
     * @param email the email of the user you want to get
     * @return the user if found, else null
     */
    public User getUser(String email);

    /**
     * Method to create a new user, returns a boolean value indicating whether the
     * user was created or not.
     * 
     * @param user the user to be created
     * @return true if the user was created, else false
     */
    public boolean createUser(User user);

    /**
     * Method for retrieving all saved events.
     * 
     * @return a collection of all saved events
     */
    public Collection<Event> getAllEvents();

    /**
     * Method for updating a collection of events, returns a boolean value
     * indicating whether the events were updated or not.
     * 
     * @param events a collection of events
     * @return true if the events were updated, else false
     */
    public boolean updateEvents(Collection<Event> events);

    /**
     * Method for updating a event, returns a boolean value indicating whether the
     * event was updated or not.
     * 
     * @param event the event to be updated
     * @return true if the event was updated, else false
     */
    public boolean updateEvent(Event event);

    /**
     * Method for creating a event, returns a boolean value indicating whether the
     * event was created or not.
     * 
     * @param event the event to be created
     * @return true if the event was created, else false
     */
    public boolean createEvent(Event event);

    /**
     * Method for deleting a event, returns a boolean value indicating whether the
     * event was deleted or not.
     * 
     * @param event the event to be deleted
     * @return true if the event was deleted, else false
     */
    public boolean deleteEvent(Event event);

    /**
     * Method for checking if this is a remote DataAccess implemenentation or not.
     * 
     * @return true if this is a remote implemenentation, else false
     */
    public boolean isRemote();

}

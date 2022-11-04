package eventplanner.fxui;

import java.util.Collection;

import eventplanner.core.Event;
import eventplanner.core.User;

public interface DataAccess {

    public User getUser(String email);

    public boolean createUser(User user);

    public Collection<Event> getAllEvents();

    public boolean updateEvent(Event event);

    public boolean updateEvents(Collection<Event> events);

    public boolean createEvent(Event event);

    public boolean deleteEvent(Event event);

    public Collection<User> loadUsers();
    
    public boolean isRemote();

}

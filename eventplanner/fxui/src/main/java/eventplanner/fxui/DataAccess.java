package eventplanner.fxui;

import java.util.Collection;
import java.util.List;

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

    public static void connection() throws Exception {
        RemoteDataAccess.checkConnection();
    }

    public Collection<User> loadUsers();

    public void overwriteUsers(List<User> users);
    public void overwriteEvents(List<Event> events);
    
    public boolean isRemote();

}

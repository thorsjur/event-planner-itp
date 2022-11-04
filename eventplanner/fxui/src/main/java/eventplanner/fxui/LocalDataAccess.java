package eventplanner.fxui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.json.util.IOUtil;

public class LocalDataAccess implements DataAccess{

    private final boolean isRemote = false;

    private final File eventFile = new File("src/main/resources/eventplanner/fxui/data/event.json");
    private final File userFile = new File("src/main/resources/eventplanner/fxui/data/user.json");

    @Override
    public User getUser(String email) {
        try {
            return IOUtil.loadUserMatchingEmail(email, this.userFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean createUser(User user) {
        try {
            IOUtil.appendUserToFile(user, userFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Collection<Event> getAllEvents() {

        try {
            return syncEvents(IOUtil.loadAllEvents(eventFile));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean updateEvent(Event event) {
        try {
            IOUtil.updateEvent(event, eventFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateEvents(Collection<Event> events) {
        boolean flag = true;
        for (Event event : events) {
            if (!updateEvent(event)) {
                flag = false;   
            }
        }
        return flag;
    }

    @Override
    public boolean createEvent(Event event) {
        try {
            IOUtil.appendEventToFile(event, eventFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteEvent(Event event) {
        try {
            IOUtil.deleteEventFromFile(event, eventFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isRemote() {
        return this.isRemote;
    }

    private Collection<Event> syncEvents(Collection<Event> events) {
        events.forEach(event -> {
            Collection<User> dummies = new ArrayList<>();
            Collection<User> users = new ArrayList<>();

            event.getUsers().forEach(dummy -> {
                User realUser = getUser(dummy.email());
                users.add(realUser);
                dummies.add(dummy);
            });

            dummies.forEach(dummy -> event.removeUser(dummy));
            users.forEach(user -> event.addUser(user));
        });
        return events;
    }

    public Collection<User> loadUsers() {
        
        try {
            return IOUtil.loadAllUsers(eventFile);
        } catch (IOException e) {
            System.out.println("Could not load events");
            return new ArrayList<>();
        }
    }

    public void overwriteUsers(List<User> users) {
        
        try {
            IOUtil.overwriteUsers(users, userFile);
        } catch (Exception e) {
            System.out.println("Could not overwrite users");
        }

    }

    public void overwriteEvents(List<Event> events) {

        try {
            IOUtil.overwriteEvents(events, eventFile);
        } catch (Exception e) {
            System.out.println("Could not overwrite events.");
        }
    }


}

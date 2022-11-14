package eventplanner.dataaccess;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.io.BufferedWriter;
import java.io.FileWriter;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.json.util.IOUtil;

/**
 * Implementation of {@link DataAccess} to be used for local data access and
 * testing purposes.
 */
public class LocalDataAccess implements DataAccess {

    private File eventFile;
    private File userFile;

    public LocalDataAccess() {
        String path = System.getProperty("user.home") + "/EventPlanner/data/";
        try {
            Files.createDirectories(Paths.get(path));

            eventFile = new File(path + "event.json");
            if (!eventFile.exists()) {
                eventFile.createNewFile();

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(eventFile))) {
                    writer.write("[ ]");
                }

            }
            userFile = new File(path + "user.json");
            if (!userFile.exists()) {
                userFile.createNewFile();

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile))) {
                    writer.write("[ ]");
                }
            }
        } catch (IOException e) {
            System.err.println("Something went wrong! Wasn't able to create data files.");
        }
    }

    @Override
    public DataAccess copy() {
        return new LocalDataAccess();
    }

    @Override
    public User getUser(String email) {
        try {
            return IOUtil.loadUserMatchingEmail(email, userFile);
        } catch (IOException e) {
            printIOError(e);
            return null;
        }
    }

    @Override
    public boolean createUser(User user) {
        try {
            IOUtil.appendUserToFile(user, userFile);
        } catch (IOException e) {
            printIOError(e);
            return false;
        }
        return true;
    }

    @Override
    public Collection<Event> getAllEvents() {
        try {
            return syncEvents(IOUtil.loadAllEvents(eventFile));
        } catch (IOException e) {
            printIOError(e);
            return null;
        }
    }

    @Override
    public boolean updateEvent(Event event) {
        try {
            IOUtil.updateEvent(event, eventFile);
            return true;
        } catch (IOException e) {
            printIOError(e);
            return false;
        }
    }

    @Override
    public boolean updateEvents(Collection<Event> events) {

        // Short circuits on IOException thrown from updateEvent method, to ensure no
        // more damage than necessary is done.
        return events.stream().allMatch(event -> updateEvent(event));
    }

    @Override
    public boolean createEvent(Event event) {
        try {
            IOUtil.appendEventToFile(event, eventFile);
            return true;
        } catch (IOException e) {
            printIOError(e);
            return false;
        }
    }

    @Override
    public boolean deleteEvent(Event event) {
        try {
            IOUtil.deleteEventFromFile(event, eventFile);
            return true;
        } catch (IOException e) {
            printIOError(e);
            return false;
        }
    }

    @Override
    public boolean isRemote() {
        return false;
    }

    /**
     * Method to load all users from file.
     * 
     * @return a collection of users
     */
    public Collection<User> loadUsers() {
        try {
            return IOUtil.loadAllUsers(userFile);
        } catch (IOException e) {
            System.err.println("Could not load events");
            return new ArrayList<>();
        }
    }

    /**
     * Deletes a user from the default LocalDataAccess file.
     * 
     * @param user the user you want to delete 
     */
    public void deleteUser(User user) {
        try {
            IOUtil.deleteUserFromFile(user.email(), userFile);
        } catch (IOException e) {
            printIOError(e);
        }
    }

    /**
     * Method for retrieving all saved users.
     * 
     * @return a collection of all saved users
     */
    public Collection<User> getAllUsers() {
        try {
            return IOUtil.loadAllUsers(userFile);
        } catch (IOException e) {
            printIOError(e);
            return new ArrayList<>();
        }
    }

    private void printIOError(Exception e) {
        System.err.println("An IO error has occurred: " + e.getMessage());
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

}

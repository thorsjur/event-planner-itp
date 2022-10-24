package eventplanner.fxui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.json.EventCollectionJsonReader;
import eventplanner.json.EventCollectionJsonWriter;
import eventplanner.json.UserCollectionJsonReader;
import eventplanner.json.UserCollectionJsonWriter;

public class FxuiTestUtil {
    
    public static boolean areEventsEqual(Event ev1, Event ev2) {
        return ev1.equals(ev2) && ev1.getUsers().equals(ev2.getUsers());
    }

    public static Collection<User> loadUsers() {
        Collection<User> userCollection;
        
        try {
            UserCollectionJsonReader reader = new UserCollectionJsonReader();
            userCollection = reader.load();
        } catch (IOException e) {
            userCollection = new ArrayList<>();
            System.out.println("Could not load events");
        }

        return new ArrayList<>(userCollection);
    }

    public static void cleanUpEvents() {
        EventCollectionJsonReader reader = new EventCollectionJsonReader();
        Collection<Event> loadEvents;
        try {
            loadEvents = reader.load();
        } catch (IOException e) {
            return;
        }

        List<Event> events = loadEvents.stream()
            .filter(event -> !event.getName().equals("TestName"))
            .collect(Collectors.toList());

        EventCollectionJsonWriter writer = new EventCollectionJsonWriter();
        try {
            writer.save(events);
        } catch (IOException e) {
            System.out.println("Error occurred saving events ...");
        }
    }

    public static void cleanUpUsers() {
        Collection<User> users;
        users = loadUsers();

        List<User> usersList = users.stream()
            .filter(user -> !user.email().equals("test@test.org"))
            .collect(Collectors.toList());

        UserCollectionJsonWriter writer = new UserCollectionJsonWriter();
        try {
            writer.save(usersList);
        } catch (IOException e) {
            System.out.println("Error occurred saving events ...");
        }
    }

}

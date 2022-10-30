package eventplanner.fxui;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import eventplanner.core.Event;
import eventplanner.core.User;

public class FxuiTestUtil {

    private static DataAccess dA = new LocalDataAccess();
    
    public static boolean areEventsEqual(Event ev1, Event ev2) {
        return ev1.equals(ev2) && ev1.getUsers().equals(ev2.getUsers());
    }


    public static void cleanUpEvents() {
        Collection<Event> loadEvents;
        try {
            loadEvents = dA.getAllEvents();
        } catch (Exception e) {
            return;
        }

        List<Event> events = loadEvents.stream()
            .filter(event -> !event.getName().equals("TestName"))
            .collect(Collectors.toList());

        System.out.println("Test0 - Printing event-names:");
        for (Event event2 : events) {
            System.out.println(event2.getName());
        }

        dA.overwriteEvents(events);

    }

    public static void cleanUpUsers() {
        Collection<User> users;
        users = dA.loadUsers();

        List<User> usersList = users.stream()
            .filter(user -> !user.email().equals("test@test.org"))
            .collect(Collectors.toList());

        dA.overwriteUsers(usersList);
    }

}

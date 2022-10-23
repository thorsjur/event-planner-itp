package eventplanner.fxui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.json.UserCollectionJsonReader;

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
}

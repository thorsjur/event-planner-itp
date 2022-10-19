package eventplanner.fxui.util;

import eventplanner.core.Event;
import eventplanner.core.User;

// TODO: Flytte logikken til event klassen
public class EventUtil {
    
    public static boolean isUserRegistered(Event event, User user) {
        return event.getUsers().stream()
                .anyMatch(u -> u.equals(user));
    }
}

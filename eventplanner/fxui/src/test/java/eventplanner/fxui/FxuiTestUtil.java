package eventplanner.fxui;

import eventplanner.core.Event;

public class FxuiTestUtil {
    
    public static boolean areEventsEqual(Event ev1, Event ev2) {
        return ev1.equals(ev2) && ev1.getUsers().equals(ev2.getUsers());
    }
}

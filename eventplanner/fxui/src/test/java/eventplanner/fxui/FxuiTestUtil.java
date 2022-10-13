package eventplanner.fxui;

import eventplanner.core.Event;

public class FxuiTestUtil {
    
    public static boolean areEventsEqual(Event ev1, Event ev2) {
        return ev1.getEndDate().isEqual(ev2.getEndDate())
                && ev1.getLocation().equals(ev2.getLocation())
                && ev1.getStartDate().isEqual(ev2.getStartDate())
                && ev1.getName().equals(ev2.getName())
                && ev1.getStartDate().isEqual(ev2.getStartDate())
                && ev1.getType().equals(ev2.getType())
                && ev1.getUsers().equals(ev2.getUsers());
    }
}

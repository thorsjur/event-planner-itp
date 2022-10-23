package eventplanner.core.util;

import java.util.Map;

import eventplanner.core.Event;

public class EventUtil {
    
    public static Event createEventFromParams(Map<String, String> params) {
        return new Event(null, null, null, null, null);
    }
}

package json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

import EventPlanner.Event;

public class EventModule extends SimpleModule {
    
    private static final String NAME = "EventModule";

    public EventModule() {
        super(NAME, Version.unknownVersion());
        addSerializer(Event.class, new EventSerializer());
        addDeserializer(Event.class, new EventDeserializer());
    }
}

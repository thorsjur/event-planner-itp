package json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;

import project.Event;

public class EventModule extends SimpleModule {
    
    private static final String NAME = "EventModule";
    private static final VersionUtil VERSION_UTIL = new VersionUtil(){};

    public EventModule() {
        super(NAME, Version.unknownVersion());
        addSerializer(Event.class, new EventSerializer());
        addDeserializer(Event.class, new EventDeserializer());
    }
}

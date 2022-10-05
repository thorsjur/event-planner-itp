package eventplanner.json;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import eventplanner.core.Event;

public class EventSerializer extends JsonSerializer<Event> {

    @Override
    public void serialize(Event event, JsonGenerator jsonGen, SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("type", event.getType().toString());
        jsonGen.writeStringField("name", event.getName());
        jsonGen.writeStringField("start-time", event.getStartDate().toString());
        jsonGen.writeStringField("end-time", event.getEndDate().toString());
        jsonGen.writeStringField("location", event.getLocation());
        jsonGen.writeStringField("users", listToString(event.getUsers()));
        jsonGen.writeEndObject();
    }

    public String listToString(List<String> usernamesAsList) {
        String usernamesString = usernamesAsList.toString();
        usernamesString = usernamesString.replaceAll("\\s+", "");
        return usernamesString.substring(1, usernamesString.length()-1);
    }

    
}
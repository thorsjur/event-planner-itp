package json;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import eventplanner.Event;
import eventplanner.EventType;

public class EventDeserializer extends JsonDeserializer<Event> {

    @Override
    public Event deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        EventType eventType = EventType.valueOf(node.get("type").asText());
        String name = node.get("name").asText();
        String location = node.get("location").asText();
        LocalDateTime startDateTime = LocalDateTime.parse(node.get("start-time").asText());
        LocalDateTime endDateTime = LocalDateTime.parse(node.get("end-time").asText());

        return new Event(eventType, name, startDateTime, endDateTime, location);
    }
    
}
package eventplanner.json;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import eventplanner.core.EventType;
import eventplanner.core.Event;

public class EventDeserializer extends JsonDeserializer<Event> {

    @Override
    public Event deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        EventType eventType = EventType.valueOf(node.get("type").asText());
        String name = node.get("name").asText();
        String location = node.get("location").asText();
        LocalDateTime startDateTime = LocalDateTime.parse(node.get("start-time").asText());
        LocalDateTime endDateTime = LocalDateTime.parse(node.get("end-time").asText());

        List<String> usersList = new ArrayList<>();
        JsonNode usersNode = node.get("users");
        if (usersNode instanceof ArrayNode) {
            for (JsonNode elemetNode : ((ArrayNode) usersNode)) {
                usersList.add(elemetNode.asText());
            }
        }

        return new Event(eventType, name, startDateTime, endDateTime, location, usersList);
    }

    
}
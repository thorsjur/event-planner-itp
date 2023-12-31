package eventplanner.json;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import eventplanner.core.Event;
import eventplanner.core.EventType;
import eventplanner.core.User;

/**
 * A custom deserializer to deserialize events from json. Extends the jackson
 * JsonDeserializer, see link.
 * 
 * @see <a href=
 *      "https://fasterxml.github.io/jackson-databind/javadoc/2.13/com/fasterxml/jackson/databind/JsonDeserializer.html">JsonDeserializer
 *      docs</a>
 */
public class EventDeserializer extends JsonDeserializer<Event> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.
     * jackson.core.JsonParser,
     * com.fasterxml.jackson.databind.DeserializationContext)
     */
    @Override
    public Event deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException, JacksonException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        UUID id = UUID.fromString(node.get("id").asText());
        EventType eventType = EventType.valueOf(node.get("type").asText());
        String name = node.get("name").asText();
        String location = node.get("location").asText();
        LocalDateTime startDateTime = LocalDateTime.parse(node.get("start-time").asText());
        LocalDateTime endDateTime = LocalDateTime.parse(node.get("end-time").asText());
        String authorEmail = node.get("author").asText();
        String description = node.get("description").asText();

        JsonNode usersNode = node.get("users");

        // Adds dummy users to the events, and will later sync the dummy events with the
        // actual users.
        List<User> dummyUsers = new ArrayList<>();
        if (usersNode instanceof ArrayNode) {
            ((ArrayNode) usersNode).forEach(element -> {
                dummyUsers.add(new User(element.asText(), "dummy_password", false));
            });
        }
        return new Event(id, eventType, name, startDateTime, endDateTime, location, dummyUsers, authorEmail,
                description);
    }

}
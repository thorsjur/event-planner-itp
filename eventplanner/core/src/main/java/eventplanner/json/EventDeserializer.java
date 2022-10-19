package eventplanner.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import eventplanner.core.EventType;
import eventplanner.core.Event;
import eventplanner.core.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A custom deserializer to deserialize events from json.
 * Extends the jackson JsonDeserializer, see link.
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

        EventType eventType = EventType.valueOf(node.get("type").asText());
        String name = node.get("name").asText();
        String location = node.get("location").asText();
        LocalDateTime startDateTime = LocalDateTime.parse(node.get("start-time").asText());
        LocalDateTime endDateTime = LocalDateTime.parse(node.get("end-time").asText());

        List<User> usersList = new ArrayList<>();
        JsonNode usersNode = node.get("users");
        if (usersNode instanceof ArrayNode) {
            for (JsonNode elemetNode : ((ArrayNode) usersNode)) {
                usersList.add(User.findUser(elemetNode.asText())); //TODO - Må finne en måte å passe inn userne her
            }
        }

        return new Event(eventType, name, startDateTime, endDateTime, location, usersList);
    }

}
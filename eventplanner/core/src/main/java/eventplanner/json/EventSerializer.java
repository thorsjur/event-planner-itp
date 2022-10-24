package eventplanner.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import eventplanner.core.Event;
import eventplanner.core.User;

import java.io.IOException;

/**
 * A custom serializer to serialize events to json.
 * Extends the jackson JsonSerializer, see link.
 * 
 * @see <a href=
 *      "https://fasterxml.github.io/jackson-databind/javadoc/2.13/com/fasterxml/jackson/databind/JsonSerializer.html">JsonSerializer
 *      docs</a>
 */
public class EventSerializer extends JsonSerializer<Event> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object,
     * com.fasterxml.jackson.core.JsonGenerator,
     * com.fasterxml.jackson.databind.SerializerProvider)
     */
    @Override
    public void serialize(Event event, JsonGenerator jsonGen, SerializerProvider serializerProvider)
            throws IOException {

        jsonGen.writeStartObject();
        jsonGen.writeStringField("type", event.getType().toString());
        jsonGen.writeStringField("name", event.getName());
        jsonGen.writeStringField("start-time", event.getStartDate().toString());
        jsonGen.writeStringField("end-time", event.getEndDate().toString());
        jsonGen.writeStringField("location", event.getLocation());
        jsonGen.writeStringField("author", event.getAuthorEmail());
        jsonGen.writeStringField("description", event.getDescription());
        jsonGen.writeArrayFieldStart("users");
        for (User user : event.getUsers()) {
            jsonGen.writeString(user.email());
        }
        jsonGen.writeEndArray();
        jsonGen.writeEndObject();
    }

}
package eventplanner.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

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
public class UserSerializer extends JsonSerializer<User> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object,
     * com.fasterxml.jackson.core.JsonGenerator,
     * com.fasterxml.jackson.databind.SerializerProvider)
     */
    @Override
    public void serialize(User user, JsonGenerator jsonGen, SerializerProvider serializerProvider)
            throws IOException {

        jsonGen.writeStartObject();
        jsonGen.writeStringField("email", user.email());
        jsonGen.writeStringField("password", user.password());
        jsonGen.writeBooleanField("above18", user.above18());
        jsonGen.writeEndObject();
    }

}
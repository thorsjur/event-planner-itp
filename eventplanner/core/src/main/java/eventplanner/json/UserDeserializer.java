package eventplanner.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import eventplanner.core.User;
import eventplanner.core.util.UserUtil;

import java.io.IOException;

/**
 * A custom deserializer to deserialize users from json.
 * Extends the jackson JsonDeserializer, see link.
 * 
 * @see <a href=
 *      "https://fasterxml.github.io/jackson-databind/javadoc/2.13/com/fasterxml/jackson/databind/JsonDeserializer.html">JsonDeserializer
 *      docs</a>
 */
public class UserDeserializer extends JsonDeserializer<User> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.
     * jackson.core.JsonParser,
     * com.fasterxml.jackson.databind.DeserializationContext)
     */
    @Override
    public User deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException, JacksonException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        String email = node.get("email").asText();
        String password = UserUtil.deHash(node.get("password").asText());
        Boolean above18 = Boolean.parseBoolean(node.get("above18").asText());

        return new User(email, password, above18);
    }

}
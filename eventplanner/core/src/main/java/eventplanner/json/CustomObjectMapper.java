package eventplanner.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Class to provide functionality for writing and reading JSON.
 * Inherited from Jacksons ObjectMapper
 * 
 * @see <a href=
 *      "https://fasterxml.github.io/jackson-databind/javadoc/2.13/com/fasterxml/jackson/databind/ObjectMapper.html">ObjectMapper
 *      docs</a>
 */
public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        registerModule(new UserModule());
        registerModule(new EventModule());
        enable(SerializationFeature.INDENT_OUTPUT);
    }

}

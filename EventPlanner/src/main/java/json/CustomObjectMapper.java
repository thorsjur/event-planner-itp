package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CustomObjectMapper extends ObjectMapper {
    
    public CustomObjectMapper() {
        registerModule(new EventModule());
        enable(SerializationFeature.INDENT_OUTPUT);
    }
    
}


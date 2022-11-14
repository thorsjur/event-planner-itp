package eventplanner.json;

import com.fasterxml.jackson.core.Version;

import com.fasterxml.jackson.databind.module.SimpleModule;

import eventplanner.core.User;

/**
 * A simple module that provides registration of custom serializers and
 * deserializer.
 * Extends the jacksons databind SimpleModule.
 * 
 * @see <a href=
 *      "https://fasterxml.github.io/jackson-databind/javadoc/2.13/com/fasterxml/jackson/databind/module/SimpleModule.html">SimpleModule
 *      documentation</a>
 */
public class UserModule extends SimpleModule {

    private static final String NAME = "UserModule";

    public UserModule() {
        super(NAME, Version.unknownVersion());
        addSerializer(User.class, new UserSerializer());
        addDeserializer(User.class, new UserDeserializer());
    }
}

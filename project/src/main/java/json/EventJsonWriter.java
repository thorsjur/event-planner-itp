package json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import project.Event;

public class EventJsonWriter {
    
    public static final String DIRECTORY_PATH = "project/src/main/resources/data/";
    public static final String DEFAULT_FILE_NAME = "event";
    public static final String FILE_EXTENSION = ".json";

    private static final CustomObjectMapper OBJECT_MAPPER = new CustomObjectMapper();

    public void save(final Event event, File file) throws IOException {
        if (file == null) file = new File(DIRECTORY_PATH + DEFAULT_FILE_NAME + FILE_EXTENSION);
        if (!file.exists()) throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        OBJECT_MAPPER.writeValue(file, event);
    }

    public void save(Event event) throws IOException {
        save(event, null);
    }

}

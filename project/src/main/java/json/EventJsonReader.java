package json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import project.Event;

public class EventJsonReader {
    
    private static final CustomObjectMapper OBJECT_MAPPER = new CustomObjectMapper();

    public Event load(File file) throws IOException {
        if (file == null) file = new File(EventJsonWriter.DIRECTORY_PATH + EventJsonWriter.DEFAULT_FILE_NAME + EventJsonWriter.FILE_EXTENSION);
        if (!file.exists()) throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        
        return OBJECT_MAPPER.readValue(file, Event.class);
    }

    public Event load() throws IOException {
        return load(null);
    }

}

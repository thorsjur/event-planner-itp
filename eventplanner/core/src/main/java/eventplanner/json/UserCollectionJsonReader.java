package eventplanner.json;

import com.fasterxml.jackson.core.type.TypeReference;

import eventplanner.core.User;
import eventplanner.json.util.IOUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Reads data from a JSON file using {@link CustomObjectMapper}.
 */
public class UserCollectionJsonReader {

    private static final CustomObjectMapper USER_MAPPER = new CustomObjectMapper();

    /**
     * Method to load a collection of users from a JSON file. If no file is
     * specified for the method, the reader reads from the default JSON file
     * specified in {@link UserCollectionJsonWriter}
     * 
     * @param file the json file to load users from.
     * @return a collection of users
     * @throws IOException on low level I/O errors
     */
    public Collection<User> load(File file) throws IOException {
        if (file == null) {
            file = getDefaultFile();
        }
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + file.getAbsolutePath());
        }
        if (!IOUtil.hasFileExtension(file, UserCollectionJsonWriter.FILE_EXTENSION)) {
            throw new IllegalArgumentException("File is not of type .json");
        }

        // Few symbols in file indicate either a invalid or empty json file, thus a
        // empty collection is returned.
        if (file.length() < 10) {
            return new ArrayList<>();
        }
        return USER_MAPPER.readValue(file, new TypeReference<Collection<User>>() {
        });
    }

    /**
     * Method to load a collection of users using the project's default JSON file.
     * 
     * @see UserCollectionJsonReader#load(File file)
     */
    public Collection<User> load() throws IOException {
        return load(null);
    }

    private File getDefaultFile() {
        String[] segments = { "eventplanner", "core", "src", "main", "java", "resources", "data", "user.json" };
        Path path = IOUtil.getPathRelativeToProjectRoot(segments);
        return path.toFile();
    }

}

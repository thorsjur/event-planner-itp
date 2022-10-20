package eventplanner.json;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import eventplanner.core.User;

public class UserCollectionJsonWriterTest {

    User user;

    @BeforeEach
    public void setup() throws IOException {
        user = new User("test@test2.test", "testPassword", true);
        UserCollectionJsonWriter writer = new UserCollectionJsonWriter();
        Collection<User> userCollection = new ArrayList<User>(List.of(user));
        writer.save(userCollection, new File("src/main/java/resources/data/user.json"));
    }

    // @Test
    // public void testJsonWriterAndReader() throws IOException {
    //     UserCollectionJsonReader reader = new UserCollectionJsonReader();
    //     assertTrue( () -> {
    //         try {
    //             for (User user : reader.load(new File("src/main/java/resources/data/user.json"))) {
    //                 if (user.getEmail().equals(this.user.getEmail())) {
    //                     return true;
    //                 }
    //             }
    //         } catch (IOException e) {
    //             e.printStackTrace();
    //         }
    //         //return false; TODO
    //         return true;
    //     });;
    // }
    
}

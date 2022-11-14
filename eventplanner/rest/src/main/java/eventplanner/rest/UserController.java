package eventplanner.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eventplanner.core.User;
import eventplanner.json.util.IOUtil;

/**
 * RestController for handling all requests concerning users.
 * Uses {@code /user} as base path.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public void connect() {
        // Empty method for testing connection.
    }

    /**
     * Handler method for handling GET requests from a given user email.
     * Request syntax: {@code SERVICE_PATH + /user/get?= + USER_EMAIL}
     * 
     * @param email the email of the user to be acquired
     * @return the user in json format with a matching email, null if no user has the given email 
     */
    @GetMapping(path = "/get",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User user(@RequestParam String email) {
        try {
            return IOUtil.loadUserMatchingEmail(email, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Handler method for handling POST requests that creates a new user.
     * Request syntax: {@code SERVICE_PATH + /user/create}
     * 
     * @param user the user in json format to be created
     * @return a boolean value indicating whether the handler was successful
     */
    @PostMapping(path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean create(@RequestBody User user) {
        try {
            IOUtil.appendUserToFile(user, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Handler method for handling DELETE requests from a given user email.
     * Request syntax: {@code SERVICE_PATH + /user/ + USER_EMAIL}
     * 
     * @param email the email of the user to be deleted
     * @return a boolean value indicating whether the handler was successful
     */
    @DeleteMapping("/{email}")
    public boolean delete(@PathVariable String email) {
        try {
            IOUtil.deleteUserFromFile(email, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
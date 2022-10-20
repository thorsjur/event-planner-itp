package eventplanner.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eventplanner.json.UserCollectionJsonReader;

public class UserUtil {
    public static List<User> findUsers(List<String> emails) {
        List<User> usersList = new ArrayList<>();
        Collection<User> allUsersList = new ArrayList<>();

        try {
            UserCollectionJsonReader reader = new UserCollectionJsonReader();
            allUsersList = reader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not load users");
        }

        for (String email : emails) {
            for (User user : allUsersList) {
                if (email.equals(user.email())) {
                    usersList.add(user);
                }
            } 
        }

        if (usersList.size() == 0) {
            System.out.println("List is empty");
        }
        
        return usersList;
    }

    public static User findUser(String email) {
        return findUsers(List.of(email)).get(0);
    }


    public static String passwordHash(String password) {
        return password + "h";
    }
    public static String deHash(String password) {
        return (password == null || password.length() == 0)
        ? null 
        : (password.substring(0, password.length() - 1));
    }
}

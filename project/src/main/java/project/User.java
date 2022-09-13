package project;

import java.util.ArrayList;
import java.util.List;

public class User {
    
    private String userName;
    private List<Event> events;

    public User(String userName){
        if (userName.isBlank()){
            throw new IllegalArgumentException("User name is blank");
        }
        this.userName = userName;
        this.events = new ArrayList<>();
    }

    public List<Event> getEvents() {
        return this.events;
    }

    public String getUserName() {
        return this.userName;
    }
}

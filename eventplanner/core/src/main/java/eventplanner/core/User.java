package eventplanner.core;

import java.util.ArrayList;
import java.util.List;

public class User {
    
    private String userName;
    private List<Event> events;

    public User(String userName){
        if (userName==null || userName.isBlank()){
            throw new IllegalArgumentException("User name is null or blank");
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

    public void addEvent(Event event){
        if (event==null){
            throw new IllegalArgumentException("Event is null");
        }
        if (!this.events.contains(event)){
            this.events.add(event);
        }
    }

    public void removeEvent(Event event){
        if (event==null){
            throw new IllegalArgumentException("Event is null");
        }
        this.events.remove(event);
        event.removeUser(this);
    }
}

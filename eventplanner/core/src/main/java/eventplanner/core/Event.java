package eventplanner.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    
    private EventType type;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private List<String> users;

    public Event(EventType type, String name, LocalDateTime localDateTime, LocalDateTime localDateTime2, String location, List<String> users){
        if (type==null || name==null || localDateTime==null || localDateTime2==null || location==null || users==null){
            throw new IllegalArgumentException("One or more parameters are null");
        }
        if (name.isBlank() || location.isBlank()){
            throw new IllegalArgumentException("One or more parameters are blank");
        }
        this.users = new ArrayList<>(users);
        this.type = type;
        this.name = name;
        this.startDate = localDateTime;
        this.endDate = localDateTime2;
        this.location = location;
    }

    public List<String> getUsers() {
        return new ArrayList<>(this.users);
    }

    public EventType getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public String getLocation() {
        return this.location;
    }

    public void addUser(String username){
        if (username==null){
            throw new IllegalArgumentException("Username is null");
        }
        if (!this.users.contains(username)){
            this.users.add(username);
        }
    }

    public void removeUser(String username){
        if (username==null){
            throw new IllegalArgumentException("Username is null");
        }
        this.users.remove(username);
    }
}

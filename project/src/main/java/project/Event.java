package project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {
    
    private EventType type;
    private String name;
    private Date startDate;
    private Date endDate;
    private String location;
    private List<User> users;

    public Event(EventType type, String name, Date startDate, Date endDate, String location){
        if (type==null || name==null || startDate==null || endDate==null || location==null){
            throw new IllegalArgumentException("One or more parameters are null");
        }
        if (name.isBlank() || location.isBlank()){
            throw new IllegalArgumentException("One or more parameters are blank");
        }
        this.users = new ArrayList<>();
        this.type = type;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

    public List<User> getUsers() {
        return new ArrayList<>(this.users);
    }

    public EventType getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public String getLocation() {
        return this.location;
    }

    public void addUser(User user){
        if (user==null){
            throw new IllegalArgumentException("User is null");
        }
        if (!this.users.contains(user)){
            this.users.add(user);
        }
    }

    public void removeUser(User user){
        if (user==null){
            throw new IllegalArgumentException("User is null");
        }
        this.users.remove(user);
    }
}

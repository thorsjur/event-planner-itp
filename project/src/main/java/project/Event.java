package project;

import java.util.ArrayList;
import java.util.List;

public class Event {
    
    private String name;
    private String startTime;
    private String endTime;
    private List<User> users;

    public Event(String name, String startTime, String endTime){
        if (name==null || startTime==null || endTime==null){
            throw new IllegalArgumentException("One or more parameters are null");
        }
        if (name.isBlank() || startTime.isBlank() || endTime.isBlank()){
            throw new IllegalArgumentException("One or more parameters are blank");
        }
        this.users = new ArrayList<>();
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
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

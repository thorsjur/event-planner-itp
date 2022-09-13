package project;

import java.util.ArrayList;
import java.util.List;

public class Event {
    
    private String name;
    private String startTime;
    private String endTime;
    private List<User> users;

    public Event(String name, String startTime, String endTime){
        if (name.isBlank() || startTime.isBlank() || endTime.isBlank()){
            throw new IllegalArgumentException("One or more parameters are null");
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
}

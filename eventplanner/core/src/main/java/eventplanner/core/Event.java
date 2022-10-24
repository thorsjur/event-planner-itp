package eventplanner.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an event of type {@link EventType}.
 */
public class Event {

    private EventType type;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private List<User> users = new ArrayList<>();
    private String authorEmail;
    private String description;

    /**
     * Event constructor which requires valid arguments.
     * 
     * @param type           the {@link EventType}
     * @param name           the name describing the event
     * @param localDateTime  the time when the event starts
     * @param localDateTime2 the time when the event ends
     * @param location       the location of the event
     * @param users          a list of {@link User}s
     * 
     * @throws IllegalArgumentException when invalid arguments are passed
     */
    public Event(EventType type, String name, LocalDateTime localDateTime,
            LocalDateTime localDateTime2, String location, List<User> users, String authorEmail, String description) {

        validateEventInput(type, name, localDateTime, localDateTime2, location, users);
        if (users != null) {
            this.users.addAll(users);
        }
        this.type = type;
        this.name = name;
        this.startDate = localDateTime;
        this.endDate = localDateTime2;
        this.location = location;
        setDescription(description);
        setAuthorEmail(authorEmail);
    }

    /**
     * Event constructor which requires valid arguments.
     * An empty list of {@link User}s is created upon creation.
     * 
     * @param type           the {@link EventType}
     * @param name           the name describing the event
     * @param localDateTime  the time when the event starts
     * @param localDateTime2 the time when the event ends
     * @param location       the location of the event
     * 
     * @throws IllegalArgumentException when invalid arguments are passed
     */
    public Event(EventType type, String name, LocalDateTime localDateTime,
            LocalDateTime localDateTime2, String location) {

        this(type, name, localDateTime, localDateTime2, location, null, null, null);
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

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public String getLocation() {
        return this.location;
    }

    public String getAuthorEmail() {
        return this.authorEmail;
    }

    public String getDescription() {
        return this.description;
    }

    private void setDescription(String description){
        if (description == null || description.isBlank()){
            this.description = "No description available";
        }
        else{
            this.description = description;
        }
    }

    private void setAuthorEmail(String authorEmail){
        if (authorEmail == null || authorEmail.isBlank()){
            // Creates default user as author of default generated events
            User author = new User("STUDENTERSAMFUNDET@gmail.no", "Samf2022", true);
            this.authorEmail = author.email();
        }
        else{
            this.authorEmail = authorEmail;
        }
    }

    /**
     * Add a user to this event.
     * 
     * @param user the {@link User} to be added
     */
    public void addUser(User user) {
        validateUser(user);
        if (!this.users.contains(user)) {
            this.users.add(user);
        }
    }

    /**
     * Remove a user from this event.
     * 
     * @param user the {@link User} to be removed
     */
    public void removeUser(User user) {
        validateUser(user);
        this.users.remove(user);
    }

    private void validateEventInput(EventType type, String name, LocalDateTime localDateTime,
            LocalDateTime localDateTime2, String location, List<User> users) {

        if (type == null || name == null || localDateTime == null
                || localDateTime2 == null || location == null) {
            throw new IllegalArgumentException("One or more parameters are null");
        }
        if (name.isBlank() || location.isBlank()) {
            throw new IllegalArgumentException("One or more parameters are blank");
        }
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
    }

    /**
     * Logistically compare this event to another object.
     * Does not compare registered users.
     * Returns true if and only if the events are functionality equivalent.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Event)) {
            return false;
        }
        Event event = (Event) o;
        return endDate.isEqual(event.getEndDate())
                && location.equals(event.getLocation())
                && startDate.isEqual(event.getStartDate())
                && name.equals(event.getName())
                && type.equals(event.getType());
    }
}

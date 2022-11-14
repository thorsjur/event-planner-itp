package eventplanner.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents an event of type {@link EventType}.
 */
public class Event {

    private UUID id;
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
     * @param startDateTime  the time when the event starts
     * @param endDateTime the time when the event ends
     * @param location       the location of the event
     * @param users          a list of {@link User}s
     * 
     * @throws IllegalArgumentException when invalid arguments are passed
     */
    public Event(UUID id, EventType type, String name, LocalDateTime startDateTime, LocalDateTime endDateTime,
            String location, List<User> users, String authorEmail, String description) {

        validateEventInput(type, name, startDateTime, endDateTime, location);
        if (users != null) {
            this.users.addAll(users);
        }

        this.id = id == null ? UUID.randomUUID() : id;
        this.type = type;
        this.name = name;
        this.startDate = startDateTime;
        this.endDate = endDateTime;
        this.location = location;
        setDescription(description);
        setAuthorEmail(authorEmail);
    }

    /**
     * Event constructor which requires valid arguments. An empty list of
     * {@link User}s is created upon creation.
     * 
     * @param type           the {@link EventType}
     * @param name           the name describing the event
     * @param startDateTime  the time when the event starts
     * @param endDateTime the time when the event ends
     * @param location       the location of the event
     * 
     * @throws IllegalArgumentException when invalid arguments are passed
     */
    public Event(UUID id, EventType type, String name, LocalDateTime startDateTime, LocalDateTime endDateTime,
            String location) {

        this(id, type, name, startDateTime, endDateTime, location, null, null, null);
    }

    public List<User> getUsers() {
        return new ArrayList<>(this.users);
    }

    public UUID getId() {
        return this.id;
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
            LocalDateTime localDateTime2, String location) {

        if (type == null || name == null || localDateTime == null || localDateTime2 == null || location == null) {
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

    private void setDescription(String description) {
        this.description = description == null ? "No description available" : description;
    }

    private void setAuthorEmail(String authorEmail) {

        // Sets a default author email if no such is provided
        this.authorEmail = authorEmail == null ? "admin@samfundet.no" : authorEmail;
    }

    /**
     * Logistically compare this event to another object. Does not compare
     * registered users. Returns true if and only if the events are functionality
     * equivalent, with the exception of users.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Event)) {
            return false;
        }
        Event event = (Event) o;
        return endDate.isEqual(event.getEndDate()) && location.equals(event.getLocation())
                && startDate.isEqual(event.getStartDate()) && name.equals(event.getName())
                && type.equals(event.getType());
    }

    /**
     * Overrides the inherited method hashCode. Equal objects must have equal
     * hashcodes, since Events overrides equals, hashCode must be overwritten to
     * assign a arbitrary constant.
     * 
     * @return an arbitrary constant
     */
    @Override
    public int hashCode() {
        assert false : "hashCode not designed";
        return 42;
    }
}

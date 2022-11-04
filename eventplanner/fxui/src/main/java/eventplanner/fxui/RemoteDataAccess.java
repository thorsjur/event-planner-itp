package eventplanner.fxui;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.json.CustomObjectMapper;

public class RemoteDataAccess implements DataAccess{

    private final boolean isRemote = true;

    private final CustomObjectMapper mapper = new CustomObjectMapper();

    @Override
    public User getUser(String email) {
        try {
            DataAccess.connection();
            final URI requestUri = new URI("http://localhost:8080/user/get?email=" + email);
            final HttpRequest request = HttpRequest.newBuilder(requestUri)
                    .GET()
                    .build();
            final HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            final String responseString = response.body();
            if (responseString.isEmpty()) {
                return null;
            }
            final User readValue = mapper.readValue(responseString, User.class);
            return readValue;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean createUser(User user) {
        try {
            DataAccess.connection();
            final HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8080/user/create"))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(mapper.writeValueAsString(user)))
                    .build();
            final HttpResponse<InputStream> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofInputStream());
            return response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Collection<Event> getAllEvents() {

        Collection<Event> events = new ArrayList<>();
        try {
            DataAccess.connection();
            final URI requestUri = new URI("http://localhost:8080/event/all");
            final HttpRequest request = HttpRequest.newBuilder(requestUri).GET().build();
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            final String responseString = response.body();
            events = mapper.readValue(responseString, new TypeReference<Collection<Event>>() {
            });
            syncEvents(events);
            return events;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;

    }

    @Override
    public boolean updateEvent(Event event) {
        try {
            DataAccess.connection();
            final HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8080/event/update"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(mapper.writeValueAsString(event)))
                    .build();
            final HttpResponse<InputStream> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofInputStream());
            return response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateEvents(Collection<Event> events) {
        boolean flag = true;
        for (Event event : events) {
            if (!updateEvent(event)) {
                flag = false;   
            }
        }
        return flag;
    }

    @Override
    public boolean createEvent(Event event) {
        try {
            DataAccess.connection();
            final HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8080/event/create"))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(mapper.writeValueAsString(event)))
                    .build();
            final HttpResponse<InputStream> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofInputStream());
            return response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteEvent(Event event) {
        try {
            DataAccess.connection();
            final HttpRequest request = HttpRequest
                    .newBuilder(new URI("http://localhost:8080/event/" + encode(event.getId().toString())))
                    .DELETE()
                    .build();
            final HttpResponse<InputStream> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofInputStream());
            return response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void syncEvents(Collection<Event> events) {
        events.forEach(event -> {
            Collection<User> dummies = new ArrayList<>();
            Collection<User> users = new ArrayList<>();

            event.getUsers().forEach(dummy -> {
                User realUser = getUser(dummy.email());
                users.add(realUser);
                dummies.add(dummy);
            });

            dummies.forEach(dummy -> event.removeUser(dummy));
            users.forEach(user -> event.addUser(user));
        });
    }

    private String encode(String input) {
        try {
            return URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }

    public boolean isRemote() {
        return this.isRemote;
    }

    public static void checkConnection() throws Exception {
        final HttpRequest request = HttpRequest
            .newBuilder(new URI("http://localhost:8080/user"))
            .GET()
            .build();
        final HttpResponse<InputStream> response = HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofInputStream());
    }

    /* Not valid method for remote dataAccess.
     */
    @Override
    public Collection<User> loadUsers() {
        return null;
    }

    /* Not valid method for remote dataAccess.
     */
    @Override
    public void overwriteUsers(List<User> users) {
        return;
        
    }

    /* Not valid method for remote dataAccess.
     */
    @Override
    public void overwriteEvents(List<Event> events) {
        return;        
    }

    
}

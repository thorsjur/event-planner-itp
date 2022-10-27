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

import com.fasterxml.jackson.core.type.TypeReference;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.json.CustomObjectMapper;

public class DataAccess {

    private static final CustomObjectMapper mapper = new CustomObjectMapper();

    public static User getUser(String email) {
        try {
            final URI requestUri = new URI("http://localhost:8080/user?email=" + email);
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
            e.printStackTrace();
        }
        return null;
    }

    public static void createUser(User user) {
        try {
            final HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8080/user/create"))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(mapper.writeValueAsString(user)))
                    .build();
            final HttpResponse<InputStream> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofInputStream());
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Collection<Event> getAllEvents() {

        Collection<Event> events = new ArrayList<>();
        try {
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

    public static boolean updateEvent(Event event) {
        try {
            final HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8080/event/update"))
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(mapper.writeValueAsString(event)))
                    .build();
            final HttpResponse<InputStream> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofInputStream());
            System.out.println(response);
            return Boolean.parseBoolean(response.body().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateEvents(Collection<Event> events) {
        events.forEach(event -> updateEvent(event));
    }

    public static boolean createEvent(Event event) {
        try {
            final HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8080/event/create"))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(mapper.writeValueAsString(event)))
                    .build();
            final HttpResponse<InputStream> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofInputStream());
            System.out.println(response);
            return Boolean.parseBoolean(response.body().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteEvent(Event event) {
        try {
            final HttpRequest request = HttpRequest
                    .newBuilder(new URI("http://localhost:8080/event/" + encode(event.getId().toString())))
                    .DELETE()
                    .build();
            final HttpResponse<InputStream> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofInputStream());
            System.out.println(response);
            return Boolean.parseBoolean(response.body().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void syncEvents(Collection<Event> events) {
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

    private static String encode(String input) {
        try {
            return URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return input;
        }
    }

}

package eventplanner.fxui;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
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

/**
 * Implementation of {@link DataAccess} to be used for remote data access.
 */
public class RemoteDataAccess implements DataAccess {

    private static final CustomObjectMapper mapper = new CustomObjectMapper();
    private static final String SERVICE_PATH = "http://localhost:8080/";

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String JSON_MEDIA_TYPE = "application/json";

    @Override
    public User getUser(String email) {
        try {
            final URI requestUri = new URI(SERVICE_PATH + "user/get?email=" + email);

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
            return mapper.readValue(responseString, User.class);

        } catch (URISyntaxException | IOException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return null;
    }

    @Override
    public boolean createUser(User user) {
        try {
            assertConnection();

            final URI requestUri = new URI(SERVICE_PATH + "user/create");
            final HttpRequest request = HttpRequest.newBuilder(requestUri)
                    .header(CONTENT_TYPE, JSON_MEDIA_TYPE)
                    .POST(BodyPublishers.ofString(mapper.writeValueAsString(user)))
                    .build();
            final HttpResponse<InputStream> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofInputStream());
            return response.statusCode() == 200;

        } catch (URISyntaxException | IOException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return false;
    }

    @Override
    public Collection<Event> getAllEvents() {

        Collection<Event> events = new ArrayList<>();
        try {
            assertConnection();

            final URI requestUri = new URI(SERVICE_PATH + "event/all");
            final HttpRequest request = HttpRequest.newBuilder(requestUri)
                    .GET()
                    .build();
            final HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            final String responseString = response.body();
            events = mapper.readValue(responseString, new TypeReference<Collection<Event>>() {});
            syncEvents(events);

        } catch (URISyntaxException | IOException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        return events;

    }

    @Override
    public boolean updateEvent(Event event) {
        try {
            assertConnection();

            final URI requestURI = new URI(SERVICE_PATH + "event/update");
            final HttpRequest request = HttpRequest.newBuilder(requestURI)
                    .header(CONTENT_TYPE, JSON_MEDIA_TYPE)
                    .PUT(BodyPublishers.ofString(mapper.writeValueAsString(event))).build();
            final HttpResponse<InputStream> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofInputStream());
            return response.statusCode() == 200;

        } catch (URISyntaxException | IOException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return false;
    }

    @Override
    public boolean updateEvents(Collection<Event> events) {

        // The method short circuits if any event fails to update
        return events.stream()
                .anyMatch(event -> !updateEvent(event));
    }

    @Override
    public boolean createEvent(Event event) {
        try {
            assertConnection();

            final URI requestURI = new URI(SERVICE_PATH + "event/create");
            final HttpRequest request = HttpRequest.newBuilder(requestURI)
                    .header(CONTENT_TYPE, JSON_MEDIA_TYPE)
                    .POST(BodyPublishers.ofString(mapper.writeValueAsString(event)))
                    .build();
            final HttpResponse<InputStream> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofInputStream());
            return response.statusCode() == 200;

        } catch (URISyntaxException | IOException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return false;
    }

    @Override
    public boolean deleteEvent(Event event) {
        try {
            assertConnection();

            final URI requestURI = new URI(SERVICE_PATH + "event/" + encode(event.getId().toString()));
            final HttpRequest request = HttpRequest.newBuilder(requestURI)
                    .DELETE()
                    .build();
            final HttpResponse<InputStream> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofInputStream());
            return response.statusCode() == 200;

        } catch (URISyntaxException | IOException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return false;
    }

    @Override
    public boolean isRemote() {
        return true;
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

    /**
     * Checks the connection by sending a dummy GET request to the server, which
     * throws an exception if the connection is faulty.
     */
    public static void assertConnection() throws URISyntaxException, IOException, InterruptedException {
        final HttpRequest request = HttpRequest.newBuilder(new URI(SERVICE_PATH + "user")).GET().build();
        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofInputStream());
    }

}

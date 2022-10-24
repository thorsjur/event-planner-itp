package eventplanner.fxui;

import java.io.InputStream;
import java.net.URI;
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
            final User readValue = mapper.readValue(responseString, User.class);
            return readValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Collection<Event> getAllEvents() {

        Collection<Event> events = new ArrayList<>();
        try {
            final URI requestUri = new URI("http://localhost:8080/event/all");
            final HttpRequest request = HttpRequest.newBuilder(requestUri).GET().build();
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            final String responseString = response.body();
            events = mapper.readValue(responseString, new TypeReference<Collection<Event>>(){});
            return events;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return events;

    }

    public static void updateEvent(Event event) {
        try {
            final HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8080/event/update"))
                .header("Content-Type", "application/json")    
                .PUT(BodyPublishers.ofString(mapper.writeValueAsString(event)))
                .build();
              final HttpResponse<InputStream> response = HttpClient.newBuilder()
                  .build()
                  .send(request, HttpResponse.BodyHandlers.ofInputStream());
                System.out.println(response);
          } catch (Exception e) {
            e.printStackTrace();
          }
      
    }

    public static void main(String[] args) {
        //System.out.println(DataAccess.getUser("test@test.test"));
        //System.out.println(DataAccess.getAllEvents());
    }

}

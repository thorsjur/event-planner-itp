[nav](../nav.md)

# Release 3

 1. The project is now implementing an API solution ...

 2. We have further expanded our serialization and deserialization to handle new data for users and events. We decided to add password and age fields for users and an author and description field for events. 

 3. The user interface and its functionality is now expanded so that there is a login page when you start the app, where you can either login to a user, or automatically register a new user if the username is not yet known. All available events are displayed to the user, and you can also sign up for the events. Every event has now its own info page where various information about the event is displayed, such as a short description, who created the event, how many users are registered for the event and so on. Users can create new events (with enhanced validation), remove events that they have created, filter and display events for which the user is registered, search for events or type of event on the main page, as well as unregister/remove oneself from the events. 

 4. We have increased our testing breadth and the modules are tested thoroughly. We are testing the functionality of the UI using TestFX, making sure that the app's view works as expected.

 5. With the help of the external development tools *Spotbugs* and *Checkstyle*, the code base is analyzed to help improve code quality and identify potential vulnerabilities along with design flaws.

 6. Public methods and classes has been documented with JavaDoc, and the generated JavaDoc pages can be found at [docs/](../../../docs/). Some methods has inline comments if the method could be difficult to understand.
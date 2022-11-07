[nav](../nav.md)

# Release 3

## Enhancements

Since the last release we have extended functionality, implemented rest-api, improved codequality and updated documentation;

 1. A rest-api server has been implemented (rest module). Documentation on how to launch the server, and how it works can be found [here](../../eventplanner/rest/README.md). 

 2. We have further expanded our serialization and deserialization to handle new data for users and events. We decided to add password and age fields for users and an id, author and description field for events. 

 3. The user interface and its functionality is now expanded so that there is a login page when you start the app, where you can either login, or register a new user. All available events are displayed to the user, and you can also sign up for the events. Every event has its own info page where various information about the event is displayed, such as a short description, who created the event, how many users are registered for the event etc. Users can create new events (with enhanced validation), remove events (if user is author), filter and display events for which the user is registered, search for events or type of event on the main page, as well as deregister from events. 

 4. Tests have been enhanced to cover the implemented functionality. Code coverage can be reported with [jacoco](../../readme.md).

 5. CodeQuality has been tested with *checkstyle* and *spotbugs*.

 6. There have been implemented integration-test designed to test the project from front to back, read more [here](../../eventplanner/integrationtests/README.md).

 7. JavaDocs have been updated, and JavaDoc pages can be found at [docs/](../../../docs/). We have decided to not write javadoc for getters because our get methods dont have any side effect, nor do they require some sort of precondition outside of initialization. Some methods have inline comments if the method could be difficult to understand.

 8. Various diagrams have been created, and displayed in respective documentation-files. The collection of all diagrams can be found [here](../diagrams/).

 ## Improvement potential

 The project is now a functional application. But there have been made some decicions that should probably be improved before a possible release to the public.

 1. The server is now ran locally. In a real production, the rest-server would be hosted remotely so that users would react with the same server. This is explained holistically in the [rest-module-documentation](../../eventplanner/rest/README.md).

 2. Userdata security. Data, like passwords and emails, would benefit from a complex encryption so that the data would not be retrievable under a hypothetical attack. We currently hash passwords, but the hashingmethod used is very simple and could probably be reversed. Hashing functions like SHA-256 would be a better solution.

 3. Api-server security. Remote api-servers ofter use an api-key to ensure wether the api-requests are accepted. This has been explained further in the [rest-module-documentation](../../eventplanner/rest/README.md)
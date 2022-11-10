[nav](../nav.md)

# Release 3

## Enhancements

Since the last release we have extended functionality, implemented a REST API, improved overall code quality and updated documentation thoroughly.

  1. A RESTful API has been implemented (see [rest module](../../eventplanner/rest/)). Documentation on how to launch the server and API documentation can be found [here](../../eventplanner/rest/README.md), along with the modules dependencies and a possible improvement of the API. A DataAccess interface is used by the client to connect to the server. See [fxui module documentation](../../eventplanner/fxui/README.md) for more information.

  2. We have further expanded our serialization and deserialization to handle new data for users and events. We decided to add password and age fields for users and an id, author and description field for events. 

  3. The user interface and its functionality is now expanded so that there is a login page when you start the app, where you can either login, or register a new user. All available events are displayed to the user, and you can also sign up for the events. Every event has its own info page where various information about the event is displayed, such as a short description, who created the event, how many users are registered for the event etc. Users can create new events (with enhanced validation), remove events (if user is author), filter and display events for which the user is registered, search for events or type of event on the main page, as well as deregister from events. 

  4. Tests have been expanded to cover the implemented functionality, and enhanced to better previous tests. Code coverage can be reported with [jacoco](../../readme.md).

  5. CodeQuality has been tested with *checkstyle* and *spotbugs*.

  6. There have been implemented integration testing designed to test the project from front to back, read more [here](../../eventplanner/integrationtests/README.md).

  7. JavaDocs have been updated, and JavaDoc pages can be found at each module's respective `target/site/apidocs/index.html`. We have chosen not to write JavaDocs for getters and setters, because the methods are self explanatory. Some methods have inline comments if the method could be difficult to understand.

  8. Various diagrams have been created, and displayed in respective documentation-files. The collection of all diagrams can be found [here](../diagrams/).

<br>

 ## Improvement potential

 The project is now a functional application, however there are some key shortcomings of the project, that would have to be implemented before a release to the public can be made.

 1. The server runs locally. In a real production, the rest server would be hosted remotely so that users would interact with the same server, thus the same database. This is explained holistically in the [rest-module-documentation](../../eventplanner/rest/README.md).

 2. Security of data. Data, like passwords and emails, would benefit from a complex encryption so that the data would not be retrievable under a hypothetical attack. We currently hash passwords, but the encryption method used is very simple and could probably be reversed easily. A hashing function like SHA-256 would be a better solution, together with implementation of public and private keys. The reasoning for the current solution and more is discussed in the [core module's readme](../../eventplanner/core/README.md).

 3. API security. Remote servers often use an API key to ensure only users with proper permissions can access and alter the data. This has been explained further in the [rest module documentation](../../eventplanner/rest/README.md).
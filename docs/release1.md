# Release 1

**Current functionality:**


 1. The project is built and configured using Maven. This allows build automation and the use of helpful plugins, such as *JaCoCo* to view current test coverage. *JaCoCo* is implemented so that it creates a code coverage report on run of `mvn test`. The project is also currently *GitPodified*, allowing remote collaboration and a shared workspace.

 2. We are using JavaFX to create the user interface, and update it through controllers. The UI is currently divided into three controllers and fxml files. We are planning updates so that the project conforms better to the Model-View-Controller principle, such as creating a special class (EventManager) for handling the events and working as a interface between the model and controllers.

 3. Currently there are only two base classes in the model (*Event* and *User*), to achieve basic functionality. These classes currently provide minimal functionality and logic, and so they are easy to build upon for later releases.

 4. With the help of the *Jackson* library we have implemented custom serialization and deserialization of the Event-class. In later releases we will also add the support for serialization and deserialization of users. We have chosen to not save the collection of users signed up for the event for the first release, because the user interface and model does not currently support any actions regarding the User-class.

 5. Upon initialization of the application a ListView component of previously saved events will be displayed. There is currently limited functionality for sorting events or selecting other datafiles, however this can be easily implemented in future releases

 6. Currently there is no user validation, and so we've allowed everyone to create new events. There is basic input validation implemented, however this will be enhanced in future releases. 

 7. Because of the limited functionality and logic currently implemented, there has been limited testing. Future functionality will be thoroughly tested, and we will try to achieve something close to test-driven development.
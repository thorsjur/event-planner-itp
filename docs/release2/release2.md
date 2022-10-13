[nav](../nav.md)

# Release 2


 1. The project is now modularized into a *core* module and a *fxui* module which each contain their own pom.xml file. These modules inherit the dependencies from the pom.xml in the parent module *eventplanner*. The modularization of the project serves advantages such as being able to build modules seperately, as well as making it easier to expand the project in the future. It also reduces the coupling between the user interface layer, persistence layer and the core layer.

 2. We have also expanded our serialization and deserialization to handle users. We decided that the relation between *Event* and *User* should work so that an *Event* object contains a list of *User* objects, but an *User* does not contain knowledge of the events the user is registered to. This made it easier to expand our previous serialization and deserialization. In addition this will simplify the process of moving the persistence to the cloud.

 3. The user interface and its functionality is now expanded so that there is a login page when you start the app, where you can either login to a user, or automatically register a new user if the username is not yet known. All available events are displayed to the user, and you can also sign up for the events. You can create new events (with enhanced validation), display events for which you are registered, as well as unregister/remove yourself from the events.

 4. We have increased our testing breadth and the modules are tested thoroughly. We are testing the functionality of the UI using TestFX, making sure that the app's view works as expected.

 5. With the help of the external development tools *Spotbugs* and *Checkstyle*, the code base is analyzed to help improve code quality and identify potential vulnerabilities along with design flaws.

 6. Public methods and classes has been documented with JavaDoc, and the generated JavaDoc pages can be found at [docs/](../../../docs/). Some methods has inline comments if the method could be difficult to understand.

 
 


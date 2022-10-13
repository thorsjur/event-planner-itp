[nav](../nav.md)

# Release 2

**Current functionality:**


 1. The project is now multi modularized into a *core* module and a *fxui* module which each contain their own pom.xml file. These modules inherit the dependecies from the pom.xml in the parent module *eventplanner*. The multi modularizatioin of the project serves advantages such as being able to build all sub-modules seperately, as well as making it much easier to expand the project. 

 2. We have now expanded our serialization and deserialization to also take care of users. We decided that the relation between *Event* and *User* works so that an *Event* object contains a list of *User* objects, but a *User* does not know himself which *Event* it is a part of. This made it easy to expand our previous serialization and deserialization.

 3. The user interface and its functionality is now expanded so that there is a login page when you start the app, where you can either login to a user, or register a new user if the username is not yet in the database. You then get all the avalible events displayed, and can now also sign up for events. You can create new events (with enhanced validation), and get your events displayed, as well as remove events you have joined.

 4. We have increased our testing and are testing both modules thoroughly. We are testing the apps ui functionality by using testFX, making sure that the apps view works as expected.

 5. With the help of implementing *Spotbugs* and *Checkstyle*, we can now get an analasys of our code to help improve the codebase and identify potential vulnerabilities along with design flaws.
 


[nav](../../docs/nav.md)

# Group 25 - Core module

## Module content

Core module contains core layer and persistence layer.

- The persistence layer can be found at [/src/main/java/eventplanner/json](./src/main/java/eventplanner/json/)
- The core layer can be found at [/src/main/java/eventplanner/core](./src/main/java/eventplanner/core/)

We found it practical to create a common module for both the persistance and core -logic. The reason for this is that the persistence layer is very dependent on the core logic. And the core logic is mainly used in the persistence layer.

### Persistence layer

The persistence layer can be found [here](./src/main/java/eventplanner/json/).

To improve the applications usability, we wish to limit the user’s explicit knowledge of the data persistence, thus we choose to not use the desktop metaphor. User data will be displayed upon the user entering a unique username. Where and how the data is saved will not be disclosed to the user. When a user clicks on the “create new event”-button, “add event”-button or “remove event”-button, the saving should be implicitly done by the program.

[This](../fxui/src/main/resources/data/event.json) is where data about users and events are stored. Events will be stored in a json file. Each event will be stored as its own object with properties; id, type, name, start-time, end-time, location, author, description and users. 
    
    {
        "id" : "123e4567-e89b-12d3-a456-356642440000",
        "type" : "Example",
        "name" : "Examplename",
        "start-time" : "2003-08-17T19:09",
        "end-time" : "2003-08-18T14:09",
        "location" : "Examplelocation",
        "author" : "example@example.example",
        "description" : "exampledesc", 
        "users" : 
            [
                "ExampleName1",
                "ExampleName2"
            ]
    }

The names saved in "users" will refer to the attendants at the event.

The users will be stored in its own json file (the filepath depends on wether local or remote data access is beeing used, [read more](../rest/README.md)). User object will be stored with properties: email, password and above18. Where the password will be stored as its hashed version. Above18 property represents wether the user is above 18 years.

    {
        "email" : "example@example.example",
        "password" : "hashedpassword",
        "above18" : false
    }




### Core layer

The core layer can be found [here](./src/main/java/eventplanner/core/).

This layer contains classes that represent the core logic of the project. The Event class can be used to initiate an object with the properties described above in the persistence layer. It also contains an list of all the users connected to the event.

The user-class can be used to initiate an user object. This object will have the property name, password and above18.

## Module Architecture
![Architecture diagram](../../docs/diagrams/core_architecture.png)

**Legend:**
 - A red, dotted line from *A* to *B* depicts that *B* is a dependency of *A*.
    - Arrows from a module indicate that all layers in the module has the dependency
 - The "box" named *eventplanner* represents the collection of local modules.
 - The components with a symbol in the top-right corner represents modules, such as *javafx* and *core*.
 - The folders represent the current packages or layers of the modules.

## Reports

Jacoco code-coverage:

 ```
mvn test
```
Spotbugs and checkstyle:

 ```
mvn site
```

All reports can be found at each respectable modules `target/site` folder.

jacoco.html;
spotbugs.html;
checksstyle.html;
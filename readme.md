[---------------nav---------------](/docs/nav.md)

# EventPlanner

Group project created in the course it1901. The frontend is written in Java with JavaFX, while the backend is written with Java with Spring. The data is stored as `.json` through Jackson.

The dependencies are managed using Maven.

## Requirements

Note that [maven](https://maven.apache.org/install.html) is required to run the application.

## Repository content
- The [**eventplanner**](eventplanner/) folder constitutes the local modules, as well as project documentation.
    - [eventplanner/core](eventplanner/core/) contains the core module, with a core logic layer and persistence layer using json.
        - The persistence layer can be found at [eventplanner/core/src/main/java/eventplanner/json](eventplanner/core/src/main/java/eventplanner/json/)
        - The core layer can be found at [eventplanner/core/src/main/java/eventplanner/core](eventplanner/core/src/main/java/eventplanner/core/)
    - [eventplanner/fxui](eventplanner/fxui/) contains the module for the user interface
    - [eventplanner/rest](eventplanner/rest/) contains the module for the rest server
    - [eventplanner/integrationtest](eventplanner/integrationtests/) contains the integration tests
    - Each of the modules have their respective test folders located at `eventplanner/[MODULE_NAME]/src/test/java/eventplanner/`
    - The respective resource directories are located at `eventplanner/[MODULE_NAME]/src/main/resources/`
- Release documentation can be found at [docs/.](docs/)
- Project documentation can be found at [eventplanner/README.md](eventplanner/README.md)

<br>

## Images
![image](https://github.com/thorsjur/event-planner-itp/assets/113522770/990b835b-a586-4856-a94a-b9e7c788a6d3)

_Figure 1: Registration screen._
<br><br><br>

![image](https://github.com/thorsjur/event-planner-itp/assets/113522770/9ceb8c99-8a6d-4b8e-83b7-e767aa330b02)

_Figure 2: Events._
<br><br><br>

![image](https://github.com/thorsjur/event-planner-itp/assets/113522770/c98821a6-bf43-45b3-8f09-40b9f646d256)

_Figure 3: Event info._
<br><br><br>

![image](https://github.com/thorsjur/event-planner-itp/assets/113522770/7ed5968b-da72-4b2e-892f-221f47a2d638)

_Figure 4: Multi registration._
<br><br><br>

![image](https://github.com/thorsjur/event-planner-itp/assets/113522770/b4c29b91-d52c-48ce-b106-700b1b9315c7)

_Figure 5: Filtering._
<br><br><br>

![image](https://github.com/thorsjur/event-planner-itp/assets/113522770/58d1ef26-80d3-42d7-8233-228bd1af1b5f)

_Figure 6: Creating a new event._
<br><br><br>


<br>

## EventPlanner Architecture

![Architecture diagram](docs/diagrams/architecture.png)

**Legend:**
 - A red, dotted line from *A* to *B* depicts that *B* is a dependency of *A*.
    - Arrows from a module indicate that all layers in the module has the dependency
 - The "box" named *eventplanner* represents the collection of local modules.
 - The components with a symbol in the top-right corner represents modules, such as *javafx* and *core*.
 - The folders represent the current packages or layers of the modules.

<br>

## Building
 ```
cd eventplanner
mvn install
```

Running mavens install command will validate, compile, test, package and verify the code. After it is done it will install the package to the local repository. Make sure you are in the correct directory (`gr2225/eventplanner/`)

A flag can be passed to the build to skip tests:
 ```
mvn install -DskipTests
```

<br>

## Usage
In order to use the remote data access (RESTful API), the server needs to be booted **before** the application is launched.
 ```
cd eventplanner/rest/
mvn exec:java
```
To run the application: change directory to `eventplanner/fxui/` and run the application with `mvn javafx:run`.
 ```
cd eventplanner/fxui/
mvn javafx:run
```
If the application is launched without the server offline. The alternative local data access will be used. The applications title will tell whether you are connected to the server.

The application can also be bundled into an execution file for windows or respectively .dmg for mac and .deb for linux. The bundle is created with jpackage and jlink.
 ```
from /fxui
mvn javafx:jlink jpackage:jpackage
```

On linux, the generated deb-file can be installed with the commands:
 ```
sudo dpkg -i [path to .deb]
sudo apt-get install -f
```

<br>

## Reports

A code coverage report by JaCoCo is generated on running the tests using

 ```
mvn test
```
SpotBugs and Checkstyle reports for linting the code base for potential issues and for reporting styling mistakes is generated upon running

 ```
mvn site
```
In addition, JavaDoc pages are generated on running `mvn site`.
All reports can be found at each respective modules `target/site` folder, where JaCoCo and JavaDoc has their respective directories.

<br>

## Known problems

There might be problems depending on your operating system and prerequisites. This is a list of all known usage problems:

### Problems with fakeroot or similar (linux)
Fakeroot is required to generate the deb file. Can be installed by running the following commands.
```
sudo apt-get update
sudo apt-get install fakeroot
````
